package com.abheekd.raycaster.objects;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ray {
  private Point2D start;
  private Point2D end;
  private double angle;
  private double magnitude;

  // constructors
  public Ray() {
    this.start = new Point2D.Double(0, 0);
    this.end = new Point2D.Double(0, 0);
  }

  public Ray(Point2D start, Point2D end) {
    this.start = start;
    this.end = end;
    setAngleWithPoints();
    setMagnitudeWithPoints();
  }

  public Ray(double angle, double magnitude) {
    this.angle = angle;
    this.magnitude = magnitude;
    setPointsWithAngleAndMagnitude();
  }
  // end constructors

  // accessors
  public Point2D getStart() {
    return start;
  }

  public Point2D getEnd() {
    return end;
  }

  public double getMagnitude() {
    return this.magnitude;
  }

  public double getAngle() {
    return this.angle;
  }
  // end accessors

  // mutators
  public void setStart(Point2D start) {
    this.start.setLocation(start);
    setMagnitudeWithPoints();
    setAngleWithPoints();
  }

  public void setEnd(Point2D end) {
    this.end.setLocation(end);
    setMagnitudeWithPoints();
    setAngleWithPoints();
  }

  public void setMagnitude(double magnitude) {
    this.magnitude = magnitude;
    setPointsWithAngleAndMagnitude();
  }

  public void setAngle(double angle) {
    this.angle = angle;
    setPointsWithAngleAndMagnitude();
  }
  // end mutators

  // member functions
  public void setMagnitudeWithPoints() {
    this.magnitude = this.start.distance(end);
  }

  public void setAngleWithPoints() {
    this.angle = Math.atan(start.getY() / start.getX());
  }

  public void setPointsWithAngleAndMagnitude() {
    this.start = new Point2D.Double(0, 0);
    this.end = new Point2D.Double(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
  }
  // end member functions

  // JFrame functions
  public void draw(Graphics g, Point2D origin, double direction) {
    g.setColor(Color.BLUE);
    g.drawLine(
            (int)(origin.getX() + start.getX()),
            (int)(origin.getY() + start.getY()),
            (int)(origin.getX() + Math.cos(direction + angle) * magnitude),
            (int)(origin.getY() + Math.sin(direction + angle) * magnitude)
    );
  }
  // end JFrame functions
}
