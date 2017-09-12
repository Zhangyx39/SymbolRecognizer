import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Candy;
import recognizer.symbol.Circle;
import recognizer.symbol.CompositeSymbol;
import recognizer.symbol.DeathlyHallows;
import recognizer.symbol.DecoratorSymbolCenter;
import recognizer.symbol.EquilateralTriangle;
import recognizer.model.IRecognizer;
import recognizer.symbol.Line;
import recognizer.symbol.Point;
import recognizer.model.Recognizer;
import recognizer.symbol.Rectangle;
import recognizer.symbol.Snowman;
import recognizer.symbol.Symbol;
import recognizer.symbol.Triangle;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for Recognizer.
 *
 * <p>addBasicSymbol:
 * -Empty list add
 * -NonEmpty list add
 * -Duplicate address add
 * -Add null exception
 *
 * <p>addBasicSymbol:
 * -Get Empty List
 * -Get Non Empty List
 *
 * <p>Detection:
 * - within list of circles and multiple lines
 *
 * <p>Equilateral
 * - single case success, fail
 * - single case within precision
 * - precision test, fail and success
 * - orientation test, fail and success
 *
 * <p>Triangles
 * - single case success, fail
 * - single case within precision
 * - precision test, fail and success
 * - orientation test, fail and success
 *
 * <p>SnowMan
 * - single case success, fail
 * - single case within precision
 * - ordering
 * - precision test, fail and success
 * - orientation test, fail and success
 *
 * <p>Creation:
 *
 * <p>Circle:
 * - creation exception r<0
 * - creation success
 *
 * <p>Line:
 * - creation exception same point
 * - creation success
 */

public class IRecognizerTest {

  IRecognizer model;
  private BasicSymbol circleLarge;
  private BasicSymbol circleSmall;
  private BasicSymbol circleMid;
  private BasicSymbol circleOther;

  private BasicSymbol line0;
  private BasicSymbol line1;
  private BasicSymbol line2;
  private List<Symbol> listOfT;
  private IRecognizer modelRec;

  /**
   * Build the model.
   * Create three basic circles, that can form a snow man.
   * Create three basic lines, that can form a triangle.
   */
  @Before
  public void setUp() {
    model = new Recognizer();
    //set up for a snowman
    circleLarge = new Circle(20, 20, 20);
    circleSmall = new Circle(20, 65, 5);
    circleMid = new Circle(20, 50, 10);
    circleOther = new Circle(0, 0, 10);
    List<BasicSymbol> listOfC = new ArrayList<>();
    listOfC.add(circleLarge);
    listOfC.add(circleMid);
    listOfC.add(circleSmall);

    //set up for an equilateral triangle
    double height = 3 * Math.sqrt(3);
    line0 = new Line(0, 0, 6, 0);
    line1 = new Line(0, 0, 3, height);
    line2 = new Line(6, 0, 3, height);
    listOfT = new ArrayList<>();
    listOfT.add(line0);
    listOfT.add(line1);
    listOfT.add(line2);

    // set up new recognizer
    modelRec = new Recognizer();
  }

