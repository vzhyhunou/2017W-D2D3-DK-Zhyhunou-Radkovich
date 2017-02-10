package com.epam.volume;

import com.epam.figures.*;

public class CubeVolumeCalculator implements VolumeCalculator {

  @Override
  public double calculate(Figure figure) {
    Cube cube = (Cube) figure;
    return cube.getSide() * cube.getSide() * cube.getSide();
  }
}
