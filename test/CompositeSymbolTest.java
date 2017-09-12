import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Circle;
import recognizer.symbol.CompositeSymbol;
import recognizer.symbol.EquilateralTriangle;
import recognizer.symbol.Line;
import recognizer.symbol.Point;
import recognizer.symbol.Snowman;
import recognizer.symbol.Symbol;
import recognizer.symbol.Triangle;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Composite Symbol Class with 1% imprecision.
 *
 * <p>Snowman
 * - creation failure, success with different precision
 * - creation failure with different order
 * - creation success with different orientation
 * - get components
 * - toString
 *
 * <p>Triangle && EquilateralTriangle
 * - creation failure
 * - creation success
 * - get components
 * - toString
 */
public class CompositeSymbolTest {

  private BasicSymbol large;
  private BasicSymbol small;
  private BasicSymbol mid;
  private CompositeSymbol snowmanBasic;

  private BasicSymbol line0;
  private BasicSymbol line1;
  private BasicSymbol line2;
  private CompositeSymbol triangle;
  private CompositeSymbol triangleEqui;


  /**
   * Create list of three basic circles that forms a snowman.
   * Create list of three lines that forms a equilateral triangle.
   */
  @Before
  public void setUp() {
    //set up for a snowman
    large = new Circle(20, 20, 20);
    small = new Circle(20, 65, 5);
    mid = new Circle(20, 50, 10);
    List<Symbol> listOfC = new ArrayList<>();
    listOfC.add(large);
    listOfC.add(mid);
    listOfC.add(small);
    snowmanBasic = new Snowman(listOfC);

    //set up for an equilateral triangle
    double height = 3 * Math.sqrt(3);
    line0 = new Line(0, 0, 6, 0);
    line1 = new Line(0, 0, 3, height);
    line2 = new Line(6, 0, 3, height);
    List<Symbol> listOfT = new ArrayList<>();
    listOfT.add(line0);
    listOfT.add(line1);
    listOfT.add(line2);
    triangle = new Triangle(listOfT);
    triangleEqui = new EquilateralTriangle(listOfT);
  }


  /**
   * Test triangle and Equilateral creation with three lines, changing one of the
   * line to test precision factor of 1%.
   */
  @Test
  public void testTriangleCreationPrecision() {
    List<Symbol> listOfLines = new ArrayList<>();
    //correct line0 should be (0,0) -> (0,6), changing point (0,6)
    for (double r = 3; r < 9; r = r + 0.1) {
      BasicSymbol line0 = new Line(0, 0, r, 0);
      listOfLines.add(line2);
      listOfLines.add(line0);
      listOfLines.add(line1);
      try {
        CompositeSymbol triangle = new Triangle(listOfLines);
        CompositeSymbol triangleE = new EquilateralTriangle(listOfLines);
        listOfLines.clear();
      } catch (IllegalArgumentException e) {
        listOfLines.clear();
        //if cannot create symbol in 1% range,test fail
        if (r >= (0.99 * 6) && r <= 6.06) {
          fail();
        }
      }
    }
  }

  /**
   * Test triangle and equilateral creation with different orientations by
   * rotating figures from zero to 360 degrees.
   */
  @Test
  public void testTriangleCreationOrientation() {
    List<Symbol> listOfLine = new ArrayList<>();
    Point org0 = new Point(0, 0);
    Point org1 = new Point(6, 0);
    Point org2 = new Point(3, (3 * Math.sqrt(3)));

    for (int theta = 0; theta <= 360; theta++) {
      //changing center point location by rotating theta degrees around (0,0) point
      Point new0 = helperRotateAroundCenter(theta, org0.getX(), org0.getY());
      Point new1 = helperRotateAroundCenter(theta, org1.getX(), org1.getY());
      Point new2 = helperRotateAroundCenter(theta, org2.getX(), org2.getY());
      //forming new symbols
      BasicSymbol line0 = new Line(new0.getX(), new0.getY(), new1.getX(), new1.getY());
      BasicSymbol line1 = new Line(new1.getX(), new1.getY(), new2.getX(), new2.getY());
      BasicSymbol line2 = new Line(new2.getX(), new2.getY(), new0.getX(), new0.getY());
      //adding new symbols
      listOfLine.add(line0);
      listOfLine.add(line1);
      listOfLine.add(line2);
      try {
        CompositeSymbol triangle = new Triangle(listOfLine);
        CompositeSymbol triangleE = new EquilateralTriangle(listOfLine);
        listOfLine.clear();
      } catch (IllegalArgumentException e) {
        //if cannot create symbol, test fail
        listOfLine.clear();
        fail();
      }
    }
  }

