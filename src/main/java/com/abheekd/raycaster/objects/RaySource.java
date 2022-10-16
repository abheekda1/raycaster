package com.abheekd.raycaster.objects;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class RaySource {
    private final int numRays = 30;
    private final Vector<Ray> rays;
    private Point2D position;
    private double direction;
    private final int drawSize = 10;
    private Map map;

    public RaySource() {
        this.position = new Point2D.Double(0, 0);
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

    public Point2D getPosition() {
        return this.position;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void addRay(Ray ray) {
        this.rays.add(ray);
    }

    public void calculateDistances() {
        for (int i = 0; i < rays.size(); i++) {
            int dof = 0;
            Ray r = rays.get(i);
            //System.out.print("\n(" + i + ")\n-----------------------------\n");
            r.setMagnitude(10000);
            if (this.direction > Math.PI) {
                double rayY = (int)(position.getY() / 100) * 100;
                r.setMagnitude(Math.abs((rayY - position.getY()) / Math.sin(direction + r.getAngle())));
            } else if (this.direction < Math.PI) {
                double rayY = (int)(position.getY() / 100) * 100 + 100;
                double rayX = (position.getX() + Math.cos(direction + r.getAngle()) * (Math.abs((rayY - position.getY()) / Math.sin(direction + r.getAngle()))));
                while (dof < 4) {
                    if (rayY == 0) break;
                    if (rayY >= 500) break;
                    //System.out.println("DOF: " + dof);
                    //System.out.println("X: " + rayX);
                    //System.out.println("Y: " + rayY);
                    //System.out.println();
                    if (rayX / 100 > 5 || rayX / 100 < 0) break;
                    int mapBlock = map.getGrid()[(int)rayY / 100][(int)rayX / 100];
                    if (mapBlock == 1) {
                        r.setMagnitude(Math.abs((rayY - position.getY()) / Math.sin(direction + r.getAngle())));
                        //System.out.println("Hit!");
                        break;
                    } else {
                        rayY += 100;
                        rayX += 100 / Math.tan(direction + r.getAngle());
                        dof++;
                    }
                }
            }
            //System.out.print("-----------------------------\n");
        }
    }

    public void move(boolean[] keys, double xSpeed, double ySpeed) {
        if (keys[0] /* W */) {
            double dX = (Math.cos(direction) * xSpeed) /* movement distance */;
            double dY = (Math.sin(direction) * ySpeed) /* movement distance */;

            double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
            double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

            this.setPosition(new Point2D.Double(newX, newY));
        }

        if (keys[1] /* S */) {
            double dX = -(Math.cos(direction) * xSpeed) /* movement distance */;
            double dY = -(Math.sin(direction) * ySpeed) /* movement distance */;

            double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
            double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

            this.setPosition(new Point2D.Double(newX, newY));
        }

        if (keys[2] /* A */) {
            double newDirection = direction - 0.002;
            if (newDirection < 0) {
                newDirection = 2 * Math.PI;
            }
            // System.out.println("New direction: " + newDirection);
            this.setDirection(newDirection);
        }

        if (keys[3] /* D */) {
            double newDirection = (direction + 0.002) % (2 * Math.PI);
            // System.out.println("New direction: " + newDirection);
            this.setDirection(newDirection);
        }
    }

    public void draw(@NotNull Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)position.getX() - drawSize / 2, (int)position.getY() - drawSize / 2, drawSize, drawSize);
        g.setColor(Color.PINK);
        g.fillRect((int)position.getX() - drawSize / 2, (int)position.getY() - drawSize / 2, drawSize, drawSize);

        /*
        g.setColor(Color.GREEN);
        g.drawLine((int)position.getX(), (int)position.getY(), (int)(position.getX() + (Math.cos(direction) * drawSize)), (int)(position.getY() + (Math.sin(direction) * drawSize)));
         */
        for (Ray ray : rays) {
            ray.draw(g, this.position, this.direction);
        }
    }
}
