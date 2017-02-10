package com.epam.figures;

public class Cube extends Figure {
  private double side;

  public Cube(double side) {
    this.side = side;
  }

  public double getSide() {
    return side;
  }

  public void setSide(double side) {
    this.side = side;
  }

  @Override
  public String getType() {
    return CUBE;
  }
}
