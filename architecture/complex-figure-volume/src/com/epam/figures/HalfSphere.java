package com.epam.figures;


public class HalfSphere extends Figure {
  private double radius;

  public HalfSphere(double radius) {
    this.radius = radius;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  @Override
  public String getType() {
    return HALF_SPHERE;
  }
}
