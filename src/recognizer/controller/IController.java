package recognizer.controller;

import java.awt.event.MouseAdapter;
import java.util.List;

import recognizer.symbol.IPoint;
import recognizer.symbol.Symbol;

/**
 * The controller interface lists all necessary functions that the controller
 * class should support.
 */
public interface IController {


  /**
   * Adding points to the model to remember user's doodle.
   * Only valid points are added, null ones are ignored.
   *
   * @param p a point of x and y position.
   */
  void addPoint(IPoint p);

  /**
   * Getting the list of points of one dragging action, acting as a buffer
   * to save user's doodle until mouse release.
   *
   * @return List of points
   */
  List<IPoint> getPoints();

  /**
   * On mouse release, the controller calls model to start line and circle
   * fitting, to replace user doodle with basic symbols.
   */
  void recognize();

  /**
   * Getting all the symbols stored in the model.
   *
   * @return list of symbols including basic and composite.
   */
  List<Symbol> getAllSymbols();

  /**
   * Set a MouseAdapter and a MouseMotionAdapter to the view.
   * The MouseAdapter and the MouseMotionAdapter should support interaction
   * between the controller and the view when mouseDragged and mouseReleased
   * is called.
   *
   * @param adapter a mouse listener
   */
  void setMouseListener(MouseAdapter adapter);
}
