package com.abheekd.raycaster.objects;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ray {
  private Point2D start;
  private Point2D end;

  // constructors
  public Ray() {
    this.start = new Point2D.Double(0, 0);
    this.end = new Point2D.Double(0, 0);
  }

  public Ray(Point2D start, Point2D end) {
    this.start = start;
    this.end = end;
  }
  // end constructors

  // accessors
  public Point2D getStart() {
    return start;
  }

  public Point2D getEnd() {
    return end;
  }
  // end accessors

  // mutators
  public void setStart(Point2D start) {
    this.start.setLocation(start);
  }

  public void setEnd(Point2D end) {
    this.end.setLocation(end);
  }
  // end mutators

  // member functions
  public double getMagnitude() {
    return start.distance(end);
  }

  public double getAngle() {
    return Math.atan(start.getY() / start.getX());
  }
  // end member functions

  // JFrame functions
  public void draw(Graphics g) {
    g.drawLine(
            (int)start.getX(),
            (int)start.getY(),
            (int)end.getX(),
            (int)end.getY()
    );
  }
  // end JFrame functions
}
