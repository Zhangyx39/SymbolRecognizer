package recognizer.symbol;

/**
 * This package-private Utility class contains some static methods that is
 * needed for the recognition process. To make things clear and to avoid writing
 * duplicate codes, Utility methods in put in the class as static methods. More
 * methods are expected in the future.
 */
public class Utility {

  static boolean sameDistance(double d1, double d2, double imprecision) {
    return Math.abs(d1 - d2) / d2 < imprecision;
  }

  static boolean sameLength(Line l1, Line l2, double delta) {
    double length1 = l1.length();
    double length2 = l2.length();
    return Math.abs(length1 - length2) < delta;
  }

  static boolean isParallel(Line l1, Line l2, double imprecision) {
    return Math.abs(l1.angle() - l2.angle()) < 2 * Math.PI * imprecision
            || Math.abs(Math.abs(l1.angle() - l2.angle()) - Math.PI)
            < 2 * Math.PI * imprecision;
  }

  static boolean isPerpendicular(Line l1, Line l2, double imprecision) {
    return Math.abs(Math.abs(l1.angle() - l2.angle()) - Math.PI / 2)
            < Math.PI * imprecision;
  }

  static boolean isConnected(Line l1, Line l2, double imprecision) {
    double delta = (l1.length() + l2.length()) * imprecision / 2;
    PointSet ps = new PointSet(delta);
    ps.add(l1.getEnd1());
    ps.add(l1.getEnd2());
    if (ps.has(l2.getEnd1())) {
      return !ps.has(l2.getEnd2());
    } else {
      return ps.has(l2.getEnd2());
    }
  }

  static boolean samePoint(IPoint p1, IPoint p2, double delta) {
    return p1.distanceTo(p2) < delta;
  }

  static boolean nearMiddle(Line l, IPoint p, double imprecision) {
    IPoint center = new DecoratorSymbolCenter(l).getCenter();
    double delta = l.length() * imprecision;
    return samePoint(center, p, delta);
  }
}
