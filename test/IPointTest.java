import static org.junit.Assert.assertEquals;

import recognizer.symbol.IPoint;
import recognizer.symbol.Point;
import org.junit.Before;
import org.junit.Test;

/**
 * A test for point interface.
 * - object creation positive and negative numbers
 * - copy success
 * - getX getY success
 * - test distance to self is 0
 * - test distance success
 */
public class IPointTest {

  private IPoint testpoint;
  private IPoint testpointNeg;

  @Before
  public void setUp() {
    testpoint = new Point(2.0, 1.0);
    testpointNeg = new Point(-2.0, -1.0);
  }

  @Test
  public void copy() {
    IPoint copied = testpoint.copy();
    assertEquals(testpoint.getX(), copied.getX(), 0.00001);
    assertEquals(testpoint.getY(), copied.getY(), 0.00001);

    IPoint copiedNeg = testpointNeg.copy();
    assertEquals(testpointNeg.getX(), copiedNeg.getX(), 0.00001);
    assertEquals(testpointNeg.getY(), copiedNeg.getY(), 0.00001);

  }

  @Test
  public void getX() {
    assertEquals(2.0, testpoint.getX(), 0.00001);
    assertEquals(-2.0, testpointNeg.getX(), 0.00001);

  }

  @Test
  public void getY() {
    assertEquals(1.0, testpoint.getY(), 0.00001);
    assertEquals(-1.0, testpointNeg.getY(), 0.00001);
  }

  @Test
  public void testDistance() {
    assertEquals(Math.sqrt(20), testpoint.distanceTo(testpointNeg), 0.0001);
    assertEquals(Math.sqrt(0), testpoint.distanceTo(testpoint), 0.0001);
  }

  @Test
  public void testToString() {
    assertEquals("(2.00, 1.00)", testpoint.toString());
    assertEquals("(-2.00, -1.00)", testpointNeg.toString());
  }
}