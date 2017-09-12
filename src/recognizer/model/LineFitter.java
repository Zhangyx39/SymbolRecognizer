package recognizer.model;

import java.util.List;
import recognizer.symbol.IPoint;
import recognizer.symbol.Line;
import recognizer.symbol.Point;

/**
 * Fits a line of given points by calculating the degree of orientation to the y-axis.
 * When the origination is 90 or 270 degrees,
 * cos(theta) = 0, tan(theta) = sin(theta)/cos(theta) = infinity, the line is vertical.
 *
 * <p>Edge cases:
 * When computing q = 2Sxy/(sXX-sYY); tan(n) = q; tan(n) = sin(n)/cos(n)
 * division by zero, q = infinite, meaning cos(n)=0, so set n = 90 = pi/2 or 270 =3pi/2.
 *
 * <p>If two points are the same, cannot form a valid line, set goodness to 0, return null.
 */
public class LineFitter extends FitterAbstract {

  private double sXX;
  private double sYY;
  private double sXY;

  public LineFitter(List<IPoint> points) {
    super(points);
  }

  /**
   * This method sets the goodness of fit and basic symbol instance variable of the class.
   */
  public void fit() {
    //compute center points
    IPoint s = getCenter(points);

    //use center point to compute tan(theta) = q
    double q = computeQ(s);

    //compute theta = m = arctan(q)
    double m = computeM(q);

    //compute a= cos(m/2); b = sin(m/2);
    double a = Math.cos(m / 2);
    double b = Math.sin(m / 2);

    //avoid code duplication, using lambda function to find tMax and tMin.
    //map list of points to a(x-xAvg) + b(y-yAvg).
    double tMax = findTMax(a, b, s);
    double tMin = findTMin(a, b, s);

    //compute fitted line;
    setFittedLine(a, b, s, tMax, tMin);

    //find goodness of fit
    setGoodness(a, b, s, tMax, tMin);
  }

  /**
   * Computing the center points of given line.
   *
   * @param points List of points
   * @return center position
   */
  private IPoint getCenter(List<IPoint> points) {
    double sumX = 0;
    double sumY = 0;
    for (IPoint p : points) {
      sumX += p.getX();
      sumY += p.getY();
    }
    double avgX = sumX / points.size();
    double avgY = sumY / points.size();
    //System.out.println("Center: " + avgX + "," + avgY);
    return new Point(avgX, avgY);
  }

  /**
   * Compute the Q value of line fitting equation and set sXY, sXX, sYY for  later use.
   * If sXX == sYY, then Q value becomes Positive Infinity and later on theta becomes PI/2.
   *
   * @param s Center point
   * @return Q value as double
   */
  private double computeQ(IPoint s) {
    //compute sXY, sXX, sYY
    sXY = 0;
    sXX = 0;
    sYY = 0;
    for (IPoint p : points) {
      sXY += (p.getX() - s.getX()) * (p.getY() - s.getY());
      sXX += (p.getX() - s.getX()) * (p.getX() - s.getX());
      sYY += (p.getY() - s.getY()) * (p.getY() - s.getY());
    }

    //compute q = 2Sxy/(sXX-sYY); tan(n) = q; tan(n) = sin(n)/cos(n)
    //division by zero, q = infinite,
    //meaning cos(n)=0, so n = 90 = pi/2 or 270 =3pi/2.
    if (sXX == sYY) {
      return Double.POSITIVE_INFINITY;
    } else {
      return 2 * sXY / (sXX - sYY);
    }
  }

  /**
   * Given Q value, compute the m degree of the line. If q is infinity, m becomes PI/2.
   * Otherwise m is either arctan(q) or arctan(q) + PI, which ever makes the function f(m) >0.
   *
   * @param q Q value
   * @return m degree in radians
   */
  private double computeM(double q) {
    double theta1;
    double theta2;
    //compute q = 2Sxy/(sXX-sYY); tan(n) = q; tan(n) = sin(n)/cos(n)
    //division by zero, meaning cos(n)=0, so n = 90 = pi/2 or 270 =3pi/2.
    if (q == Double.POSITIVE_INFINITY) {
      theta1 = Math.PI / 2;
    } else {
      theta1 = Math.atan(q);
    }
    theta2 = theta1 + Math.PI;

    //compute f(m) = 2Sxy sin(m) - (sYY - sXX)cos(m)
    double f1 = 2 * sXY * Math.sin(theta1) - (sYY - sXX) * Math.cos(theta1);
    double f2 = 2 * sXY * Math.sin(theta2) - (sYY - sXX) * Math.cos(theta2);

    //pick m that gives positive f(m)
    return (f1 > 0) ? theta1 : theta2;
  }

  /**
   * Setting the fitted line after calculation.
   * If two points end up being the same, return goodness = 0, and line null.
   */
  private void setFittedLine(double a, double b, IPoint s, double tMax, double tMin) {
    //find line segment

    //System.out.println("tMax:" + tMax + " tMin:" + tMin);
    IPoint p1 = new Point((s.getX() + tMin * a), (s.getY() + tMin * b));
    IPoint p2 = new Point((s.getX() + tMax * a), (s.getY() + tMax * b));

    //if two points are the same, cannot form a valid line, return null.
    //however, two points would never be the same given that there are more than 1 point
    //p1 == p2 when a = b= 0, sX = xY; a and b will never be zero at the same time.
    //since a = sine(theta) b = cos(theta).
    if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
      this.goodness = 0;
      return;
    }

    basicSymbol = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());

  }

  /**
   * Setting the goodness of fit after calculation.
   */
  public void setGoodness(double a, double b, IPoint s, double tMax, double tMin) {
    double tOMin = Double.MAX_VALUE;
    double tOMax = Double.MIN_NORMAL;
    for (IPoint p : points) {
      double t = b * (p.getX() - s.getX()) - a * (p.getY() - s.getY());
      tOMax = Math.max(tOMax, t);
      tOMin = Math.min(tOMin, t);
    }
    this.goodness = 1 - Math.min(1, (tOMax - tOMin) / (tMax - tMin));
  }

  /**
   * Find the max value given a , b and center s.
   */
  private double findTMax(double a, double b, IPoint s) {
    double tMax = Double.MIN_NORMAL;
    for (IPoint p : points) {
      double t = a * (p.getX() - s.getX()) + b * (p.getY() - s.getY());
      tMax = Math.max(tMax, t);
    }
    return tMax;
  }

  /**
   * Find the min value given a , b and center s.
   */
  private double findTMin(double a, double b, IPoint s) {
    double tMin = Double.MAX_VALUE;
    for (IPoint p : points) {
      double t = a * (p.getX() - s.getX()) + b * (p.getY() - s.getY());
      tMin = Math.min(tMin, t);
    }
    return tMin;
  }

}
