import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import recognizer.model.LineFitter;
import recognizer.symbol.IPoint;
import recognizer.symbol.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for line fitter.
 */
public class LineFitterTest {

  private List<IPoint> line45;
  private List<IPoint> line180;
  private List<IPoint> line90;
  private List<IPoint> line135;
  private List<IPoint> lineImperfect;
  private LineFitter fitter;

  /**
   * line45 is a line at 45 degrees.
   * line180 is a horizontal line.
   * line90 is a vertical line.
   * line135 is a the reverse of 45.
   */
  @Before
  public void setUp() {
    line45 = new ArrayList<>();
    line45.add(new Point(0, 0));
    line45.add(new Point(1, 1));
    line45.add(new Point(2, 2));
    line45.add(new Point(3, 3));
    line45.add(new Point(4, 4));

    line180 = new ArrayList<>();
    line180.add(new Point(0, 0));
    line180.add(new Point(1, 0));
    line180.add(new Point(2, 0));
    line180.add(new Point(3, 0));
    line180.add(new Point(4, 0));

    line90 = new ArrayList<>();
    line90.add(new Point(0, 0));
    line90.add(new Point(0, 1));
    line90.add(new Point(0, 2));
    line90.add(new Point(0, 3));
    line90.add(new Point(0, 4));

    line135 = new ArrayList<>();
    line135.add(new Point(4, 4));
    line135.add(new Point(3, 3));
    line135.add(new Point(2, 2));
    line135.add(new Point(1, 1));
    line135.add(new Point(0, 0));

    lineImperfect = new ArrayList<>();
    lineImperfect.add(new Point(0, 0));
    lineImperfect.add(new Point(1, 1));
    lineImperfect.add(new Point(2, 0));
    lineImperfect.add(new Point(3, 3));
    lineImperfect.add(new Point(4, 0));

  }

  /**
   * Fit lines successfully and correctly.
   */
  @Test
  public void getFittedLine() {
    fitter = new LineFitter(line45);
    assertEquals("(Line (0.00, 0.00) (4.00, 4.00))", fitter.getFittedBasicSymbol().toString());

    fitter = new LineFitter(line180);
    assertEquals("(Line (0.00, 0.00) (4.00, 0.00))", fitter.getFittedBasicSymbol().toString());

    fitter = new LineFitter(line90);
    assertEquals("(Line (-0.00, 0.00) (0.00, 4.00))", fitter.getFittedBasicSymbol().toString());

    fitter = new LineFitter(line135);
    assertEquals("(Line (0.00, 0.00) (4.00, 4.00))", fitter.getFittedBasicSymbol().toString());

  }

  /**
   * High goodness when the points make a perfect line.
   */
  @Test
  public void getGoodness() {
    fitter = new LineFitter(line45);
    assertTrue(fitter.getGoodness() > 0.9);

    fitter = new LineFitter(line180);
    assertTrue(fitter.getGoodness() > 0.9);

    fitter = new LineFitter(line90);
    assertTrue(fitter.getGoodness() > 0.9);

    fitter = new LineFitter(line135);
    assertTrue(fitter.getGoodness() > 0.9);
  }

  /**
   * Low goodness when the points are not in a line.
   */
  @Test
  public void getLowGoodness() {
    fitter = new LineFitter(lineImperfect);
    fitter.fit();
    assertTrue(fitter.getGoodness() < 0.5);
  }

}