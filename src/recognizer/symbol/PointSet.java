package recognizer.symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class to canForm triangles.
 * A PointSet is a set of points. New points that has a distance with other
 * points in the set within delta cannot be added to the set.
 */
public class PointSet {
  private List<IPoint> points;
  private double delta;

  /**
   * Construct a PointSet object and set the delta.
   *
   * @param delta the shortest distance allow for points in the set
   */
  PointSet(double delta) {
    this.delta = delta;
    this.points = new ArrayList<>();
  }

  /**
   * Add a new point to the set. If this point is too close to other
   * points, it cannot be added.
   *
   * @param p the point to be added
   */
  void add(IPoint p) {
    for (IPoint point : points) {
      if (point.distanceTo(p) < delta) {
        return;
      }
    }
    points.add(p);
  }

  boolean has(IPoint p) {
    for (IPoint point : points) {
      if (point.distanceTo(p) < delta) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the size of the set.
   *
   * @return the size of the set
   */
  int size() {
    return points.size();
  }
}
