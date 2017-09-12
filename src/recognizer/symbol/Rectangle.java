package recognizer.symbol;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Rectangle class represents a rectangle as a composite symbol. A
 * rectangle contains four line segments that come together to form four
 * distinct vertices. Each of the interior angle is a right angle.
 */
public class Rectangle extends CompositeSymbolAbstract {

  public Rectangle(List<Symbol> symbols) throws IllegalArgumentException {
    super();
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a rectangle.");
    } else {
      this.symbols.addAll(symbols);
    }
  }

  @Override
  public Symbol copy() {
    return new Rectangle(this.getComponents());
  }

  @Override
  public String toString() {
    return "(Rectangle " + symbols.get(0).toString() + " "
            + symbols.get(1).toString() + " "
            + symbols.get(2).toString() + " "
            + symbols.get(3).toString() + ")";
  }

  @Override
  public boolean canForm(List<Symbol> symbols) {
    // check if symbols contains 3 line segments.
    if (symbols == null || symbols.size() != 4) {
      return false;
    }
    if (!(symbols.get(0) instanceof Line)
            || !(symbols.get(1) instanceof Line)
            || !(symbols.get(2) instanceof Line)
            || !(symbols.get(3) instanceof Line)) {
      return false;
    }
    // get a list of 4 line segments.
    List<Line> lines = symbols.stream().map(s ->
            (Line) s).collect(Collectors.toList());

    // sort the circles in ascending order of size
    lines.sort(Comparator.comparingDouble(Line::angle));

    Line l1 = lines.get(0);
    Line l2 = lines.get(1);
    Line l3 = lines.get(2);
    Line l4 = lines.get(3);
    double delta = (l1.length() + l2.length() + l3.length() + l4.length())
            * imprecision / 4;
    if (!Utility.isParallel(l1, l2, imprecision)
            || !Utility.sameLength(l1, l2, delta)) {
      return false;
    }
    if (!Utility.isParallel(l3, l4, imprecision)
            || !Utility.sameLength(l3, l4, delta)) {
      return false;
    }
    if (!Utility.isPerpendicular(l2, l3, imprecision)
            && !Utility.isPerpendicular(l1, l4, imprecision)) {
      return false;
    }
    PointSet ps = new PointSet(delta);
    ps.add(l1.getEnd1());
    ps.add(l1.getEnd2());
    ps.add(l2.getEnd1());
    ps.add(l2.getEnd2());
    ps.add(l3.getEnd1());
    ps.add(l3.getEnd2());
    ps.add(l4.getEnd1());
    ps.add(l4.getEnd2());
    return ps.size() == 4;
  }
}
