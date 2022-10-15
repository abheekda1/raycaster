package com.abheekd.raycaster.objects;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Vector;

public class RaySource {
    private final int numRays = 1;
    private final Vector<Ray> rays;
    private Point2D position;
    private double direction;
    private final int drawSize = 10;

    public RaySource() {
        this.position = new Point2D.Double(0, 0);
        this.direction = 0.0;

        this.rays = new Vector<>();
        for (int i = 0; i < numRays; i++) {
            this.rays.add(new Ray());
        }
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

    public void move(boolean[] keys) {
        if (keys[0] /* W */) {
            double dX = (Math.cos(direction) * 0.005) /* movement distance */;
            double dY = (Math.sin(direction) * 0.005) /* movement distance */;

            double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
            double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

            this.setPosition(new Point2D.Double(newX, newY));
        }

        if (keys[1] /* S */) {
            double dX = -(Math.cos(direction) * 0.005) /* movement distance */;
            double dY = -(Math.sin(direction) * 0.005) /* movement distance */;

            double newX = Math.min(Math.max(position.getX() + dX, 0), 500);
            double newY = Math.min(Math.max(position.getY() + dY, 0), 500);

            this.setPosition(new Point2D.Double(newX, newY));
        }

        if (keys[2] /* A */) {
            double newDirection = direction - 0.001;
            if (newDirection < 0) {
                newDirection = 2 * Math.PI;
            }
            System.out.println("New direction: " + newDirection);
            this.setDirection(newDirection);
        }

        if (keys[3] /* D */) {
            double newDirection = (direction + 0.001) % (2 * Math.PI);
            System.out.println("New direction: " + newDirection);
            this.setDirection(newDirection);
        }
    }

    public void draw(@NotNull Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)position.getX() - drawSize / 2, (int)position.getY() - drawSize / 2, drawSize, drawSize);
        g.setColor(Color.PINK);
        g.fillRect((int)position.getX() - drawSize / 2, (int)position.getY() - drawSize / 2, drawSize, drawSize);

        g.setColor(Color.GREEN);
        g.drawLine((int)position.getX(), (int)position.getY(), (int)(position.getX() + (Math.cos(direction) * drawSize)), (int)(position.getY() + (Math.sin(direction) * drawSize)));

        for (Ray ray : rays) {
            ray.draw(g);
        }
    }
}
