import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Circle;
import recognizer.symbol.Line;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class for the basic symbol interface.
 *
 * <p>Circle:
 * - Object creation success
 * - Object creation fail, neg radius
 *
 * <p>Line:
 * - Object creation success
 * - Object creation fail, same two point not a line
 */
public class BasicSymbolTest {

  BasicSymbol circle;
  BasicSymbol line;
  BasicSymbol samePointline;

  @Before
  public void setUp() {
    circle = new Circle(1.0, 1.0, 1);
    line = new Line(1.0, 1.0, 2.0, 2.0);
  }

  @Test
  public void testSuccess() {
    try {
      BasicSymbol circle = new Circle(1, 1, 2);
      BasicSymbol line = new Line(1, 25, 6, 9);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegRadius() {
    BasicSymbol circleNeg = new Circle(1.0, 2.0, -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLineWithSamePoint() {
    samePointline = new Line(1.0, 1.0, 1.0, 1.0);
  }

  @Test
  public void testToCircleString() {
    assertEquals("(Circle (1.00, 1.00) 1.00)", circle.toString());
  }

  @Test
  public void testToLineString() {
    assertEquals("(Line (1.00, 1.00) (2.00, 2.00))", line.toString());
  }
}