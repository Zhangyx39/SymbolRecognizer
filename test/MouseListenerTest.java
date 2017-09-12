import org.junit.Before;
import org.junit.Test;

import java.awt.Label;
import java.awt.event.MouseEvent;

import recognizer.controller.Controller;
import recognizer.controller.IMouseFunction;
import recognizer.model.IModel;
import recognizer.model.Model;
import recognizer.view.IView;
import recognizer.view.View;

import static org.junit.Assert.assertEquals;

/**
 * Test for the MouseListeners.
 * <p>SymbolAdder</p>
 * It extends MouseAdapter and takes a MouseFunction to construct.
 * Only the mouseReleased method is overridden.
 * <p>DragDetection</p>
 * It extends MouseMotionAdapter and takes a MouseFunction to construct.
 * Only the mouseDragged method is overridden.
 */
public class MouseListenerTest {

  private PressedTester pressedTester;
  private ReleasedTester releasedTester;
  private DraggedTester draggedTester;
  private Controller.MyMouseAdapter myMouseAdapter;
  private MouseEvent mouseEventDefault;

  /**
   * Set up some instances that is needed in the tests.
   */
  @Before
  public void setUp() {
    IView viewDefault = new View(200, 200);
    IModel modelDefault = new Model();
    Controller controllerDefault = new Controller(modelDefault, viewDefault);
    pressedTester = new PressedTester();
    releasedTester = new ReleasedTester();
    draggedTester = new DraggedTester();
    myMouseAdapter = controllerDefault.new MyMouseAdapter(releasedTester,
        pressedTester, draggedTester);
    mouseEventDefault = new MouseEvent(new Label(), 0, 0, 0, 0,
        0, 1, false);
  }

  /**
   * When mousePressed is called in the mouse adapter, the method inside the
   * function object is called. Mouse press event will be detected and
   * coordinates are recorded.
   */
  @Test
  public void mousePressedTest() {
    // A mouse press event at (0, 0)
    myMouseAdapter.mousePressed(mouseEventDefault);
    assertEquals("Mouse press event detected at: (0, 0).\n",
        pressedTester.report());
  }

  /**
   * When mouseReleased is called in the mouse adapter, the method inside the
   * function object is called. Mouse release event will be detected and
   * coordinates are recorded.
   */
  @Test
  public void mouseReleasedTest() {
    // A mouse release event at (0, 0)
    myMouseAdapter.mouseReleased(mouseEventDefault);
    assertEquals("Mouse release event detected at: (0, 0).\n",
        releasedTester.report());
  }

  /**
   * When mouseDragged is called in the mouse adapter, the method inside the
   * function object is called. Mouse drag event will be detected and
   * coordinates are recorded.
   */
  @Test
  public void mouseDraggedTest() {
    // A mouse drag event at (0, 0)
    myMouseAdapter.mouseDragged(mouseEventDefault);
    assertEquals("Mouse drag event detected at: (0, 0).\n",
        draggedTester.report());
  }

  /**
   * Test that when mouseReleased is called, the correct coordinates are
   * recorded.
   */
  @Test
  public void mouseReleasedMoreTest() {
    MouseEvent e1 = new MouseEvent(new Label(), 0, 0, 0,
        100, 100, 1, false);
    MouseEvent e2 = new MouseEvent(new Label(), 0, 0, 0,
        123, 456, 1, false);
    MouseEvent e3 = new MouseEvent(new Label(), 0, 0, 0,
        0, 333, 1, false);
    myMouseAdapter.mouseReleased(e1);
    myMouseAdapter.mouseReleased(e2);
    myMouseAdapter.mouseReleased(e3);
    String expected = "Mouse release event detected at: (100, 100).\n"
        + "Mouse release event detected at: (123, 456).\n"
        + "Mouse release event detected at: (0, 333).\n";
    assertEquals(expected, releasedTester.report());
  }

  /**
   * Test that when mousePressed is called, the correct coordinates are
   * recorded.
   */
  @Test
  public void mousePressedMoreTest() {
    MouseEvent e1 = new MouseEvent(new Label(), 0, 0, 0,
        100, 100, 1, false);
    MouseEvent e2 = new MouseEvent(new Label(), 0, 0, 0,
        123, 456, 1, false);
    MouseEvent e3 = new MouseEvent(new Label(), 0, 0, 0,
        0, 333, 1, false);
    myMouseAdapter.mousePressed(e1);
    myMouseAdapter.mousePressed(e2);
    myMouseAdapter.mousePressed(e3);
    String expected = "Mouse press event detected at: (100, 100).\n"
        + "Mouse press event detected at: (123, 456).\n"
        + "Mouse press event detected at: (0, 333).\n";
    assertEquals(expected, pressedTester.report());
  }

