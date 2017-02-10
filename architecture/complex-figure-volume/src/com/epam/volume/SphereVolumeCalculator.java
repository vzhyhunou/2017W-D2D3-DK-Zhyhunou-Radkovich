package com.epam.volume;

import com.epam.figures.*;

public class SphereVolumeCalculator implements VolumeCalculator {
  @Override
  public double calculate(Figure figure) {
    HalfSphere halfSphere = (HalfSphere) figure;
    return 0.66 * Math.PI * Math.pow(halfSphere.getRadius(), 3);
  }
}
