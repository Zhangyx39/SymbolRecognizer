package recognizer.model;

import recognizer.symbol.BasicSymbol;

/**
 * Interface for fitter class, outlines functionality that a fitter should support.
 */
public interface Fitter {

  /**
   * Method returns the goodness of fit to a basic symbol.
   *
   * @return goodness of fit as a double
   */
  double getGoodness();

  /**
   * Method returns the fitted symbol from the set of doodle points.
   *
   * @return A basic symbol.
   */
  BasicSymbol getFittedBasicSymbol();

}