  /**
   * Test that when mouseDragged is called, the correct coordinates are
   * recorded.
   */
  @Test
  public void mouseDraggedMoreTest() {
    MouseEvent e1 = new MouseEvent(new Label(), 0, 0, 0,
        100, 100, 1, false);
    MouseEvent e2 = new MouseEvent(new Label(), 0, 0, 0,
        123, 456, 1, false);
    MouseEvent e3 = new MouseEvent(new Label(), 0, 0, 0,
        0, 333, 1, false);
    myMouseAdapter.mouseDragged(e1);
    myMouseAdapter.mouseDragged(e2);
    myMouseAdapter.mouseDragged(e3);
    String expected = "Mouse drag event detected at: (100, 100).\n"
        + "Mouse drag event detected at: (123, 456).\n"
        + "Mouse drag event detected at: (0, 333).\n";
    assertEquals(expected, draggedTester.report());
  }

  /**
   * Test that a mix of the three method is called, they work correctly.
   */
  @Test
  public void mixMethodsTest() {
    MouseEvent e1 = new MouseEvent(new Label(), 0, 0, 0,
        100, 100, 1, false);
    MouseEvent e2 = new MouseEvent(new Label(), 0, 0, 0,
        123, 456, 1, false);
    MouseEvent e3 = new MouseEvent(new Label(), 0, 0, 0,
        0, 333, 1, false);
    myMouseAdapter.mousePressed(e1);
    myMouseAdapter.mouseDragged(e2);
    myMouseAdapter.mouseDragged(e3);
    myMouseAdapter.mouseReleased(e3);
    String expected = "Mouse press event detected at: (100, 100).\n"
        + "Mouse drag event detected at: (123, 456).\n"
        + "Mouse drag event detected at: (0, 333).\n"
        + "Mouse release event detected at: (0, 333).\n";
    assertEquals(expected, pressedTester.report()
        + draggedTester.report() + releasedTester.report());
  }

  /**
   * The function objects used in mouse adapter will only be invoked when
   * mouseReleased, mousePressed and mouseDrag are called. All other methods
   * are not used in the mouse adapter.
   */
  @Test
  public void myMouseAdapterOtherTest() {
    myMouseAdapter.mouseClicked(mouseEventDefault);
    myMouseAdapter.mouseEntered(mouseEventDefault);
    myMouseAdapter.mouseExited(mouseEventDefault);
    myMouseAdapter.mouseMoved(mouseEventDefault);
    // The function objects are not called.
    assertEquals("", pressedTester.report());
    assertEquals("", releasedTester.report());
    assertEquals("", draggedTester.report());
  }

  /**
   * A mouse function that is used for the tests.
   * Every time mousePressed is invoked, it records the x and y coordinates
   * of the mouse event.
   * It provides a method that can output the record.
   */
  private class PressedTester implements IMouseFunction {

    private StringBuilder sb;

    public PressedTester() {
      this.sb = new StringBuilder();
    }

    /**
     * When this method is called, it appends a message to the StringBuilder.
     * The Message record the x and y coordinates of the mouse event.
     *
     * @param e the mouse event
     */
    @Override
    public void run(MouseEvent e) {
      sb.append("Mouse press event detected at: ("
          + e.getX() + ", " + e.getY() + ").\n");
    }

    /**
     * Report all messages that the StringBuilder keeps.
     *
     * @return the record in String
     */
    public String report() {
      return sb.toString();
    }
  }


  /**
   * A mouse function that is used for the tests.
   * Every time mouseReleased is invoked, it records the x and y coordinates
   * of the mouse event.
   * It provides a method that can output the record.
   */
  private class ReleasedTester implements IMouseFunction {

    private StringBuilder sb;

    public ReleasedTester() {
      this.sb = new StringBuilder();
    }

    /**
     * When this method is called, it appends a message to the StringBuilder.
     * The Message record the x and y coordinates of the mouse event.
     *
     * @param e the mouse event
     */
    @Override
    public void run(MouseEvent e) {
      sb.append("Mouse release event detected at: ("
          + e.getX() + ", " + e.getY() + ").\n");
    }

    /**
     * Report all messages that the StringBuilder keeps.
     *
     * @return the record in String
     */
    public String report() {
      return sb.toString();
    }
  }

  /**
   * A mouse function that is used for the tests.
   * Every time mouseDragged is invoked, it records the x and y coordinates
   * of the mouse event.
   * It provides a method that can output the record.
   */
  private class DraggedTester implements IMouseFunction {

    private StringBuilder sb;

    public DraggedTester() {
      this.sb = new StringBuilder();
    }

    /**
     * When this method is called, it appends a message to the StringBuilder.
     * The Message record the x and y coordinates of the mouse event.
     *
     * @param e the mouse event
     */
    @Override
    public void run(MouseEvent e) {
      sb.append("Mouse drag event detected at: ("
          + e.getX() + ", " + e.getY() + ").\n");
    }

    /**
     * Report all messages that the StringBuilder keeps.
     *
     * @return the record in String
     */
    public String report() {
      return sb.toString();
    }
  }

}