  /**
   * Test snow man creation with three circles, changing one of the
   * radius to test precision factor of 1%.
   */
  @Test
  public void testSnowmanCreationPrecision() {
    List<Symbol> listOfCirlce = new ArrayList<>();
    //correct mid circle should be (20,50) radius= 10;

    for (double r = 8; r < 12; r = r + 0.1) {
      BasicSymbol mid = new Circle(20, 50, r);
      listOfCirlce.add(large);
      listOfCirlce.add(mid);
      listOfCirlce.add(small);
      try {
        CompositeSymbol snowman = new Snowman(listOfCirlce);
        listOfCirlce.clear();
      } catch (IllegalArgumentException e) {
        listOfCirlce.clear();
        //if cannot create symbol in 1% range,test fail
        if (r <= 10.1 && r >= 9.9) {
          fail();
        }
      }
    }
  }

  /**
   * Test snow man creation with different orientations by
   * rotating figures from zero to 360 degrees.
   */
  @Test
  public void testSnowmanCreationOrientation() {
    List<Symbol> listOfCirlce = new ArrayList<>();
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
      //adding new symbols
      listOfCirlce.add(large);
      listOfCirlce.add(mid);
      listOfCirlce.add(small);
      try {
        CompositeSymbol snowman = new Snowman(listOfCirlce);
        listOfCirlce.clear();
      } catch (IllegalArgumentException e) {
        //if cannot create symbol, test fail
        listOfCirlce.clear();
        fail();
      }
    }
  }

  /**
   * Test snow man creating with different ordering on sizes.
   */
  @Test
  public void testSnowmanCreationDiffOrder() {
    List<Symbol> listOfCirlce = new ArrayList<>();

    listOfCirlce.add(small);
    listOfCirlce.add(mid);
    listOfCirlce.add(large);

    listOfCirlce.add(mid);
    listOfCirlce.add(small);
    listOfCirlce.add(large);

    try {
      CompositeSymbol snowman = new Snowman(listOfCirlce.subList(0, 3));
      CompositeSymbol snowman2 = new Snowman(listOfCirlce.subList(3, 6));

      listOfCirlce.clear();
    } catch (IllegalArgumentException e) {
      listOfCirlce.clear();
      fail();
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
   * Testing the get component method by checking the toString of each compoennt.
   */
  @Test
  public void testSnowmanGetComponents() {
    List<Symbol> snowComponents = snowmanBasic.getComponents();
    assertEquals(large.toString(), snowComponents.get(0).toString());
    assertEquals(mid.toString(), snowComponents.get(1).toString());
    assertEquals(small.toString(), snowComponents.get(2).toString());
  }

  @Test
  public void testTriangleGetComponent() {
    List<Symbol> triComponents = triangle.getComponents();
    assertEquals(line0.toString(), triComponents.get(0).toString());
    assertEquals(line1.toString(), triComponents.get(1).toString());
    assertEquals(line2.toString(), triComponents.get(2).toString());

    List<Symbol> triEqComponents = triangleEqui.getComponents();
    assertEquals(line0.toString(), triEqComponents.get(0).toString());
    assertEquals(line1.toString(), triEqComponents.get(1).toString());
    assertEquals(line2.toString(), triEqComponents.get(2).toString());
  }


  //Testing toString
  @Test
  public void testSnowmanToString() {
    String result = "(Snowman ";
    result = result + snowmanBasic.getComponents().get(0).toString() + " ";
    result = result + snowmanBasic.getComponents().get(1).toString() + " ";
    result = result + snowmanBasic.getComponents().get(2).toString() + ")";
    assertEquals(result, snowmanBasic.toString());
  }

  @Test
  public void testTriangleToString() {
    String result = "(Triangle ";
    result = result + triangle.getComponents().get(0).toString() + " ";
    result = result + triangle.getComponents().get(1).toString() + " ";
    result = result + triangle.getComponents().get(2).toString() + ")";
    assertEquals(result, triangle.toString());

    String result2 = "(EquilateralTriangle ";
    result2 = result2 + triangleEqui.getComponents().get(0).toString() + " ";
    result2 = result2 + triangleEqui.getComponents().get(1).toString() + " ";
    result2 = result2 + triangleEqui.getComponents().get(2).toString() + ")";
    assertEquals(result2, triangleEqui.toString());
  }
}