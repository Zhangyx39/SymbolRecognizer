package recognizer.symbol;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This Candy class represents a candy symbol as a composite symbol. It
 * contains two triangle and a circle.
 */
public class Candy extends CompositeSymbolAbstract {

  public Candy(List<Symbol> symbols) {
    super();
    if (!canForm(symbols)) {
      throw new IllegalArgumentException("Cannot form a DH sign.");
    } else {
      this.symbols.addAll(symbols);
    }
  }

  @Override
  public Symbol copy() {
    return new Candy(this.getComponents());
  }

  @Override
  public String toString() {
    return "(Candy " + symbols.get(0).toString() + " "
            + symbols.get(1).toString() + " "
            + symbols.get(2).toString() + ")";
  }

  @Override
  public boolean canForm(List<Symbol> symbols) {
    Triangle t1 = null;
    Triangle t2 = null;
    Circle c = null;
    for (Symbol symbol : symbols) {
      if (symbol instanceof Triangle) {
        if (t1 == null) {
          t1 = (Triangle) symbol;
        } else  {
          t2 = (Triangle) symbol;
        }
      } else if (symbol instanceof Circle) {
        c = (Circle) symbol;
      }
    }
    if (t1 == null || t2 == null || c == null) {
      return false;
    }
    double r = c.getRadius();
    IPoint cCenter = c.getCenter();
    IPoint t1Center = new DecoratorSymbolCenter(t1).getCenter();
    IPoint t2Center = new DecoratorSymbolCenter(t2).getCenter();

    List<Line> sides1 = new ArrayList<>();
    for (Symbol symbol : t1.getComponents()) {
      sides1.add((Line) symbol);
    }
    sides1.sort(Comparator.comparingDouble(cCenter::distanceTo));
    double d1 = 2 * t1Center.distanceTo(sides1.get(2));

    List<Line> sides2 = new ArrayList<>();
    for (Symbol symbol : t2.getComponents()) {
      sides2.add((Line) symbol);
    }
    sides2.sort(Comparator.comparingDouble(cCenter::distanceTo));
    double d2 = 2 * t2Center.distanceTo(sides2.get(2));

    return Utility.sameDistance(t1Center.distanceTo(cCenter), d1 + r,
            imprecision)
            && Utility.sameDistance(t2Center.distanceTo(cCenter), d2 + r,
            imprecision)
            && Utility.sameDistance(t1Center.distanceTo(t2Center),
            d1 + r * 2 + d2, imprecision);
  }
}
