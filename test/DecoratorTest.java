import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Circle;
import recognizer.symbol.DecoratorSymbolCenter;
import recognizer.symbol.DecoratorSymbolName;
import recognizer.symbol.EquilateralTriangle;
import recognizer.symbol.IPoint;
import recognizer.symbol.Line;
import recognizer.symbol.Snowman;
import recognizer.symbol.Symbol;
import recognizer.symbol.Triangle;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Decorator as well as its subclass, DecoratorSymbolCenter and
 * DecoratorSymbolName.
 */
public class DecoratorTest {

  private Circle circle;
  private Line line;
  private Snowman snowman;
  private Triangle triangle;
  private EquilateralTriangle equilateralTriangle;

  /**
   * Set up some instances that can be used in tests.
   */
  @Before
  public void setup() {
    circle = new Circle(0, 0, 5);
    line = new Line(0 ,0 ,4, 4);

    List<Symbol> circles = new ArrayList<>();
    circles.add(new Circle(0, 0, 5));
    circles.add(new Circle(0, 7, 2));
    circles.add(new Circle(0, 10, 1));
    snowman = new Snowman(circles);

    List<Symbol> lines = new ArrayList<>();
    lines.add(new Line(0, 0, 4, 0));
    lines.add(new Line(0, 0, 2, 3.46));
    lines.add(new Line(4, 0, 2, 3.46));

    triangle = new Triangle(lines);
    equilateralTriangle = new EquilateralTriangle(lines);
  }

  /**
   * Test for the DecoratorSymbolCenter.
   */
  @Test
  public void decoratorCenterTest() {
    DecoratorSymbolCenter dCircle = new DecoratorSymbolCenter(circle);
    IPoint dCircleCenter = dCircle.getCenter();
    assertEquals(0, dCircleCenter.getX(), 0.01);
    assertEquals(0, dCircleCenter.getY(), 0.01);

    DecoratorSymbolCenter dLine = new DecoratorSymbolCenter(line);
    IPoint dLineCenter = dLine.getCenter();
    assertEquals(2, dLineCenter.getX(), 0.01);
    assertEquals(2, dLineCenter.getY(), 0.01);

    DecoratorSymbolCenter dSnowman = new DecoratorSymbolCenter(snowman);
    IPoint dSnowmanCenter = dSnowman.getCenter();
    assertEquals(0, dSnowmanCenter.getX(), 0.01);
    assertEquals(7, dSnowmanCenter.getY(), 0.01);

    DecoratorSymbolCenter dTriangle = new DecoratorSymbolCenter(triangle);
    IPoint dTriangleCenter = dTriangle.getCenter();
    assertEquals(2, dTriangleCenter.getX(), 0.01);
    assertEquals(1.15, dTriangleCenter.getY(), 0.01);

    DecoratorSymbolCenter dET = new DecoratorSymbolCenter(equilateralTriangle);
    IPoint dETCenter = dET.getCenter();
    assertEquals(2, dETCenter.getX(), 0.01);
    assertEquals(1.15, dETCenter.getY(), 0.01);
  }

  /**
   * Test for the DecoratorSymbolName.
   */
  @Test
  public void decoratorNameTest() {
    DecoratorSymbolName dCircle = new DecoratorSymbolName(circle);
    assertEquals("Circle", dCircle.getNameString());

    DecoratorSymbolName dLine = new DecoratorSymbolName(line);
    assertEquals("Line", dLine.getNameString());

    DecoratorSymbolName dSnowman = new DecoratorSymbolName(snowman);
    assertEquals("Snowman", dSnowman.getNameString());

    DecoratorSymbolName dTriangle = new DecoratorSymbolName(triangle);
    assertEquals("Triangle", dTriangle.getNameString());

    DecoratorSymbolName dET = new DecoratorSymbolName(equilateralTriangle);
    assertEquals("Equilateral Triangle", dET.getNameString());

  }
}