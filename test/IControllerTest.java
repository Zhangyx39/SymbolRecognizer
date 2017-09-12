
import org.junit.Before;
import org.junit.Test;

import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import recognizer.controller.Controller;
import recognizer.controller.IController;
import recognizer.model.IModel;
import recognizer.model.Model;
import recognizer.symbol.Circle;
import recognizer.symbol.EquilateralTriangle;
import recognizer.symbol.IPoint;
import recognizer.symbol.Line;
import recognizer.symbol.Point;
import recognizer.symbol.Snowman;
import recognizer.symbol.Symbol;
import recognizer.symbol.Triangle;
import recognizer.view.IView;
import recognizer.view.View;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the controller.
 * <p>Recognize</p>
 * - recognizeNoPoint
 * - recognizeOnePoint
 *
 * <p>Circle</p>
 * - perfectCircleAnticlockwiseTest
 * - perfectCircleClockwiseTest
 * - ImperfectCircleTest
 * - BrokenCircleTest
 *
 * <p>Line Segment</p>
 * - perfectLineHorizontalTest
 * - perfectLineVerticalTest
 * - perfectLineSlantTest
 * - imperfectLineTest
 * - arcRecognizedAsLineTest
 *
 * <p>Snowman</p>
 * - snowmanSuccessTest
 * - imperfectSnowmanTest
 *
 * <p>Triangle</p>
 * - triangleSuccessTest
 * - imperfectTriangleTest
 *
 * <p>Equilateral Triangle</p>
 * - equilateralTriangleSuccessTest
 * - imperfectEquilateralTriangleTest
 *
 * <p>GetSymbols</p>
 * - getSymbolsEmptyTest
 * - multiSymbolsTest
 *
 * <p>GetPoints</p>
 * - getPointsEmptyTest
 * - getPointsTest
 * - getPointsAfterRecognizeTest
 *
 * <p>Null Input</p>
 * - nullViewTest
 * - nullModelTest
 * - nullPointTest
 *
 * <p>Mock View</p>
 * - mockViewConstructTest
 * - mockViewTest
 *
 * <p>Helper Methods</p>
 * - circleGenerator
 * - lineSegmentGenerator
 *
 * <p>Helper Class</p>
 * - MockView
 */

public class IControllerTest {

  private IController controllerDefault;
  private IModel modelDefault;
  private IView viewDefault;
  private MockView mockView;


  /**
   * Set up instances that can be used in many tests.
   */
  @Before
  public void setUp() {
    viewDefault = new View(200, 200);
    modelDefault = new Model();
    controllerDefault = new Controller(modelDefault, viewDefault);
    mockView = new MockView();
  }

  /**
   * Test that when no point is added and the recognized() method is called.
   * No exception is expected and no symbol can be recognized.
   */
  @Test
  public void recognizeNoPoint() {
    try {
      controllerDefault.recognize();
      List<Symbol> symbols = controllerDefault.getAllSymbols();
      assertTrue(symbols.isEmpty());
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * When only one point is added, no exception is expected and no symbol is
   * recognized.
   */
  @Test
  public void recognizeOnePoint() {
    controllerDefault.addPoint(new Point(10, 10));
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertTrue(symbols.isEmpty());
  }

  /**
   * Generate a list of points that are perfectly on a circle. Try to
   * recognize a circle. This circle should almost be the same as the one we
   * used to generate the points. The points are drawn in anticlockwise order.
   */
  @Test
  public void perfectCircleAnticlockwiseTest() {
    // Create 100 points that is on the circle:
    // (x - 3)^2 + (y - 4)^2 = 100
    List<IPoint> points = circleGenerator(3, 4, 10, 100, 0);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one circle in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Circle);

    // The recognized circle should almost be the same as expected.
    Circle c = (Circle) symbols.get(0);
    assertEquals(3, c.getCenter().getX(), 0.01);
    assertEquals(4, c.getCenter().getY(), 0.01);
    assertEquals(10, c.getRadius(), 0.01);
  }

  /**
   * Generate a list of points that are perfectly on a circle. Try to
   * recognize a circle. This circle should almost be the same as the one we
   * used to generate the points. The points are drawn in clockwise order.
   */
  @Test
  public void perfectCircleClockwiseTest() {
    // Create 100 points that is on the circle:
    // (x - 3)^2 + (y - 4)^2 = 100
    List<IPoint> points = circleGenerator(3, 4, 10, 100, 0);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }
    Collections.reverse(points);

    // After recognition, there should be only one circle in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Circle);

    // The recognized circle should almost be the same as expected.
    Circle c = (Circle) symbols.get(0);
    assertEquals(3, c.getCenter().getX(), 0.01);
    assertEquals(4, c.getCenter().getY(), 0.01);
    assertEquals(10, c.getRadius(), 0.01);
  }

