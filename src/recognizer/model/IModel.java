package recognizer.model;

import java.util.List;
import recognizer.symbol.IPoint;
import recognizer.symbol.Symbol;

/**
 * This model interface outlines all the functionality that a model should supprot.
 */
public interface IModel {

  /**
   * Getting the copy list of points of one dragging action, acting as a buffer
   * to save user's doodle until mouse release. A copy of list of points are returns
   * to avoid mutation outside of Model class.
   *
   * @return List of points
   */
  List<IPoint> getPoints();

  /**
   * Recognize basic symbols with given set of points in the buffer.
   * It utilizes the fitter class to recognize Lines and Circles.
   * It clean the buffer automatically afterwards.
   */
  void recognize();

  /**
   * Adding a point to the buffer, remembering points of one dragging action,
   * to save user's doodle until mouse release.
   * It only adds if the point is valid, non-null, else it does nothing.
   */
  void addPoint(IPoint p);

  /**
   * Getting all the symbols after recognition process,
   * including basic and composite ones.
   *
   * @return list of symbols with center point and critical fields.
   */
  List<Symbol> getSymbols();
}
