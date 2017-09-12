package recognizer.symbol;

/**
 * This class represents a 2D-point in the Cartesian coordinate system. It
 * represents a point by its x and y coordinates in double.
 */
public class Point implements IPoint {
  private final double x;
  private final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public IPoint copy() {
    return new Point(this.x, this.y);
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public double distanceTo(IPoint other) {
    return Math.sqrt((x - other.getX()) * (x - other.getX())
            + (y - other.getY()) * (y - other.getY()));
  }

  @Override
  public double distanceTo(Line l) {
    double x1 = l.getEnd1().getX();
    double y1 = l.getEnd1().getY();
    double x2 = l.getEnd2().getX();
    double y2 = l.getEnd2().getY();
    double a = y1 - y2;
    double b = x2 - x1;
    double c = y2 * x1 - y1 * x2;
    double d = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
    return d;
  }

  /**
   * The format of the string is: (x.xx, y.yy)
   *
   * @return a formatted string
   */
  @Override
  public String toString() {
    return String.format("(%.2f, %.2f)", x, y);
  }
}
