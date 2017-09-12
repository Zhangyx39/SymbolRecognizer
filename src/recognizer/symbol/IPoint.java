package recognizer.symbol;

/**
 * This interface requires all methods that a 2D-Point should support.
 */
public interface IPoint {
  /**
   * Get a copy of the point.
   * @return a new point with same x and y coordinates.
   */
  IPoint copy();

  /**
   * Get the x coordinate of the point.
   * @return x coordinate in double
   */
  double getX();

  /**
   * Get the y coordinate of the point.
   * @return y coordinate in double
   */
  double getY();

  /**
   * Compute the distance from this point to another point.
   * @param other another point
   * @return the distance in double
   */
  double distanceTo(IPoint other);

  /**
   * Compute the distance from this point to a line
   * @param l a line
   * @return the distance in double
   */
  double distanceTo(Line l);
}
