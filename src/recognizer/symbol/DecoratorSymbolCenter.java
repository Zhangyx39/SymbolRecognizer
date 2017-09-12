package recognizer.symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * the decorator SymbolCenter functional class adds the functionality of
 * calculating the center positions to all symbols without alternating its
 * original structure. It uses composition by storing symbol as a delegate
 * instance variable. This decorator design pattern allows easy addition of
 * functionality without changing codes of the old design.
 */
public class DecoratorSymbolCenter extends Decorator {

  public DecoratorSymbolCenter(Symbol sym) {
    super(sym);
  }

  /**
   * Calculates center points for each symbol.
   *
   * @return cent point in x,y double
   */
  public IPoint getCenter() {
    // a line
    if (delegate instanceof Line) {
      IPoint a = ((Line) delegate).getEnd1();
      IPoint b = ((Line) delegate).getEnd2();
      double centerx = (a.getX() + b.getX()) / 2;
      double centery = (a.getY() + b.getY()) / 2;
      return new Point(centerx, centery);
    }
    // a circle
    if (delegate instanceof Circle) {
      return ((Circle) delegate).getCenter();
    }

    // a snow man
    if (delegate instanceof Snowman) {
      Symbol midCircle = ((Snowman) delegate).getComponents().get(1);
      return ((Circle) midCircle).getCenter();
    }

    // any triangle, get the x and y average of all vertices.
    if (delegate instanceof EquilateralTriangle
            || delegate instanceof Triangle
            || delegate instanceof Rectangle) {
      List<Symbol> list = ((CompositeSymbol) delegate).getComponents();
      List<Double> xSet = new ArrayList<>();
      List<Double> ySet = new ArrayList<>();

      for (Symbol l : list) {
        xSet.add(((Line) l).getEnd1().getX());
        xSet.add(((Line) l).getEnd2().getX());
        ySet.add(((Line) l).getEnd1().getY());
        ySet.add(((Line) l).getEnd2().getY());
      }

      double sumX = 0;
      double sumY = 0;
      for (Double d : xSet) {
        sumX = sumX + d;
      }
      for (Double d : ySet) {
        sumY = sumY + d;
      }

      return new Point(sumX / xSet.size(), sumY / ySet.size());
    }
    if (delegate instanceof DeathlyHallows) {
      return new DecoratorSymbolCenter(((DeathlyHallows) delegate)
              .getComponents().get(0)).getCenter();
    }

    if (delegate instanceof Candy) {
      return new DecoratorSymbolCenter(((Candy) delegate)
              .getComponents().get(2)).getCenter();
    }
    return null;
  }
}
