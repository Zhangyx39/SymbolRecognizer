package recognizer.model;

import java.util.List;
import recognizer.symbol.Circle;
import recognizer.symbol.IPoint;

/**
 * Fits a circle, given points.
 * Edge cases: if any the divider, r, n, d is zero, the goodness = 0, max not fit value.
 */
public class CircleFitter extends FitterAbstract {

  public CircleFitter(List<IPoint> points) {
    super(points);
  }

  /**
   * This method sets the goodness of fit and basic symbol instance variable of the class.
   */
  public void fit() {
    //compute Sx, Sy, Sxy, Sxx, Syy, Sx2y2, Sxx2y2, Syx2y2
    int n = points.size();
    double sX = 0;
    double sY = 0;
    double sXY = 0;
    double sXX = 0;
    double sYY = 0;
    double sX2y2 = 0;
    double sXx2y2 = 0;
    double sYx2y2 = 0;
    for (IPoint p : points) {
      sX += p.getX();
      sY += p.getY();
      sXY += p.getX() * p.getY();
      sXX += p.getX() * p.getX();
      sYY += p.getY() * p.getY();
      sX2y2 += p.getX() * p.getX() + p.getY() * p.getY();
      sXx2y2 += p.getX() * (p.getX() * p.getX() + p.getY() * p.getY());
      sYx2y2 += p.getY() * (p.getX() * p.getX() + p.getY() * p.getY());
    }

    //compute d, da,db,dc
    double d = sXX * (n * sYY - sY * sY) - sXY * (n * sXY - sX * sY) + sX * (sXY * sY - sYY * sX);
    double da = sXx2y2 * (n * sYY - sY * sY) - sXY * (n * sYx2y2 - sX2y2 * sY)
        + sX * (sYx2y2 * sY - sYY * sX2y2);
    double db = sXX * (n * sYx2y2 - sX2y2 * sY) - sXx2y2 * (n * sXY - sX * sY)
        + sX * (sXY * sX2y2 - sYx2y2 * sX);
    double dc = sXX * (sYY * sX2y2 - sYx2y2 * sY) - sXY * (sXY * sX2y2 - sYx2y2 * sX)
        + sXx2y2 * (sXY * sY - sYY * sX);

    //cannot divide by 0, return 0, not fit, max not fit val.
    if (d == 0) {
      this.goodness = 0;
      return;
    }

    //compute a, b ,c
    double a = da / d;
    double b = db / d;
    double c = dc / d;

    //compute cx, cy, r
    double cx = a / 2;
    double cy = b / 2;
    double r = Math.sqrt(c + cx * cx + cy * cy);

    //set the symbol
    basicSymbol = new Circle(cx, cy, r);

    //cannot divide by 0, return 0, not fit, max not fit val.
    if (n == 0 || r == 0) {
      goodness = 0;
      return;
    }

    //compute d
    double m = 0;
    for (IPoint p : points) {
      double di = Math.abs((p.getX() - cx) * (p.getX() - cx)
          + (p.getY() - cy) * (p.getY() - cy) - (r * r));
      m += di;
    }

    //set goodness
    double q = Math.sqrt(m) / n;
    goodness = 1 - Math.min(1, q / r);
    System.out.println(goodness);
  }
}
