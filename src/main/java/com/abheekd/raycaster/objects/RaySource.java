package com.abheekd.raycaster.objects;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;

/*
  RaySource is a square which stores an arraylist of all rays
  and is used to calculate, draw, and contain all rays for access
 */
public class RaySource {
  private final int numRays = 80;
  private final Vector<Ray> rays;
  private Point2D position;
  private double direction;
  private final int drawSize = 10;
  private Map map;

  // constructor
  public RaySource() {
    this.position = new Point2D.Double(250, 50);
    this.direction = 0.0;

    this.rays = new Vector<>();
    for (double i = -(double)numRays / 2; i < (double)numRays / 2; i++) {
      this.rays.add(new Ray(i * Math.PI / 180, 20.0));
    }
    this.map = new Map();
  }

  public RaySource(Map map) {
    this();
    this.map = map;
  }

  public Point2D getPosition() { return this.position; }

  public double getDirection() { return this.direction; }

  public void setPosition(Point2D position) { this.position = position; }

  public void setDirection(double direction) { this.direction = direction; }

  public void addRay(Ray ray) { this.rays.add(ray); }

  // calculate the lengths of the rays using the map
  public void calculateDistances() {
    try {
      FileWriter logFile = new FileWriter("log.txt", false);
      for (int i = 0; i < rays.size(); i++) {
        int dof = 0;
        Ray r = rays.get(i);

        // check angle
        double rAbsoluteAngle = this.direction + r.getAngle();
        if (rAbsoluteAngle < 0)
          rAbsoluteAngle = 2 * Math.PI + rAbsoluteAngle;
        rAbsoluteAngle = rAbsoluteAngle % (2 * Math.PI);
        // System.out.print("\n(" + i + ")\n-----------------------------\n");
        // r.setMagnitude(10000);

        // set initial magnitude to "infinity"
        double xMag = 10000;
        double yMag = 10000;

        logFile.write("\n(" + i + ")\n-----------------------------\n");
        logFile.write("Angle: " + rAbsoluteAngle / Math.PI + " pi\n");
        // based on angle
        if (rAbsoluteAngle > Math.PI) {
          // set rays
          double rayY = (int)(position.getY() / 100) * 100;
          double rayX = (position.getX() +
                         Math.cos(direction + r.getAngle()) *
                             (Math.abs((rayY - position.getY()) /
                                       Math.sin(direction + r.getAngle()))));

          // iteratively go through map
          while (dof < 4) {
            logFile.write("(Y) up\n");
            logFile.write("DOF: " + dof + '\n');
            logFile.write("X: " + rayX + '\n');
            logFile.write("Y: " + rayY + '\n');
            logFile.write('\n');
            // end if hit
            if (rayX / 100 >= 5 || rayX / 100 < 0)
              break;
            if (rayY / 100 >= 5 || rayY / 100 <= 0)
              break;
            int mapBlock = map.getGrid()[(int)rayY / 100 - 1][(int)rayX / 100];
            if (mapBlock == 1) {
              yMag = Math.abs((rayY - position.getY()) /
                              Math.sin(direction + r.getAngle()));
              logFile.write("Hit! Magnitude: " + yMag + '\n');
              break;
            } else {
              rayY -= 100;
              rayX -= 100 / Math.tan(direction + r.getAngle());
              dof++;
            }
          }
        } else if (rAbsoluteAngle < Math.PI) {
          double rayY = (int)(position.getY() / 100) * 100 + 100;
          double rayX =
              (position.getX() + Math.cos(direction + r.getAngle()) *
                                     ((rayY - position.getY()) /
                                      Math.sin(direction + r.getAngle())));
          while (dof < 4) {
            logFile.write("(X) right\n");
            logFile.write("DOF: " + dof + '\n');
            logFile.write("X: " + rayX + '\n');
            logFile.write("Y: " + rayY + '\n');
            logFile.write('\n');
            if (rayX / 100 >= 5 || rayX / 100 < 0)
              break;
            if (rayY / 100 >= 5 || rayY / 100 < 0)
              break;
            int mapBlock = map.getGrid()[(int)rayY / 100][(int)rayX / 100];
            if (mapBlock == 1) {
              yMag = Math.abs((rayY - position.getY()) /
                              Math.sin(direction + r.getAngle()));
              logFile.write("Hit! Magnitude: " + yMag + '\n');
              break;
            } else {
              rayY += 100;
              rayX += 100 / Math.tan(direction + r.getAngle());
              dof++;
            }
          }
        }

        dof = 0;
        if (rAbsoluteAngle < (Math.PI / 2) ||
            rAbsoluteAngle > (3 * Math.PI / 2)) {
          double rayX = (int)(position.getX() / 100) * 100 + 100;
          double rayY =
              (position.getY() + Math.sin(direction + r.getAngle()) *
                                     ((rayX - position.getX()) /
                                      Math.cos(direction + r.getAngle())));
          while (dof < 4) {
            logFile.write("(X) right\n");
            logFile.write("DOF: " + dof + '\n');
            logFile.write("X: " + rayX + '\n');
            logFile.write("Y: " + rayY + '\n');
            logFile.write('\n');
            if (rayX / 100 >= 5 || rayX / 100 < 0)
              break;
            if (rayY / 100 >= 5 || rayY / 100 < 0)
              break;
            int mapBlock = map.getGrid()[(int)rayY / 100][(int)rayX / 100];
            if (mapBlock == 1) {
              xMag = Math.abs((rayX - position.getX()) /
                              Math.cos(direction + r.getAngle()));
              logFile.write("Hit! Magnitude: " + xMag + '\n');
              break;
            } else {
              rayX += 100;
              rayY += 100 * Math.tan(direction + r.getAngle());
              dof++;
            }
          }
        } else if (rAbsoluteAngle > (Math.PI / 2) &&
                   rAbsoluteAngle < (3 * Math.PI / 2)) {
          double rayX = (int)(position.getX() / 100) * 100;
          double rayY =
              (position.getY() + Math.sin(direction + r.getAngle()) *
                                     ((rayX - position.getX()) /
                                      Math.cos(direction + r.getAngle())));
          while (dof < 4) {
            logFile.write("(X) left\n");
            logFile.write("DOF: " + dof + '\n');
            logFile.write("X: " + rayX + '\n');
            logFile.write("Y: " + rayY + '\n');
            logFile.write('\n');
            if (rayX / 100 >= 5 || rayX / 100 <= 0)
              break;
            if (rayY / 100 >= 5 || rayY / 100 < 0)
              break;
            int mapBlock = map.getGrid()[(int)rayY / 100][(int)rayX / 100 - 1];
            if (mapBlock == 1) {
              xMag = Math.abs((rayX - position.getX()) /
                              Math.cos(direction + r.getAngle()));
              logFile.write("Hit! Magnitude: " + xMag + '\n');
              break;
            } else {
              rayX -= 100;
              rayY -= 100 * Math.tan(direction + r.getAngle());
              dof++;
            }
          }
        }

        // final magnitude is whichever hit first - x or y
        double finalMagnitude = Math.min(xMag, yMag);
        r.setMagnitude(finalMagnitude);

        // set hitX to whether the final magnitude equals the x magnitude
        r.setHitX(finalMagnitude == xMag);
        logFile.write("-----------------------------\n");
      }
      logFile.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void move(boolean[] keys, double xSpeed, double ySpeed) {
    // if W is pressed change location
    if (keys[0] /* W */) {
      double dX = (Math.cos(direction) * xSpeed) /* movement distance */;
      double dY = (Math.sin(direction) * ySpeed) /* movement distance */;

      double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
      double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

      this.setPosition(new Point2D.Double(newX, newY));
    }

    // if S is pressed change location
    if (keys[1] /* S */) {
      double dX = -(Math.cos(direction) * xSpeed) /* movement distance */;
      double dY = -(Math.sin(direction) * ySpeed) /* movement distance */;

      double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
      double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

      this.setPosition(new Point2D.Double(newX, newY));
    }

    // if A is pressed change direction
    if (keys[2] /* A */) {
      double newDirection = direction - 0.002;
      if (newDirection < 0) {
        newDirection = 2 * Math.PI;
      }
      // System.out.println("New direction: " + newDirection);
      this.setDirection(newDirection);
    }

    // if D is pressed change direction
    if (keys[3] /* D */) {
      double newDirection = (direction + 0.002) % (2 * Math.PI);
      // System.out.println("New direction: " + newDirection);
      this.setDirection(newDirection);
    }
  }

  public void draw(@NotNull Graphics g) {
    // draw ray source
    g.setColor(Color.RED);
    g.drawRect((int)position.getX() - drawSize / 2,
               (int)position.getY() - drawSize / 2, drawSize, drawSize);
    g.setColor(Color.PINK);
    g.fillRect((int)position.getX() - drawSize / 2,
               (int)position.getY() - drawSize / 2, drawSize, drawSize);

    /*
     * g.setColor(Color.GREEN);
     * g.drawLine((int)position.getX(), (int)position.getY(),
     * (int)(position.getX()
     * + (Math.cos(direction) * drawSize)), (int)(position.getY() +
     * (Math.sin(direction) * drawSize)));
     */

    // draw each ray
    for (Ray ray : rays) {
      ray.draw(g, this.position, this.direction);
    }
  }

  public void renderDraw(@NotNull Graphics g, int w, int h, int drawHeight) {
    // draw the vertical lines recursively
    int lineWidth = w / rays.size();
    drawRay(g, w, h, lineWidth, drawHeight, 0, rays.size() - 1);
  }

  private void drawRay(@NotNull Graphics g, int w, int h, int lineWidth, int drawHeight, int x, int maxX) {
    // if rightmost ray return
    if (x > maxX) return;

    g.setColor(Color.GRAY);
    Ray r = rays.get(x);
    // if did not hit a wall, draw the next one
    if (r.getMagnitude() == 10000) {
      drawRay(g, w, h, lineWidth, drawHeight, x + 1, maxX);
      return;
    }
    int xPos = x * lineWidth;
    int lineHeight = (int)((double)drawHeight /
            (r.getMagnitude() * Math.cos(r.getAngle())));
    final int yCenter = 500 / 2;
    int yPos = yCenter - lineHeight / 2;
    if (r.getHitX()) {
      g.setColor(Color.DARK_GRAY);
    }
    g.fillRect(xPos, yPos, lineWidth, lineHeight);

    // recurse down
    drawRay(g, w, h, lineWidth, drawHeight, x + 1, maxX);
  }
}
