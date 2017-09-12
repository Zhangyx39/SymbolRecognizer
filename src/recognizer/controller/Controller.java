package recognizer.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import recognizer.model.IModel;
import recognizer.symbol.IPoint;
import recognizer.symbol.Point;
import recognizer.symbol.Symbol;
import recognizer.view.IView;

/**
 * The controller is the main operation class of the entire program.
 * On one hand, it takes input from the view and decides what to do.
 * On the other hand, it controls how and when the model is used.
 * It also controls what must be shown by the view and when.
 */
public class Controller implements IController {

  private IModel model;
  private IView view;

  /**
   * Constructor for controller, if the model or view is null, throws exception.
   *
   * @param m model to be passed in
   * @param view view to be passed in
   */
  public Controller(IModel m, IView view) {
    if (m == null || view == null) {
      throw new IllegalArgumentException("null input exception");
    }

    this.model = m;
    this.view = view;
    setMouseListener(new MyMouseAdapter(new MouseReleaseFunction(),
        new MousePressAndDragFunction(), new MousePressAndDragFunction()));
  }

  @Override
  public List<Symbol> getAllSymbols() {
    return model.getSymbols();
  }

  @Override
  public List<IPoint> getPoints() {
    return model.getPoints();
  }

  @Override
  public void addPoint(IPoint p) {
    //add points only if its not null
    if (p != null) {
      model.addPoint(p);
    }
  }

  @Override
  public void recognize() {
    model.recognize();
  }

  //set Release Listener and Drag Listener
  @Override
  public void setMouseListener(MouseAdapter adapter) {
    if (adapter == null) {
      throw new IllegalArgumentException("Null adapter.");
    }
    //view.setMouseDragListener(adapter);
    view.setMouseListener(adapter);
  }

  //recognize and repaint upon mouse release
  public class MyMouseAdapter extends MouseAdapter {

    private IMouseFunction mousePress;
    private IMouseFunction mouseRelease;
    private IMouseFunction mouseDrag;

    /**
     * Constructor for mouse adapter.
     *
     * @param mr a function for mouse released
     * @param mp a function for mouse pressed
     * @param md a function for mouse dragged
     */
    public MyMouseAdapter(IMouseFunction mr, IMouseFunction mp, IMouseFunction md) {
      mouseRelease = mr;
      mouseDrag = md;
      mousePress = mp;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      mouseRelease.run(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      mouseDrag.run(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      mousePress.run(e);
    }
  }


  /**
   * The functional class for mouse press and drag.
   * It contains only one method that record a dragged point from user doodle,
   * and repaint the view.
   */
  public class MousePressAndDragFunction implements IMouseFunction {

    /**
     * Line/Circle fitting the recorded points and paint the recognized basic
     * symbol onto the view.
     *
     * @param e A mouse event
     */
    @Override
    public void run(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      IPoint p = new Point(x, y);
      Controller.this.addPoint(p);
      view.repaint(Controller.this.getPoints(), Controller.this.getAllSymbols());
    }
  }


  /**
   * The functional class for mouse release.
   * It contains only one function that let controller fits a set of
   * doodled points to a line or a circle and repaint on the view.
   */
  public class MouseReleaseFunction implements IMouseFunction {

    /**
     * Line/Circle fitting the recorded points and paint the recognized basic
     * symbol onto the view.
     *
     * @param e A mouse event
     */
    @Override
    public void run(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        Controller.this.recognize();
        view.repaint(Controller.this.getPoints(), Controller.this.getAllSymbols());
      }
    }
  }

}
