import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import recognizer.model.CircleFitter;
import recognizer.symbol.Circle;
import recognizer.symbol.IPoint;
import recognizer.symbol.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Circle fitter test.
 */
public class CircleFitterTest {

  Circle c;
  List<IPoint> plist;
  CircleFitter fitter;

  /**
   * Set up some data.
   */
  @Before
  public void setUp() {
    c = new Circle(0, 0, 1);
    plist = new ArrayList<>();
    plist.add(new Point(0, 1));
    plist.add(new Point(0, -1));
    plist.add(new Point(1, 0));
    plist.add(new Point(-1, 0));
  }

  /**
   * Check that the fitted circle is the one we expected.
   */
  @Test
  public void getFittedCircle() {
    fitter = new CircleFitter(plist);
    assertEquals(c.toString(), fitter.getFittedBasicSymbol().toString());
  }

  /**
   * Goodness should be high.
   */
  @Test
  public void getGoodness() {
    fitter = new CircleFitter(plist);
    assertTrue(fitter.getGoodness() > 0.9);
  }
}