  /**
   * Generate a list of points that are imperfectly on a circle. Try to
   * recognize a circle. This circle should not be greatly different from the
   * one we used to generate the points.
   */
  @Test
  public void imperfectCircleTest() {
    // Create 100 points that is near the circle:
    // (x - 3)^2 + (y - 4)^2 = 100
    // Each point is randomly shifted from the perfect position by at most
    // 10% of the radius.
    List<IPoint> points = circleGenerator(3, 4, 10, 100, 1);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one circle in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Circle);

    // The recognized circle should not be greatly different from the one we
    // used to generate the points.
    Circle c = (Circle) symbols.get(0);
    assertEquals(3, c.getCenter().getX(), 1);
    assertEquals(4, c.getCenter().getY(), 1);
    assertEquals(10, c.getRadius(), 2);
  }

  /**
   * Generate a list of points that are perfectly on a circle. Cut one
   * fourth of the circle to form a broken circle. Try to recognize a circle.
   * Since no one will draw a line segment with these points, this is
   * intended to be a circle. This circle should almost be the same as the
   * one we used to generate the points.
   */
  @Test
  public void BrokenCircleTest() {
    // Create 100 points that is on the circle:
    // (x - 3)^2 + (y - 4)^2 = 100
    // Get the first 75 points to form a broken circle.
    List<IPoint> points = circleGenerator(3, 4, 10, 100, 0);
    for (int i = 0; i < 75; i++) {
      controllerDefault.addPoint(points.get(i));
    }

    // After recognition, there should be only one circle in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Circle);

    // The recognized circle should almost be the same as expected.
    Circle c = (Circle) symbols.get(0);
    assertEquals(3, c.getCenter().getX(), 0.01);
    assertEquals(4, c.getCenter().getY(), 0.01);
    assertEquals(10, c.getRadius(), 0.01);
  }

  /**
   * Generate a list of points that are perfectly on a horizontal line segment.
   * Try to recognize a line segment. This line segment should almost be the
   * same as the one we used to generate the points.
   */
  @Test
  public void perfectLineHorizontalTest() {
    // Create 100 points that is perfectly on a horizontal line segment:
    // (-5, 5) -> (5, 5)
    List<IPoint> points = lineGenerator(-5, 5, 5, 5, 100, 0);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one line segment in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);

    // The recognized line segment should almost be the same as the one we
    // used to generate the points.
    Line l = (Line) symbols.get(0);
    IPoint end1 = l.getEnd1();
    IPoint end2 = l.getEnd2();
    IPoint leftEnd = end1.getX() < end2.getX() ? end1 : end2;
    IPoint rightEnd = end1.getX() < end2.getX() ? end2 : end1;
    assertEquals(-5, leftEnd.getX(), 0.01);
    assertEquals(5, leftEnd.getY(), 0.01);
    assertEquals(5, rightEnd.getX(), 0.01);
    assertEquals(5, rightEnd.getY(), 0.01);
  }

  /**
   * Generate a list of points that are perfectly on a vertical line segment.
   * Try to recognize a line segment. This line segment should almost be the
   * same as the one we used to generate the points.
   */
  @Test
  public void perfectLineVerticalTest() {
    // Create 100 points that is perfectly on a vertical line segment:
    // (5, -5) -> (5, 5)
    List<IPoint> points = lineGenerator(5, -5, 5, 5, 100, 0);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one line segment in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);

    // The recognized line segment should almost be the same as the one we
    // used to generate the points.
    Line l = (Line) symbols.get(0);
    IPoint end1 = l.getEnd1();
    IPoint end2 = l.getEnd2();
    IPoint upperEnd = end1.getY() > end2.getY() ? end1 : end2;
    IPoint lowerEnd = end1.getY() > end2.getY() ? end2 : end1;
    assertEquals(5, upperEnd.getX(), 0.01);
    assertEquals(5, upperEnd.getY(), 0.01);
    assertEquals(5, lowerEnd.getX(), 0.01);
    assertEquals(-5, lowerEnd.getY(), 0.01);
  }

  /**
   * Generate a list of points that are perfectly on a slant line segment.
   * Try to recognize a line segment. This line segment should almost be the
   * same as the one we used to generate the points.
   */
  @Test
  public void perfectLineSlantTest() {
    // Create 100 points that is perfectly on a slant line segment:
    // (-5, -5) -> (5, 5)
    List<IPoint> points = lineGenerator(-5, -5, 5, 5, 100, 0);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one line segment in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);

    // The recognized line segment should almost be the same as the one we
    // used to generate the points.
    Line l = (Line) symbols.get(0);
    IPoint end1 = l.getEnd1();
    IPoint end2 = l.getEnd2();
    IPoint upperEnd = end1.getY() > end2.getY() ? end1 : end2;
    IPoint lowerEnd = end1.getY() > end2.getY() ? end2 : end1;
    assertEquals(5, upperEnd.getX(), 0.01);
    assertEquals(5, upperEnd.getY(), 0.01);
    assertEquals(-5, lowerEnd.getX(), 0.01);
    assertEquals(-5, lowerEnd.getY(), 0.01);
  }

  /**
   * Generate a list of points that are imperfectly on a line segment.
   * Try to recognize a line segment. This line segment should almost be the
   * same as the one we used to generate the points.
   */
  @Test
  public void imperfectLineTest() {
    // Create 100 points that is imperfectly on a slant line segment:
    // (-5, -5) -> (5, 5)
    // Each point is randomly shifted from the perfect position by at most
    // 10% of the length of the line segment.
    List<IPoint> points = lineGenerator(-5, -5, 5, 5, 100, 1);
    for (IPoint point : points) {
      controllerDefault.addPoint(point);
    }

    // After recognition, there should be only one line segment in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);

    // The recognized line segment should not be greatly different from the
    // one we used to generate the points.
    Line l = (Line) symbols.get(0);
    IPoint end1 = l.getEnd1();
    IPoint end2 = l.getEnd2();
    IPoint upperEnd = end1.getY() > end2.getY() ? end1 : end2;
    IPoint lowerEnd = end1.getY() > end2.getY() ? end2 : end1;
    assertEquals(5, upperEnd.getX(), 1);
    assertEquals(5, upperEnd.getY(), 1);
    assertEquals(-5, lowerEnd.getX(), 1);
    assertEquals(-5, lowerEnd.getY(), 1);
  }

  /**
   * Generate a list of points that are on an arc of 1/4 circle.
   * Try to recognize a line segment. Since no one will want to draw a circle
   * with only one fourth of it, it is intended to be a line segment.
   */
  @Test
  public void arcRecognizedAsLineTest() {
    // Create 100 points that is on the circle:
    // (x - 3)^2 + (y - 4)^2 = 100
    // Get the first 1/4 of the points to form an arc.
    List<IPoint> points = circleGenerator(3, 4, 10, 100, 0);
    for (int i = 0; i < 25; i++) {
      controllerDefault.addPoint(points.get(i));
    }

    // After recognition, there should be only one line segment in the list.
    controllerDefault.recognize();
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Line);
  }

  /**
   * Draw a snowman with three circles. Test the controller that the snowman
   * is recognized successfully.
   */
  @Test
  public void snowmanSuccessTest() {
    // Draw three circles:
    // circleL: x: 0, y: 0, r: 5
    // circleM: x: 0, y: 7, r: 2
    // circleS: x: 0, y: 10, r: 1
    List<IPoint> circleL = circleGenerator(0, 0, 5, 100, 0);
    List<IPoint> circleM = circleGenerator(0, 7, 2, 100, 0);
    List<IPoint> circleS = circleGenerator(0, 10, 1, 100, 0);
    for (IPoint p : circleL) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleM) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleS) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should perfectly form a snowman.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Snowman);
  }

  /**
   * Draw a snowman with three circles. These circles are slightly away from
   * the perfect positions. Test the controller that the snowman is
   * recognized successfully.
   */
  @Test
  public void imperfectSnowmanTest() {
    // Draw three circles:
    // circleL: x: 0, y: 0, r: 4.9
    // circleM: x: 0.1, y: 7, r: 2
    // circleS: x: 0, y: 10.1, r: 1
    List<IPoint> circleL = circleGenerator(0, 0, 4.9, 100, 0);
    List<IPoint> circleM = circleGenerator(0.1, 7, 2, 100, 0);
    List<IPoint> circleS = circleGenerator(0, 10.1, 1, 100, 0);
    for (IPoint p : circleL) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleM) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleS) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should also form a snowman.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Snowman);
  }

  /**
   * Draw a triangle with three line segments. Test the controller that the
   * triangle is recognized successfully.
   */
  @Test
  public void triangleSuccessTest() {
    // Draw three line segments:
    // side1: (0, 0) -> (3, 0)
    // side2: (0, 0) -> (0, 4)
    // side3: (0, 4) -> (3, 0)
    List<IPoint> side1 = lineGenerator(0, 0, 3, 0, 100, 0);
    List<IPoint> side2 = lineGenerator(0, 0, 0, 4, 100, 0);
    List<IPoint> side3 = lineGenerator(0, 4, 3, 0, 100, 0);
    for (IPoint p : side1) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side2) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side3) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should perfectly form a triangle.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Triangle);
  }

  /**
   * Draw a Triangle with three line segments. These line segments are slightly
   * away from the perfect positions. Test the controller that the triangle
   * is recognized successfully.
   */
  @Test
  public void imperfectTriangleTest() {
    // Draw three line segments that are slightly away from where they should
    // be.
    // side1: (0, 0) -> (2.9, 0)
    // side2: (0, 0) -> (0, 4.1)
    // side3: (0, 3.9) -> (3.1, 0)
    List<IPoint> side1 = lineGenerator(0, 0, 2.9, 0, 100, 0);
    List<IPoint> side2 = lineGenerator(0, 0, 0, 4.1, 100, 0);
    List<IPoint> side3 = lineGenerator(0, 3.9, 3.1, 0, 100, 0);
    for (IPoint p : side1) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side2) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side3) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should also be recognized as a triangle.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Triangle);
  }

  /**
   * Draw a equilateral triangle with three line segments with same length.
   * Test the controller that the equilateral triangle is recognized
   * successfully.
   */
  @Test
  public void equilateralTriangleSuccessTest() {
    // Draw three line segments with same length:
    // side1: (0, 0) -> (4, 0)
    // side2: (0, 0) -> (2, 3.46)
    // side3: (4, 0) -> (2, 3.46)
    List<IPoint> side1 = lineGenerator(0, 0, 4, 0, 100, 0);
    List<IPoint> side2 = lineGenerator(0, 0, 2, 3.46, 100, 0);
    List<IPoint> side3 = lineGenerator(4, 0, 2, 3.46, 100, 0);
    for (IPoint p : side1) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side2) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side3) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should perfectly form a equilateral triangle.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof EquilateralTriangle);
  }

  /**
   * Draw a equilateral triangle with three line segments. These line segments
   * are slightly away from the perfect positions. Test the controller that
   * the triangle is recognized successfully.
   */
  @Test
  public void imperfectEquilateralTriangleTest() {
    // Draw three line segments with same length. They are slightly moved
    // from the perfect position.
    // side1: (0, 0) -> (4.1, 0)
    // side2: (0, 0) -> (2.1, 3.46)
    // side3: (3.9, 0) -> (1.9, 3.46)
    List<IPoint> side1 = lineGenerator(0, 0, 4.1, 0, 100, 0);
    List<IPoint> side2 = lineGenerator(0, 0, 2.1, 3.46, 100, 0);
    List<IPoint> side3 = lineGenerator(3.9, 0, 1.9, 3.46, 100, 0);
    for (IPoint p : side1) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side2) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side3) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // These circles should also form a equilateral triangle.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof EquilateralTriangle);
  }

  /**
   * When nothing is drawn, we should get a empty list of symbols.
   */
  @Test
  public void getSymbolsEmptyTest() {
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertTrue(symbols.isEmpty());
  }

  /**
   * Draw one triangle and one snowman.
   * The order to draw: side1, side2, circleL, circleM, side3, circleS.
   * One triangle and one snowman should be recognized.
   */
  @Test
  public void multiSymbolsTest() {
    // The triangle:
    // side1: (0, 0) -> (3, 0)
    // side2: (0, 0) -> (0, 4)
    // side3: (0, 4) -> (3, 0)
    List<IPoint> side1 = lineGenerator(0, 0, 3, 0, 100, 0);
    List<IPoint> side2 = lineGenerator(0, 0, 0, 4, 100, 0);
    List<IPoint> side3 = lineGenerator(0, 4, 3, 0, 100, 0);
    // The snowman:
    // circleL: x: 0, y: 0, r: 5
    // circleM: x: 0, y: 7, r: 2
    // circleS: x: 0, y: 10, r: 1
    List<IPoint> circleL = circleGenerator(0, 0, 5, 100, 0);
    List<IPoint> circleM = circleGenerator(0, 7, 2, 100, 0);
    List<IPoint> circleS = circleGenerator(0, 10, 1, 100, 0);
    for (IPoint p : side1) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side2) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleL) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleM) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : side3) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();
    for (IPoint p : circleS) {
      controllerDefault.addPoint(p);
    }
    controllerDefault.recognize();

    // One triangle and one snowman should be recognized.
    List<Symbol> symbols = controllerDefault.getAllSymbols();
    assertEquals(2, symbols.size());
    assertTrue(symbols.get(0) instanceof Triangle);
    assertTrue(symbols.get(1) instanceof Snowman);
  }

  /**
   * When no point has been added, we get an empty list of points.
   */
  @Test
  public void getPointsEmptyTest() {
    List<IPoint> points = controllerDefault.getPoints();
    assertTrue(points.isEmpty());
  }

  /**
   * Add three points to the controller, we should get a list of three points.
   */
  @Test
  public void getPointsTest() {
    IPoint p1 = new Point(0, 0);
    IPoint p2 = new Point(1, 1);
    IPoint p3 = new Point(2, 2);
    controllerDefault.addPoint(p1);
    controllerDefault.addPoint(p2);
    controllerDefault.addPoint(p3);
    List<IPoint> points = controllerDefault.getPoints();
    assertEquals(3, points.size());
    assertEquals(p1.toString(), points.get(0).toString());
    assertEquals(p2.toString(), points.get(1).toString());
    assertEquals(p3.toString(), points.get(2).toString());
  }

  /**
   * After recognition, the points in the list should be removed. Thus we get
   * an empty list of points.
   */
  @Test
  public void getPointsAfterRecognizeTest() {
    IPoint p1 = new Point(0, 0);
    IPoint p2 = new Point(1, 1);
    IPoint p3 = new Point(2, 2);
    controllerDefault.addPoint(p1);
    controllerDefault.addPoint(p2);
    controllerDefault.addPoint(p3);
    controllerDefault.recognize();
    List<IPoint> points = controllerDefault.getPoints();
    assertTrue(points.isEmpty());
  }

  /**
   * When trying to use a null view to construct a controller, illegal
   * argument exception is expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullViewTest() {
    IController controller = new Controller(modelDefault, null);
  }

  /**
   * When trying to use a null model to construct a controller, illegal
   * argument exception is expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullModelTest() {
    IController controller = new Controller(null, viewDefault);
  }

  /**
   * When a null point is added, no symbol should be recognized and no
   * exception is expected.
   */
  @Test
  public void nullPointTest() {
    try {
      controllerDefault.addPoint(null);
      controllerDefault.recognize();
      List<Symbol> symbols = controllerDefault.getAllSymbols();
      assertTrue(symbols.isEmpty());
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * When the controller is constructed, the default MouseListener is set to
   * the mock view. The mock view should detect it and record a message.
   */
  @Test
  public void mockViewConstructTest() {
    Controller controller = new Controller(modelDefault, mockView);
    String expected = "Set MouseListener.\n";
    assertEquals(expected, mockView.report());
  }

  /**
   * Construct a controller. Set the mouseListener again.
   * Simulate a series of mouse events by dragging the mouse over three
   * points and release it.
   * Every time the mouse is dragged to a point, the mock view should
   * detect a call to the repaint method and record a message.
   * When the mouse is released, the mock view should also detect a call to
   * the repaint method and record a message.
   */
  @Test
  public void mockViewTest() {
    Controller controller = new Controller(modelDefault, mockView);
    // Set the MouseListener again with my objects.
    Controller.MousePressAndDragFunction pressAndDragFunction = controller.new
        MousePressAndDragFunction();
    Controller.MouseReleaseFunction releaseFunction = controller.new
        MouseReleaseFunction();

    Controller.MyMouseAdapter adapter = controller.new MyMouseAdapter(releaseFunction,
        pressAndDragFunction, pressAndDragFunction);

    controller.setMouseListener(adapter);
    // Three mouse events.
    MouseEvent e1 = new MouseEvent(new Label(), 0, 0, 0,
        0, 0, 0, true, MouseEvent.BUTTON1);
    MouseEvent e2 = new MouseEvent(new Label(), 0, 0, 0,
        1, 1, 0, true, MouseEvent.BUTTON1);
    MouseEvent e3 = new MouseEvent(new Label(), 0, 0, 0,
        2, 2, 0, true, MouseEvent.BUTTON1);
    // Drag the mouse and release it.
    adapter.mousePressed(e1);
    adapter.mouseDragged(e2);
    adapter.mouseDragged(e3);
    adapter.mouseReleased(e3);
    String expected = "Set MouseListener.\n" // By default.
        + "Set MouseListener.\n" // I set it again.
        + "Paint 1 points and 0 symbols.\n" // Paint the first point.
        + "Paint 2 points and 0 symbols.\n" // Paint the points.
        + "Paint 3 points and 0 symbols.\n" // Paint the points
        + "Paint 0 points and 1 symbols.\n"; // Paint the recognized symbol.
    assertEquals(expected, mockView.report());
  }

  /**
   * Generate a list of points that are on a circle, or near the circle.
   *
   * @param centerX the x coordinate of the center of the circle
   * @param centerY the y coordinate of the center of the circle
   * @param radius the radius of the circle
   * @param n the number of points to be generated
   * @param delta the distance allowed from the circle of each point
   * @return a list points
   */
  private static List<IPoint> circleGenerator(double centerX, double centerY,
      double radius, int n, double delta) {
    if (radius <= 0 || n < 2 || delta < 0) {
      throw new IllegalArgumentException("Wrong input.");
    }
    double theta = 0;
    double deltaTheta = 2 * Math.PI / n;
    List<IPoint> points = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      double x = centerX + radius * Math.cos(theta)
          + (Math.random() * delta * 2) - delta;
      double y = centerY + radius * Math.sin(theta)
          + (Math.random() * delta * 2) - delta;
      points.add(new Point(x, y));
      theta += deltaTheta;
    }
    return points;
  }

  /**
   * Generate a list of points that are on or near a line segment.
   *
   * @param x1 the x coordinate of one end of the line segment
   * @param y1 the y coordinate of one end of the line segment
   * @param x2 the x coordinate of another end of the line segment
   * @param y2 the y coordinate of another end of the line segment
   * @param n the number of points to be generated
   * @param delta the distance allowed from the line of each point
   * @return a list of points
   */
  private static List<IPoint> lineGenerator(double x1, double y1,
      double x2, double y2,
      int n, double delta) {
    if (n < 2 || delta < 0) {
      throw new IllegalArgumentException("Wrong input.");
    }
    double deltaX = (x2 - x1) / (n - 1);
    double deltaY = (y2 - y1) / (n - 1);
    List<IPoint> points = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      double x = x1 + i * deltaX + (Math.random() * delta * 2) - delta;
      double y = y1 + i * deltaY + (Math.random() * delta * 2) - delta;
      points.add(new Point(x, y));
    }
    return points;
  }

  /**
   * A helper class to test the controller. Every time a method in the mock
   * view is called, it records a message. Final we can check the message we
   * get after interaction with the controller.
   */
  private class MockView implements IView {

    private StringBuilder recorder;

    public MockView() {
      this.recorder = new StringBuilder();
    }

    /**
     * Record a message.
     *
     * @param listener the input MouseAdapter
     */
    @Override
    public void setMouseListener(MouseAdapter listener) {
      recorder.append("Set MouseListener.\n");
    }

    /**
     * This method is used to paint a list of points and symbols in a real view.
     * We simply record a message with the number of points and symbols it is
     * asked to paint.
     *
     * @param points List of points
     * @param symbols List of symbols
     */
    @Override
    public void repaint(List<IPoint> points, List<Symbol> symbols) {
      recorder.append("Paint " + points.size() + " points and "
          + symbols.size() + " symbols.\n");
    }

    /**
     * Return the messages we got so far.
     *
     * @return a string
     */
    public String report() {
      return recorder.toString();
    }
  }
}
