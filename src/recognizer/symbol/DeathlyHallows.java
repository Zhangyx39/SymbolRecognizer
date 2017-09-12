package recognizer.symbol;


import java.util.List;

/**
 * This DeathlyHallows class represents a deathly hallows sign as a composite
 * symbol. It contains a triangle, a circle and a line segment.
 */
public class DeathlyHallows extends CompositeSymbolAbstract {

  public DeathlyHallows(List<Symbol> symbols) {
    super();
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a DH sign.");
    } else {
      this.symbols.addAll(symbols);
    }
  }

  @Override
  public Symbol copy() {
    return new DeathlyHallows(this.getComponents());
  }

  @Override
  public String toString() {
    return "(DeathlyHallows " + symbols.get(0).toString() + " "
            + symbols.get(1).toString() + " "
            + symbols.get(2).toString() + ")";
  }

  @Override
  public boolean canForm(List<Symbol> symbols) {
    Triangle t = null;
    Circle c = null;
    Line l = null;
    for (Symbol symbol : symbols) {
      if (symbol instanceof Triangle) {
        t = (Triangle) symbol;
      } else if (symbol instanceof Circle) {
        c = (Circle) symbol;
      } else if (symbol instanceof Line) {
        l = (Line) symbol;
      }
    }
    if (t == null || c == null || l == null) {
      return false;
    }
    double delta = c.getRadius() * 2 * imprecision;

    // the circle is inside the triangle
    IPoint center = c.getCenter();
    Line side1 = (Line) t.getComponents().get(0);
    Line side2 = (Line) t.getComponents().get(1);
    Line side3 = (Line) t.getComponents().get(2);
    double d1 = center.distanceTo(side1);
    double d2 = center.distanceTo(side2);
    double d3 = center.distanceTo(side3);
    if (!Utility.sameDistance(d1, c.getRadius(), imprecision)
            && !Utility.sameDistance(d2, c.getRadius(), imprecision)
            && !Utility.sameDistance(d3, c.getRadius(), imprecision)) {
      return false;
    }

    PointSet ps = new PointSet(delta);
    ps.add(new DecoratorSymbolCenter(t).getCenter());
    ps.add(new DecoratorSymbolCenter(c).getCenter());
    if (ps.size() > 1) {
      return false;
    }
    ps.add(l.getEnd1());
    ps.add(l.getEnd2());
    if (ps. size() != 3) {
      return false;
    }
    Line bottom = null;
    int count = 0;
    if (Utility.isConnected(l, side1, imprecision)) {
      count++;
    } else {
      bottom = side1;
    }
    if (Utility.isConnected(l, side2, imprecision)) {
      count++;
    } else {
      bottom = side2;
    }
    if (Utility.isConnected(l, side3, imprecision)) {
      count++;
    } else {
      bottom = side3;
    }
    if (count != 2 || bottom == null) {
      return false;
    }
    if (!Utility.nearMiddle(bottom, l.getEnd1(), imprecision)
            && !Utility.nearMiddle(bottom, l.getEnd2(), imprecision)) {
      return false;
    }
    return true;
  }
}
