import org.junit.Before;
import org.junit.Test;

import java.util.List;

import recognizer.model.IModel;
import recognizer.model.Model;
import recognizer.symbol.Circle;
import recognizer.symbol.Line;
import recognizer.symbol.Point;
import recognizer.symbol.Symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test cases for the IModel interface.
 */
public class IModelTest {

  private IModel model;

  /**
   * Set up some useful variables.
   */
  @Before
  public void setup() {
    model = new Model();
  }

  /**
   * If a null point is inputted, no exception is expected.
   */
  @Test
  public void addPointNullTest() {
    try {
      model.addPoint(null);
    }
    catch (Exception e) {
      fail();
    }
  }

  /**
   * Expect a empty list when no symbol is recognized.
   */
  @Test
  public void getSymbolsEmptyTest() {
    List<Symbol> symbols = model.getSymbols();
    assertEquals(0, symbols.size());
  }

  /**
   * Recognize a line segment successfully.
   */
  @Test
  public void recognizeLineTest() {
    model.addPoint(new Point(0, 0));
    model.addPoint(new Point(5, 0));
    model.addPoint(new Point(10, 0));
    model.recognize();

    List<Symbol> symbols = model.getSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);
  }

  /**
   * Recognize a circle successfully.
   */
  @Test
  public void recognizeCircleTest() {
    model.addPoint(new Point(5, 0));
    model.addPoint(new Point(0, 5));
    model.addPoint(new Point(-5, 0));
    model.addPoint(new Point(0, -5));
    model.recognize();

    List<Symbol> symbols = model.getSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Circle);
  }
}