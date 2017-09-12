package recognizer.model;

import java.util.List;
import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Symbol;

/**
 * The interface for the model of the symbol recognizer. It shows all methods
 * that the model should support.
 */

public interface IRecognizer {

  /**
   * Add a basic symbol to the model. Same symbol with same address cannot be
   * added twice. However, two symbols with same attributes is allowed. Once
   * a basic symbol is added, the recognizer will try to canForm a
   * composite symbol automatically. If the recognition success, basic
   * symbols to form this composite symbol will be removed from the model and
   * the new composite symbol will be added to the model.
   * So far, a composite symbol can be one of a snowman or an equilateral
   * triangle or a triangle. More composite symbol types may be supported by
   * the model later.
   *
   * @param symbol a basic symbol which can be either a line or a circle
   * @throws IllegalArgumentException when the input is null
   */
  void addBasicSymbol(BasicSymbol symbol) throws IllegalArgumentException;

  /**
   * Get a copy of all the symbols in the model. They can be a mix of basic
   * symbols and composite symbols.
   * All symbol types: Line, Circle, Snowman, Triangle and EquilateralTriangle.
   *
   * @return a list of symbols
   */
  List<Symbol> getSymbols();

  /**
   * Take the center location and the radius as inputs and create a Circle
   * object.
   *
   * @param x the x coordinate of the center of the circle
   * @param y the y coordinate of the center of the circle
   * @param r the radius of the circle
   * @return a Circle object according to the input
   * @throws IllegalArgumentException when r is not positive
   */
  BasicSymbol createCircle(double x, double y, double r) throws
      IllegalArgumentException;

  /**
   * Take the location of two points as inputs and create a line segment.
   *
   * @param x1 the x coordinate of point 1
   * @param y1 the y coordinate of point 1
   * @param x2 the x coordinate of point 2
   * @param y2 the y coordinate of point 2
   * @return a Line object
   * @throws IllegalArgumentException when point 1 and point 2 is the same
   */
  BasicSymbol createLineSegment(double x1, double y1, double x2, double y2)
      throws IllegalArgumentException;
}
