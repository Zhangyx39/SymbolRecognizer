package recognizer.symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * This Triangle class represents a triangle as a composite symbol. A triangle
 * contains three line segments that come together to form three distinct
 * vertices. No two line segments have the same orientation.
 */
public class Triangle extends CompositeSymbolAbstract implements CompositeSymbol {

  /**
   * The constructor of Triangle. It checks if the input symbols can form a
   * triangle and construct a Triangle object with these basic symbols.
   *
   * @param symbols should be a list of three line segments
   * @throws IllegalArgumentException when input is null or they cannot satisfy the rule to form a
   *         triangle
   */
  public Triangle(List<Symbol> symbols) throws IllegalArgumentException {
    super();
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a Triangle.");
    } else {
      this.symbols.addAll(symbols);
    }
  }

  /**
   * It checks if input contains three line segments and if they come
   * together to form three distinct vertices. No two line segments have the
   * same orientation.
   *
   * @param symbols a list of BasicSymbol
   * @return true if they can form a triangle
   */
  @Override
  public boolean canForm(List<Symbol> symbols) {
    // check if symbols contains 3 line segments.
    if (symbols == null || symbols.size() != 3) {
      return false;
    }
    if (!(symbols.get(0) instanceof Line)
        || !(symbols.get(1) instanceof Line)
        || !(symbols.get(2) instanceof Line)) {
      return false;
    }
    Line l1 = (Line) symbols.get(0);
    Line l2 = (Line) symbols.get(1);
    Line l3 = (Line) symbols.get(2);
    double delta = (l1.length() + l2.length() + l3.length()) * imprecision / 3;
    // sum of two sides cannot be greater than the third side
    if (l1.length() >= l2.length() + l3.length()
        || l2.length() >= l1.length() + l3.length()
        || l3.length() >= l1.length() + l2.length()) {
      return false;
    }

    // check for same orientation
    if (l1.angle() == l2.angle()
        || l1.angle() == l3.angle()
        || l2.angle() == l3.angle()) {
      return false;
    }

    // a triangle has and only has three distinct points.
    PointSet ps = new PointSet(delta);
    ps.add(l1.getEnd1());
    ps.add(l1.getEnd2());
    ps.add(l2.getEnd1());
    ps.add(l2.getEnd2());
    ps.add(l3.getEnd1());
    ps.add(l3.getEnd2());
    return ps.size() == 3;

  }

  @Override
  public Symbol copy() {
    return new Triangle(this.getComponents());
  }

  @Override
  public String toString() {
    return "(Triangle " + symbols.get(0).toString() + " "
        + symbols.get(1).toString() + " "
        + symbols.get(2).toString() + ")";
  }
}