  /**
   * Add four line segments to form a rectangle. The model should recognize a
   * rectangle.
   */
  @Test
  public void rectangleSuccessTest() {
    Line side1 = new Line(0, 0, 5, 0);
    Line side2 = new Line(5, 2, 5, 0);
    Line side3 = new Line(0, 2, 5, 2);
    Line side4 = new Line(0, 2, 0, 0);
    IRecognizer newModel = new Recognizer();
    newModel.addBasicSymbol(side1);
    newModel.addBasicSymbol(side2);
    newModel.addBasicSymbol(side3);
    newModel.addBasicSymbol(side4);
    List<Symbol> symbols = newModel.getSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Rectangle);
  }

  /**
   * Add four line segments to form a parallelogram. The model should
   * not recognize a rectangle.
   */
  @Test
  public void notRectangleTest() {
    Line side1 = new Line(0, 0, 5, 0);
    Line side2 = new Line(7, 2, 5, 0);
    Line side3 = new Line(2, 2, 7, 2);
    Line side4 = new Line(2, 2, 0, 0);
    IRecognizer newModel = new Recognizer();
    newModel.addBasicSymbol(side1);
    newModel.addBasicSymbol(side2);
    newModel.addBasicSymbol(side3);
    newModel.addBasicSymbol(side4);
    List<Symbol> symbols = newModel.getSymbols();
    assertNotEquals(1, symbols.size());
    assertFalse(symbols.get(0) instanceof Rectangle);
  }

  /**
   * Add a triangle, a circle and a line segment to form a DeathlyHallows. The
   * model should recognize a Deathly Hallows sign.
   */
  @Test
  public void deathlyHallowsSuccessTest() {
    Line side1 = new Line(0, 0, 4, 0);
    Line side2 = new Line(4, 0, 2, 3.46);
    Line side3 = new Line(0, 0, 2, 3.46);
    Circle circle = new Circle(2, 1.15, 1.15);
    Line line = new Line(2, 3.46, 2, 0);
    IRecognizer newModel = new Recognizer();
    newModel.addBasicSymbol(side1);
    newModel.addBasicSymbol(side2);
    newModel.addBasicSymbol(side3);
    newModel.addBasicSymbol(circle);
    newModel.addBasicSymbol(line);
    List<Symbol> symbols = newModel.getSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof DeathlyHallows);
  }

  /**
   * Add two triangles and a circle to form a Candy.
   * The model should recognize a Candy.
   */
  @Test
  public void candySuccessTest() {
    Line side1 = new Line(0, 0, 0, 4);
    Line side2 = new Line(0, 0, 4, 2);
    Line side3 = new Line(0, 4, 4, 2);
    Circle circle = new Circle(6, 2, 2);
    Line side4 = new Line(12, 0, 12, 4);
    Line side5 = new Line(12, 0, 8, 2);
    Line side6 = new Line(12, 4, 8, 2);
    IRecognizer newModel = new Recognizer();
    newModel.addBasicSymbol(side1);
    newModel.addBasicSymbol(side2);
    newModel.addBasicSymbol(side3);
    newModel.addBasicSymbol(circle);
    newModel.addBasicSymbol(side4);
    newModel.addBasicSymbol(side5);
    newModel.addBasicSymbol(side6);
    List<Symbol> symbols = newModel.getSymbols();
    assertEquals(1, symbols.size());
    assertTrue(symbols.get(0) instanceof Candy);
  }


  /**
   * Testing getSymbols for empty list.
   */
  @Test
  public void testGetEmptyList() {
    assertEquals(model.getSymbols().size(), 0);
  }

  /**
   * Testing adding duplicated symbols of same address are not added.
   */
  @Test
  public void testDup() {
    model.addBasicSymbol(line0);
    model.addBasicSymbol(line0);
    assertTrue(model.getSymbols().size() == 1);
    assertEquals(line0.toString(), model.getSymbols().get(0).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    model.addBasicSymbol(null);
  }

  /**
   * Test adding basic symbols to empty and non-empty list.
   * Test getting symbols for non-empty list.
   */
  @Test
  public void testAddGetSymbol() {
    BasicSymbol line0 = new Line(0, 0, 1, 1);
    BasicSymbol line1 = new Line(-1, -1, -1, -10);

    BasicSymbol cir0 = new Circle(0, 0, 10);
    BasicSymbol cir1 = new Circle(1, 1, 12);

    assertEquals(0, model.getSymbols().size());
    model.addBasicSymbol(line0);
    assertEquals(1, model.getSymbols().size());
    model.addBasicSymbol(line1);
    assertEquals(2, model.getSymbols().size());
    model.addBasicSymbol(cir0);
    assertEquals(3, model.getSymbols().size());
    model.addBasicSymbol(cir1);
    assertEquals(4, model.getSymbols().size());

    assertEquals(line0.toString(), model.getSymbols().get(0).toString());
    assertEquals(line1.toString(), model.getSymbols().get(1).toString());
    assertEquals(cir0.toString(), model.getSymbols().get(2).toString());
    assertEquals(cir1.toString(), model.getSymbols().get(3).toString());
  }

  /**
   * Test to add basic symbols to the recognizer.
   */
  @Test
  public void testBasicSymbolAdd_LineCircle() {
    modelRec.addBasicSymbol(line0);
    modelRec.addBasicSymbol(line1);

    modelRec.addBasicSymbol(circleSmall);
    modelRec.addBasicSymbol(circleMid);

    assertEquals(4, modelRec.getSymbols().size());
    assertEquals(line0.toString(), modelRec.getSymbols().get(0).toString());
    assertEquals(line1.toString(), modelRec.getSymbols().get(1).toString());
    assertEquals(circleSmall.toString(), modelRec.getSymbols().get(2).toString());
    assertEquals(circleMid.toString(), modelRec.getSymbols().get(3).toString());
  }

  /**
   * Test for not recognizing Snowman and Triangle when lines and circles are note matched.
   */
  @Test
  public void testNonTriangleLines_NonSnowmanCircles() {
    modelRec.addBasicSymbol(line0);
    modelRec.addBasicSymbol(line1);
    BasicSymbol lineNew = new Line(3, 3, 8, 8);
    modelRec.addBasicSymbol(lineNew);

    modelRec.addBasicSymbol(circleMid);
    modelRec.addBasicSymbol(circleLarge);
    BasicSymbol circleNew = new Circle(3, 3, 10);
    modelRec.addBasicSymbol(circleNew);

    assertEquals(6, modelRec.getSymbols().size());
    assertEquals(line0.toString(), modelRec.getSymbols().get(0).toString());
    assertEquals(line1.toString(), modelRec.getSymbols().get(1).toString());
    assertEquals(lineNew.toString(), modelRec.getSymbols().get(2).toString());
    assertEquals(circleMid.toString(), modelRec.getSymbols().get(3).toString());
    assertEquals(circleLarge.toString(), modelRec.getSymbols().get(4).toString());
    assertEquals(circleNew.toString(), modelRec.getSymbols().get(5).toString());

  }

  /**
   * Snow man detection single success case.
   */
  @Test
  public void testSnowManSuccess() {
    modelRec.addBasicSymbol(circleOther);
    modelRec.addBasicSymbol(line0);
    modelRec.addBasicSymbol(circleLarge);
    modelRec.addBasicSymbol(circleMid);
    modelRec.addBasicSymbol(circleSmall);

    assertEquals(3, modelRec.getSymbols().size());
    assertEquals(circleOther.toString(), modelRec.getSymbols().get(0).toString());
    assertEquals(line0.toString(), modelRec.getSymbols().get(1).toString());
    assertTrue(modelRec.getSymbols().get(2) instanceof Snowman);
  }

  /**
   * Snow man detection single imprecise success.
   */
  @Test
  public void testSnowManImprecise() {

    //large circle original (20,20) radius 20
    modelRec.addBasicSymbol(new Circle(20, 20, 19.9));
    modelRec.addBasicSymbol(circleMid);
    modelRec.addBasicSymbol(circleSmall);

    assertEquals(1, modelRec.getSymbols().size());
    assertTrue(modelRec.getSymbols().get(0) instanceof Snowman);
  }

  /**
   * Testing recognition of an equilateral triangle with different orientation.
   * A triangle formed by three lines:
   * line0 = new Line(0, 0, 6, 0);
   * line1 = new Line(0, 0, 3, 5.2);
   * line2 = new Line(6, 0, 3, 5.2);
   * Rotate each line by 0 - 360 degrees;
   * The triangle is formed at the end of the list automatically.
   * The lines forming triangle are all removed from the list.
   * The line and circle not used to form triangle stayed the same.
   * Getting complex and basic symbol is also tested in this case.
   */

  @Test
  public void testEquilateralTriangleRecog_Orientation() {
    Point org0 = new Point(0, 0);
    Point org1 = new Point(6, 0);
    Point org2 = new Point(3, (3 * Math.sqrt(3)));
    CompositeSymbol eq = new EquilateralTriangle(listOfT);
    assertTrue(eq instanceof EquilateralTriangle);
    helperRotateTriangle(org0, org1, org2, eq);
  }


  /**
   * Same thing goes for testing non-equilateral triangles.
   */
  @Test
  public void testTriangleRecog_Orientation() {
    Point org0 = new Point(0, 5);
    Point org1 = new Point(5, 10);
    Point org2 = new Point(10, 0);
    CompositeSymbol eq = new Triangle(listOfT);
    assertTrue(eq instanceof Triangle);
    helperRotateTriangle(org0, org1, org2, eq);
  }

  /**
   * Testing recognition of an equilateral triangle with precision 1%.
   * line0 = new Line(0, 0, 6, 0);
   * line1 = new Line(0, 0, 3, 5.2);
   * line2 = new Line(6, 0, 3, 5.2);
   * The triangle is formed at the end of the list automatically.
   * The lines forming triangle are all removed from the list.
   * The line and circle not used to form triangle stayed the same.
   * Getting complex and basic symbol is also tested in this case.
   */

  @Test
  public void testEquilateralTriangleRecog_Precision() {
    Point org0 = new Point(0, 0);
    Point org1 = new Point(6, 0);
    Point org2 = new Point(3, (3 * Math.sqrt(3)));

    CompositeSymbol eq = new EquilateralTriangle(listOfT);
    assertTrue(eq instanceof EquilateralTriangle);

    helperTiranglePrecision(6, org0, org1, org2, eq);
  }

  /**
   * Same thing goes for testing non-equilateral triangles.
   */
  @Test
  public void testTriangleRecog_Precision() {
    Point org0 = new Point(0, 5);
    Point org1 = new Point(10, 0);
    Point org2 = new Point(5, 10);
    CompositeSymbol eq = new Triangle(listOfT);
    assertTrue(eq instanceof Triangle);
    helperTiranglePrecision(10, org0, org1, org2, eq);
  }


  /**
   * Equilateral Triangle detection single success case.
   */
  @Test
  public void testEquilateralSuccess() {
    modelRec.addBasicSymbol(line0);
    modelRec.addBasicSymbol(line1);
    modelRec.addBasicSymbol(line2);

    assertEquals(1, modelRec.getSymbols().size());
    assertTrue(modelRec.getSymbols().get(0) instanceof EquilateralTriangle);
  }

  /**
   * Equilateral Triangle detection single imprecise success.
   */
  @Test
  public void testEquilateralImprecise() {
    //large circle original (0,0) (0,5.99)
    BasicSymbol lineNew = new Line(0, 0, 5.99, 0);
    modelRec.addBasicSymbol(lineNew);
    modelRec.addBasicSymbol(line1);
    modelRec.addBasicSymbol(line2);

    assertEquals(1, modelRec.getSymbols().size());
    assertTrue(modelRec.getSymbols().get(0) instanceof EquilateralTriangle);
  }


  /**
   * Test that three segnments of a single line is not a triangle.
   */
  @Test
  public void testThreeLinesNotTriangle() {
    modelRec.addBasicSymbol(new Line(0, 0, 1, 1));
    modelRec.addBasicSymbol(new Line(1, 1, 5, 5));
    modelRec.addBasicSymbol(new Line(5, 5, 0, 0));

    //not recognized as triangle
    assertEquals(3, modelRec.getSymbols().size());
  }

  /**
   * Triangle detection single success case.
   */
  @Test
  public void testTriangleSuccess() {
    modelRec.addBasicSymbol(new Line(0, 0, 0, 2));
    modelRec.addBasicSymbol(new Line(0, 2, 5, 0));
    modelRec.addBasicSymbol(new Line(5, 0, 0, 0));

    assertEquals(1, modelRec.getSymbols().size());
    assertTrue(modelRec.getSymbols().get(0) instanceof Triangle);
  }

  /**
   * Triangle detection single imprecise success.
   */
  @Test
  public void testTriangleImprecise() {
    modelRec.addBasicSymbol(new Line(0.01, 0, 0, 2));
    modelRec.addBasicSymbol(new Line(0, 2, 5.01, 0));
    modelRec.addBasicSymbol(new Line(5, 0, 0, 0));

    assertEquals(1, modelRec.getSymbols().size());
    assertTrue(modelRec.getSymbols().get(0) instanceof Triangle);
  }


  /**
   * Testing snowman creation.
   */
  @Test
  public void testSnowManCreation() {
    model.addBasicSymbol(line0);
    model.addBasicSymbol(line1);

    model.addBasicSymbol(circleSmall);
    model.addBasicSymbol(circleMid);
    model.addBasicSymbol(line2);
    model.addBasicSymbol(circleLarge);

    model.addBasicSymbol(circleLarge);
    model.addBasicSymbol(circleMid);
    model.addBasicSymbol(circleSmall);

    model.addBasicSymbol(circleLarge);
    model.addBasicSymbol(circleSmall);
    model.addBasicSymbol(circleMid);

    //output a list of equilateral triangle, two snowman and three circles.
    assertEquals(4, model.getSymbols().size());
    assertTrue(model.getSymbols().get(0) instanceof EquilateralTriangle);
    assertTrue(model.getSymbols().get(1) instanceof Snowman);
    assertTrue(model.getSymbols().get(2) instanceof Snowman);
    assertTrue(model.getSymbols().get(2) instanceof Snowman);
  }

  /**
   * Testing snow man with precision within 1%.
   * Snow man is composed of three circles.
   * circleLarge = new Circle(20, 20, 20)
   * circleMid = new Circle(20, 50, 10)
   * circleSmall = new Circle(20, 65, 5)
   * Changing the mid circle's radius from 8 to 12, 0.1 at a time,
   * check that when the delta is within 1%, recognizer can form a snowman.
   */
  @Test
  public void testSnowManPrecision() {

    //changing the mid circle's radius
    int orgR = 10;
    for (double r = 8; r < 12; r = r + 0.1) {
      IRecognizer model = new Recognizer();
      model.addBasicSymbol(line1);
      BasicSymbol mid = new Circle(20, 50, r);
      model.addBasicSymbol(circleLarge);
      model.addBasicSymbol(mid);
      model.addBasicSymbol(line2);
      model.addBasicSymbol(circleSmall);

      //in 1% range
      if (r > (0.99 * orgR) && r < (1.009 * orgR)) {
        //shapes in list : line1, line 0, snowman
        //System.out.println(model.getSymbols().toString());
        assertEquals(3, model.getSymbols().size());
        assertTrue(model.getSymbols().get(0) instanceof Line);
        assertTrue(model.getSymbols().get(1) instanceof Line);
        assertTrue(model.getSymbols().get(2) instanceof Snowman);
      }

      if (r < (0.98 * orgR) && r > (1.01 * orgR)) {
        //otherwise 5 symbols are in the list
        assertEquals(5, model.getSymbols().size());
      }

    }
  }

  /**
   * Testing snow man with rotation 0-360 degrees.
   * Snow man is composed of three circles.
   * circleLarge = new Circle(20, 20, 20)
   * circleMid = new Circle(20, 50, 10)
   * circleSmall = new Circle(20, 65, 5)
   * Changing all three circle's center locations with the same angle,
   * testing that the recognizer is still able to form a snow man.
   */
  @Test
  public void testSnowManRotation() {
    double x = 20;
    double yLarge = 20;
    double yMid = 50;
    double ySmall = 65;

    for (int theta = 0; theta <= 360; theta++) {
      //changing center point location by rotating theta degrees around (0,0) point
      Point newLarge = helperRotateAroundCenter(theta, x, yLarge);
      Point newMid = helperRotateAroundCenter(theta, x, yMid);
      Point newsmall = helperRotateAroundCenter(theta, x, ySmall);
      //forming new symbols
      BasicSymbol large = new Circle(newLarge.getX(), newLarge.getY(), 20);
      BasicSymbol mid = new Circle(newMid.getX(), newMid.getY(), 10);
      BasicSymbol small = new Circle(newsmall.getX(), newsmall.getY(), 5);

      //adding new symbols,
      IRecognizer model = new Recognizer();
      model.addBasicSymbol(line1);
      model.addBasicSymbol(large);
      model.addBasicSymbol(mid);
      model.addBasicSymbol(line2);
      model.addBasicSymbol(small);

      //test that there are exactly two lines and a snow man in the list
      assertEquals(3, model.getSymbols().size());
      assertTrue(model.getSymbols().get(0) instanceof Line);
      assertTrue(model.getSymbols().get(1) instanceof Line);
      assertTrue(model.getSymbols().get(2) instanceof Snowman);
    }
  }

  /**
   * A helper method to test precision 1%.
   * The triangle is formed at the end of the list automatically.
   * The lines forming triangle are all removed from the list.
   * The line and circle not used to form triangle stayed the same.
   * Getting complex and basic symbol is also tested in this case.
   *
   * @param orgR original x-point to be tested
   * @param org0 original triangle vertex 0
   * @param org1 original triangle vertex 1
   * @param org2 original triangle vertex 2
   */
  private void helperTiranglePrecision(double orgR, Point org0, Point org1, Point org2,
      CompositeSymbol aSymbol) {
    //forming new symbols
    BasicSymbol line1 = new Line(org0.getX(), org0.getY(), org2.getX(), org2.getY());
    BasicSymbol line2 = new Line(org1.getX(), org1.getY(), org2.getX(), org2.getY());

    for (double r = orgR - 3; r < orgR + 3; r = r + 0.01) {
      //for (double r = orgR ; r <= orgR ; r = r + 0.1) {
      BasicSymbol line0 = new Line(org0.getX(), org0.getY(), r, org1.getY());

      //adding line1
      IRecognizer model = new Recognizer();
      model.addBasicSymbol(line1);
      assertEquals(1, model.getSymbols().size());

      //adding line0
      model.addBasicSymbol(line0);
      assertEquals(2, model.getSymbols().size());

      //adding line1 again
      model.addBasicSymbol(
          model.createLineSegment(org0.getX(), org0.getY(), org2.getX(), org2.getY()));
      assertEquals(3, model.getSymbols().size());

      //adding a circle
      model.addBasicSymbol(circleSmall);
      assertEquals(4, model.getSymbols().size());

      //adding line2
      model.addBasicSymbol(line2);

      //in 1% range
      if (r > (0.99 * orgR) && r < (1.009 * orgR)) {
        //shapes in list : line0, circleSmall, Equilateral Triangle
        //System.out.println(model.getSymbols().toString());
        assertEquals(3, model.getSymbols().size());
        assertTrue(model.getSymbols().get(0) instanceof Line);
        assertTrue(model.getSymbols().get(1) instanceof Circle);
        assertTrue(model.getSymbols().get(2).getClass() == (aSymbol.getClass()));
      }

      if (r < (0.98 * orgR) && r > (1.01 * orgR)) {
        //otherwise 5 symbols are in the list
        assertEquals(5, model.getSymbols().size());
      }
    }
  }

  /**
   * A helper method to rotate a triangle theta degrees.
   * A circle is added in between lines.
   * The end of this helper checks that unused line and circle are not changed.
   */
  private void helperRotateTriangle(Point org0, Point org1, Point org2, CompositeSymbol aSymbol) {

    for (int theta = 0; theta <= 360; theta++) {

      //line0 = new Line(0, 0, 6, 0);
      //line1 = new Line(0, 0, 3, 5.2);
      //line2 = new Line(6, 0, 3, 5.2);

      //changing center point location by rotating theta degrees around (0,0) point
      Point new0 = helperRotateAroundCenter(theta, org0.getX(), org0.getY());
      Point new1 = helperRotateAroundCenter(theta, org1.getX(), org1.getY());
      Point new2 = helperRotateAroundCenter(theta, org2.getX(), org2.getY());
      //forming new symbols
      BasicSymbol line0 = new Line(new0.getX(), new0.getY(), new1.getX(), new1.getY());
      BasicSymbol line1 = new Line(new0.getX(), new0.getY(), new2.getX(), new2.getY());
      BasicSymbol line2 = new Line(new1.getX(), new1.getY(), new2.getX(), new2.getY());

      IRecognizer model = new Recognizer();

      //adding line1
      model.addBasicSymbol(line1);
      assertEquals(1, model.getSymbols().size());

      //adding line0
      model.addBasicSymbol(line0);
      assertEquals(2, model.getSymbols().size());

      //adding line1 again
      model.addBasicSymbol(
          model.createLineSegment(new0.getX(), new0.getY(), new2.getX(), new2.getY()));
      assertEquals(3, model.getSymbols().size());

      //adding a circle
      model.addBasicSymbol(circleSmall);
      assertEquals(4, model.getSymbols().size());

      //adding line2
      model.addBasicSymbol(line2);

      //System.out.println(model.getSymbols().toString());

      assertEquals(3, model.getSymbols().size());
      assertEquals(line1.toString(), model.getSymbols().get(0).toString());
      assertEquals(circleSmall.toString(), model.getSymbols().get(1).toString());
      assertTrue(model.getSymbols().get(2).getClass() == (aSymbol.getClass()));
    }
  }

  /**
   * A helper method to rotate a point around the center (0,0), with degree theta.
   *
   * @param theta degree
   * @param oX original x
   * @param oY original y
   * @return Point of new x and new y
   */

  private Point helperRotateAroundCenter(double theta, double oX, double oY) {
    double centerX = 0;
    double centerY = 0;

    //convert to radian for math.cos
    theta = Math.toRadians(theta);
    double newX = centerX + (oX - centerX) * Math.cos(theta) - (oY - centerY) * Math.sin(theta);
    double newY = centerY + (oX - centerX) * Math.sin(theta) + (oY - centerY) * Math.cos(theta);

    return new Point(newX, newY);
  }

  /**
   * Test line creation with duplicated point.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLineCreationFail() {
    BasicSymbol samePointline = model.createLineSegment(1.0, 1.0, 1.0, 1.0);
  }

  /**
   * Test line creation success.
   */
  @Test
  public void testLineCreationSuccess() {
    try {
      BasicSymbol line = model.createLineSegment(1, 25, 6, 9);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  /**
   * Test circle creation with negative raidus.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCircleCreationFail() {
    BasicSymbol circleNeg = model.createCircle(1.0, 2.0, -10);
  }

  /**
   * Test circle creation with zero raidus.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCircleCreationFailZero() {

    BasicSymbol circleNeg = model.createCircle(1.0, 2.0, 0);
  }

  /**
   * Test circle creation success.
   */
  @Test
  public void testCircleCreationSuccess() {
    try {
      BasicSymbol circle = model.createCircle(1, 1, 2);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }
}