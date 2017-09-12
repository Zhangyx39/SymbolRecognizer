package recognizer.symbol;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Snowman class represents a snowman as a composite symbol. A snowman
 * contains three circles arranged in decreasing order of size. All centers
 * are in a line and the circles touch each other.
 */
public class Snowman extends CompositeSymbolAbstract implements CompositeSymbol {

  /**
   * The constructor of Snowman. It checks if the input symbols can form a
   * snowman and construct a Snowman object with these basic symbols. The
   * order of the input circles does not matter.
   *
   * @param symbols should be a list of three circles
   * @throws IllegalArgumentException when input is null or they cannot satisfy the rule to form a
   *         snowman
   */
  public Snowman(List<Symbol> symbols) throws IllegalArgumentException {
    super();
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a snowman.");
    } else {
      this.symbols.addAll(symbols);
    }
  }

  @Override
  public Symbol copy() {
    return new Snowman(this.getComponents());
  }

  @Override
  public String toString() {
    return "(Snowman " + symbols.get(0).toString() + " "
        + symbols.get(1).toString() + " "
        + symbols.get(2).toString() + ")";
  }

  /**
   * It checks if input contains three circles and if they are arranged in
   * decreasing order of size. All centers should be in a line and the circles
   * should touch each other. The order of the input circles does not matter.
   *
   * @param symbols a list of BasicSymbol
   * @return true if they can form a snowman
   */
  public boolean canForm(List<Symbol> symbols) {
    // check if symbols contains 3 circles.
    if (symbols == null || symbols.size() != 3) {
      return false;
    }
    if (!(symbols.get(0) instanceof Circle)
        || !(symbols.get(1) instanceof Circle)
        || !(symbols.get(2) instanceof Circle)) {
      return false;
    }

    // get a list of 3 circles.
    List<Circle> circles = symbols.stream().map(s ->
        (Circle) s).collect(Collectors.toList());

    // sort the circles in ascending order of size
    circles.sort(Comparator.comparingDouble(Circle::getRadius));
    Circle small = circles.get(0); // the small circle
    Circle medium = circles.get(1); // the medium circle
    Circle large = circles.get(2); // the large circle
    IPoint ps = small.getCenter(); // the center of small circle
    IPoint pm = medium.getCenter(); // the center of medium circle
    IPoint pl = large.getCenter(); // the center of large circle
    double rs = small.getRadius(); // the radius of small circle
    double rm = medium.getRadius(); // the radius of medium circle
    double rl = large.getRadius(); // the radius of large circle

    // if two of them are in the same size, return false.
    if (rs == rm || rm == rl) {
      return false;
    }

    // determine if they form a snowman by comparing the equality of the
    // distance from one center to another center and their sum of radius.
    return Utility.sameDistance(ps.distanceTo(pm), rs + rm, imprecision)
        && Utility.sameDistance(pm.distanceTo(pl), rm + rl, imprecision)
        && Utility.sameDistance(ps.distanceTo(pl), rs + rm * 2 + rl, imprecision);
  }
}
