package recognizer.symbol;

/**
 * This class represents a circle by the center point and the radius of the
 * circle. A circle is a basic symbol. Many circles may form a composite symbol.
 */
public class Circle implements BasicSymbol {

  private final IPoint center;
  private final double radius;

  /**
   * Constructor for the circle class. If radius is not possible, throws exception.
   *
   * @param centerX center point x value
   * @param centerY center point y value
   * @param radius radius must be positive
   */
  public Circle(double centerX, double centerY, double radius) {
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius should be positive.");
    }
    this.center = new Point(centerX, centerY);
    this.radius = radius;
  }

  @Override
  public Symbol copy() {
    return new Circle(center.getX(), center.getY(), radius);
  }

  @Override
  public String toString() {
    return "(Circle " + center.toString() + String.format(" %.2f)", radius);
  }

  /**
   * Get the center point of the circle.
   *
   * @return the center point
   */
  public IPoint getCenter() {
    return center.copy();
  }

  /**
   * Get the radius of the circle.
   *
   * @return the radius in double
   */
  public double getRadius() {
    return radius;
  }
}
