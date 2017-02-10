package com.epam.figures;

import java.util.*;

public class ComplexFigure extends Figure {
  private List<Figure> figures;

  public ComplexFigure(List<Figure> figures) {
    this.figures = figures;
  }

  public List<Figure> getFigures() {
    if (figures == null) {
      figures = new ArrayList<>();
    }
    return figures;
  }

  public void setFigures(List<Figure> figures) {
    this.figures = figures;
  }

  @Override
  public String getType() {
    return COMPLEX_FIGURE;
  }
}
