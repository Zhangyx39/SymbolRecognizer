package recognizer.symbol;

/**
 * Decorator to adding a name string to all symbols.
 * The functional class uses decorator design pattern to add functionality to a class
 * without alternating its original structure.
 * It uses a symbol object as an instance variable and returns string names acoordingly.
 */
public class DecoratorSymbolName extends Decorator {

  public DecoratorSymbolName(Symbol sym) {
    super(sym);
  }

  /**
   * Calculates center points for each symbol.
   *
   * @return cent point in x,y double
   */
  public String getNameString() {
    if (delegate instanceof Line) {
      return "Line";
    }
    if (delegate instanceof Circle) {
      return "Circle";
    }

    if (delegate instanceof Snowman) {
      return "Snowman";
    }
    if (delegate instanceof EquilateralTriangle) {
      return "Equilateral Triangle";
    }
    if (delegate instanceof Triangle) {
      return "Triangle";
    }
    if (delegate instanceof Rectangle) {
      return "Rectangle";
    }
    if (delegate instanceof DeathlyHallows) {
      return "Deathly Hallows";
    }
    if (delegate instanceof Candy) {
      return "Candy";
    }
    return null;
  }
}
