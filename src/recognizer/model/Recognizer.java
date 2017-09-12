package recognizer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Candy;
import recognizer.symbol.Circle;
import recognizer.symbol.DeathlyHallows;
import recognizer.symbol.EquilateralTriangle;
import recognizer.symbol.Line;
import recognizer.symbol.Rectangle;
import recognizer.symbol.Snowman;
import recognizer.symbol.Symbol;
import recognizer.symbol.Triangle;


/**
 * This class Recognizer is the implementation of the interface IRecognizer. It
 * supports the recognition of Snowman, Triangle and EquilateralTriangle.
 *
 * <p>Snowman: three circles arranged in decreasing order of size. All centers are
 * in a line and the circles touch each other.
 *
 * <p>Triangle: Three line segments coming together to form three distinct
 * vertices, no two line segments have the same orientation.
 *
 * <p>Equilateral triangle: A triangle but with all sides equal.
 */
public class Recognizer implements IRecognizer {

  private List<Symbol> symbols;

  public Recognizer() {
    symbols = new ArrayList<>();
  }

  @Override
  public void addBasicSymbol(BasicSymbol symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Null input.");
    }
    // Avoid same symbol being added twice. Allow two different symbol with
    // same attributes.
    if (exist(symbol)) {
      return;
    }
    symbols.add(symbol);
    // If a circle is added, try to canForm a snowman.
    if (symbol instanceof Circle) {
      recognizeSnowman();
    }
    // If a line segment is added try to canForm an equilateral triangle
    // first. If failed, then a normal triangle.
    else if (symbol instanceof Line) {
      recognizeEquilateralTriangle();
      recognizeRectangle();
      recognizeTriangle();
    }
    recognizeDeathlyHallows();
    recognizeCandy();
  }

  @Override
  public List<Symbol> getSymbols() {
    List<Symbol> toReturn = new ArrayList<>();
    for (Symbol s : symbols) {
      toReturn.add(s.copy());
    }
    return toReturn;
  }

  @Override
  public BasicSymbol createCircle(double x, double y, double r) {
    if (r <= 0) {
      throw new IllegalArgumentException("Radius should be positive.");
    }
    return new Circle(x, y, r);
  }

  @Override
  public BasicSymbol createLineSegment(double x1, double y1, double x2, double
      y2) {
    if (x1 == x2 && y1 == y2) {
      throw new IllegalArgumentException("Cannot create a line segment with "
          + "same points.");
    }
    return new Line(x1, y1, x2, y2);
  }

  /**
   * To check if a symbol with same reference already exist in the list.
   *
   * @param symbol a symbol to be checked
   * @return true if it already exist, false otherwise.
   */
  private boolean exist(Symbol symbol) {
    for (Symbol s : symbols) {
      if (s == symbol) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper method to find the last 3 line segments from the symbol list and
   * try to canForm them as an EquilateralTriangle. If an
   * EquilateralTriangle is found, those 3 line segments will be removed and
   * the EquilateralTriangle will be added to the end of the list.
   *
   * @return true if success to canForm, false otherwise.
   */
  private boolean recognizeEquilateralTriangle() {
    int size = symbols.size();
    if (size < 3) {
      return false;
    }
    // An array to remember the indexes of the last 3 line segments.
    int[] indexes = new int[3];
    indexes[0] = -1;
    indexes[1] = -1;
    indexes[2] = -1;

    for (int i = size - 1, j = 0; i >= 0 && j < 3; i--) {
      if (symbols.get(i) instanceof Line) {
        indexes[j] = i;
        j++;
      }
    }
    // If we cannot find 3 line segments, return false.
    if (indexes[2] == -1) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[2]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[0]));
    try {
      // Try to form a EquilateralTriangle.
      EquilateralTriangle et = new EquilateralTriangle(component);
      symbols.remove(indexes[0]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[2]);
      symbols.add(et);
      return true;
    } catch (IllegalArgumentException e) {
      // If fail to form a EquilateralTriangle, return false.
      return false;
    }
  }

  /**
   * Helper method to find the last 3 line segments from the symbol list and
   * try to canForm them as an Triangle. If an Triangle is found, those 3
   * line segments will be removed and the Triangle will be added to the end of
   * the list.
   *
   * @return true if success to canForm, false otherwise.
   */
  private boolean recognizeTriangle() {
    int size = symbols.size();
    if (size < 3) {
      return false;
    }
    // An array to remember the indexes of the last 3 line segments.
    int[] indexes = new int[3];
    indexes[0] = -1;
    indexes[1] = -1;
    indexes[2] = -1;
    for (int i = size - 1, j = 0; i >= 0 && j < 3; i--) {
      if (symbols.get(i) instanceof Line) {
        indexes[j] = i;
        j++;
      }
    }
    // If we cannot find 3 line segments, return false.
    if (indexes[2] == -1) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[2]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[0]));
    try {
      // Try to form a Triangle.
      Triangle triangle = new Triangle(component);
      symbols.remove(indexes[0]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[2]);
      symbols.add(triangle);
      return true;
    } catch (IllegalArgumentException e) {
      // If fail to form a Triangle, return false.
      return false;
    }
  }

  /**
   * Helper method to find the last 3 circles from the symbol list and
   * try to canForm them as an Snowman. If Snowman is found, those 3 line
   * segments will be removed and the Snowman will be added to the end of the
   * list.
   *
   * @return true if success to canForm, false otherwise.
   */
  private boolean recognizeSnowman() {
    int size = symbols.size();
    if (size < 3) {
      return false;
    }
    // An array to remember the indexes of the last 3 circles.
    int[] indexes = new int[3];
    indexes[0] = -1;
    indexes[1] = -1;
    indexes[2] = -1;
    for (int i = size - 1, j = 0; i >= 0 && j < 3; i--) {
      if (symbols.get(i) instanceof Circle) {
        indexes[j] = i;
        j++;
      }
    }
    // If we cannot find 3 circles, return false.
    if (indexes[2] == -1) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[2]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[0]));
    try {
      // Try to form a snowman
      Snowman snowman = new Snowman(component);
      symbols.remove(indexes[0]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[2]);
      symbols.add(snowman);
      return true;
    } catch (IllegalArgumentException e) {
      // If fail to form a Snowman, return false.
      return false;
    }
  }

  private boolean recognizeDeathlyHallows() {
    int size = symbols.size();
    if (size < 3) {
      return false;
    }
    int[] indexes = {-1, -1, -1};
    int count = 0;
    for (int i = size - 1; i >= 0; i--) {
      if (symbols.get(i) instanceof Triangle
              && indexes[0] == -1) {
        indexes[0] = i;
        count++;
      }
      if (symbols.get(i) instanceof Circle
              && indexes[1] == -1) {
        indexes[1] = i;
        count++;
      }
      if (symbols.get(i) instanceof Line
              && indexes[2] == -1) {
        indexes[2] = i;
        count++;
      }
      if (count >= 3) {
        break;
      }
    }
    if (count != 3) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[0]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[2]));
    try {
      DeathlyHallows deathlyHallows = new DeathlyHallows(component);
      Arrays.sort(indexes);
      symbols.remove(indexes[2]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[0]);
      symbols.add(deathlyHallows);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean recognizeCandy() {
    int size = symbols.size();
    if (size < 3) {
      return false;
    }
    int[] indexes = {-1, -1, -1};
    int count = 0;
    for (int i = size - 1, j = 0; i >= 0 && count < 3; i--) {
      if (symbols.get(i) instanceof Triangle
              && indexes[j] == -1) {
        indexes[j] = i;
        j++;
        count++;
      }
      else if (symbols.get(i) instanceof Circle
              && indexes[2] == -1) {
        indexes[2] = i;
        count++;
      }
    }
    if (count != 3) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[0]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[2]));
    try {
      Candy candy = new Candy(component);
      Arrays.sort(indexes);
      symbols.remove(indexes[2]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[0]);
      symbols.add(candy);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private boolean recognizeRectangle() {
    int size = symbols.size();
    if (size < 4) {
      return false;
    }
    // An array to remember the indexes of the last 4 line segments.
    int[] indexes = new int[4];
    indexes[0] = -1;
    indexes[1] = -1;
    indexes[2] = -1;
    indexes[3] = -1;
    for (int i = size - 1, j = 0; i >= 0 && j < 4; i--) {
      if (symbols.get(i) instanceof Line) {
        indexes[j] = i;
        j++;
      }
    }
    // If we cannot find 4 line segments, return false.
    if (indexes[3] == -1) {
      return false;
    }
    List<Symbol> component = new ArrayList<>();
    component.add(symbols.get(indexes[3]));
    component.add(symbols.get(indexes[2]));
    component.add(symbols.get(indexes[1]));
    component.add(symbols.get(indexes[0]));
    try {
      // Try to form a Rectangle.
      Rectangle rectangle = new Rectangle(component);
      symbols.remove(indexes[0]);
      symbols.remove(indexes[1]);
      symbols.remove(indexes[2]);
      symbols.remove(indexes[3]);
      symbols.add(rectangle);
      return true;
    } catch (IllegalArgumentException e) {
      // If fail to form a Rectangle, return false.
      return false;
    }
  }


  public static void main(String[] args) {
    IRecognizer rec = new Recognizer();
    rec.addBasicSymbol(rec.createLineSegment(0, 0, 5, 0));
    rec.addBasicSymbol(rec.createLineSegment(5, 0, 5, 2));
    rec.addBasicSymbol(rec.createLineSegment(5, 2, 0, 2));
    rec.addBasicSymbol(rec.createLineSegment(0, 2, 0, 0));
    System.out.println(rec.getSymbols());

  }


}
