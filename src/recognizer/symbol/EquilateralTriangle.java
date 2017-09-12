package recognizer.symbol;

import java.util.List;

/**
 * This EquilateralTriangle class extends Triangle and represents a equilateral
 * triangle as a composite symbol. A equilateral triangle is a triangle and
 * its three sides are equal in length.
 */
public class EquilateralTriangle extends Triangle {

  /**
   * The constructor of EquilateralTriangle. It checks if the input symbols can
   * form a equilateral triangle and construct a EquilateralTriangle object with
   * these basic symbols.
   *
   * @param symbols should be a list of three line segments
   * @throws IllegalArgumentException when input is null or they cannot satisfy the rule to form a
   *          equilateral triangle
   */
  public EquilateralTriangle(List<Symbol> symbols) throws
      IllegalArgumentException {
    super(symbols);
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a EquilateralTriangle");
    }
  }

  @Override
  public Symbol copy() {
    return new EquilateralTriangle(getComponents());
  }

  /**
   * It checks if the input can form a triangle and then check if the three
   * sides of the triangle are equal in length.
   *
   * @param symbols a list of BasicSymbol
   * @return true if they can form a equilateral triangle
   */
  @Override
  public boolean canForm(List<Symbol> symbols) {
    if (!super.canForm(symbols)) {
      return false;
    }
    Line l1 = (Line) symbols.get(0);
    Line l2 = (Line) symbols.get(1);
    Line l3 = (Line) symbols.get(2);
    double delta = (l1.length() + l2.length() + l3.length()) * imprecision / 3;
    // check if they have same lengths.

    return Utility.sameLength(l1, l2, delta)
        && Utility.sameLength(l2, l3, delta)
        && Utility.sameLength(l1, l3, delta);

  }

  @Override
  public String toString() {
    return "(EquilateralTriangle " + symbols.get(0).toString() + " "
        + symbols.get(1).toString() + " "
        + symbols.get(2).toString() + ")";
  }
}
