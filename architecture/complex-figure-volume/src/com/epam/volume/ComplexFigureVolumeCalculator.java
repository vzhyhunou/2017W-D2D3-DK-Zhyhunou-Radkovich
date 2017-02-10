package com.epam.volume;

import com.epam.figures.*;

import java.util.*;

public class ComplexFigureVolumeCalculator implements VolumeCalculator {

  private Map<String, VolumeCalculator> calculators;

  public ComplexFigureVolumeCalculator() {
    calculators = new HashMap<>();
    calculators.put(Figure.CUBE, new CubeVolumeCalculator());
    calculators.put(Figure.HALF_SPHERE, new SphereVolumeCalculator());
  }

  @Override
  public double calculate(Figure figure) {
    ComplexFigure complexFigure = (ComplexFigure) figure;

    double volume = 0;
    for (Figure figure1 : complexFigure.getFigures()) {
      volume += calculators.get(figure1.getType()).calculate(figure1);
    }

    return volume;
  }
}
