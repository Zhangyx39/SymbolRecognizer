package recognizer.view;

import java.awt.event.MouseAdapter;
import java.util.List;
import recognizer.symbol.IPoint;
import recognizer.symbol.Symbol;

/**
 * The interface for recognizer view， outlines all functionality a view class should support.
 * The view class sets canvas size, get mouse events, and draw objects on to the canvas.
 */
public interface IView {

  /**
   * This method will be called to set a mouse listener for press, drag and release.
   */
  void setMouseListener(MouseAdapter listener);

  /**
   * This method will be called to repaint the view with given points and list of symbols.
   *
   * @param points List of points
   * @param symbols List of symbols
   */
  void repaint(List<IPoint> points, List<Symbol> symbols);
}
