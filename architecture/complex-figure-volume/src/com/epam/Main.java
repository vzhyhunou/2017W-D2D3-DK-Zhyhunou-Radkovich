package com.epam;

import com.epam.figures.*;
import com.epam.volume.*;

import java.util.*;

public class Main {

  public static void main(String[] args) {
    Figure cube = new Cube(10);
    Figure halfSphere = new HalfSphere(10);
    Figure complexFigure = new ComplexFigure(Arrays.asList(cube, halfSphere));

    VolumeCalculator calculator = new ComplexFigureVolumeCalculator();
    double volume = calculator.calculate(complexFigure);
    System.out.println("volume = " + volume);
  }
}
