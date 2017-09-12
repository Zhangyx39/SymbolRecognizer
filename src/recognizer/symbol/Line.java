package recognizer.symbol;

/**
 * This class represents a line segment by two end points. A line segment is
 * a basic symbol. Many line segments may form a composite symbol.
 */
public class Line implements BasicSymbol {

  private final IPoint end1;
  private final IPoint end2;

  /**
   * Constructor for making a line.
   * If point1 and point2 are the same, throws exception.
   *
   * @param x1 x val of point 1
   * @param y1 y val of point 1
   * @param x2 x val of point 2
   * @param y2 y val of point 2
   */
  public Line(double x1, double y1, double x2, double y2) {
    if (x1 == x2 && y1 == y2) {
      throw new IllegalArgumentException("Cannot create a line segment with "
          + "two same points.");
    }
    end1 = new Point(x1, y1);
    end2 = new Point(x2, y2);
  }

  @Override
  public Symbol copy() {
    return new Line(end1.getX(), end1.getY(), end2.getX(), end2.getY());
  }

  /**
   * Get the length of the line segment.
   *
   * @return the distance from one end to the other in double.
   */
  public double length() {
    return end1.distanceTo(end2);
  }

  /**
   * Get end point 1.
   *
   * @return a point object
   */
  public IPoint getEnd1() {
    return end1.copy();
  }

  /**
   * Get end point 2.
   *
   * @return a point object
   */
  public IPoint getEnd2() {
    return end2.copy();
  }

  /**
   * Measure the angle between the line segment and the horizontal line.
   * The result is within [0, pi) in double.
   *
   * @return the orientation of a line segment
   */
  public double angle() {
    double shadow = end1.getX() - end2.getX();
    shadow = end1.getY() > end2.getY() ? shadow : -shadow;
    double theta = Math.acos(shadow / length());
    if (Math.abs(theta - Math.PI) < 0.0001) {
      return 0;
    }
    return Math.acos(shadow / length());
  }

  @Override
  public String toString() {
    return "(Line " + end1.toString() + " " + end2.toString() + ")";
  }
}
