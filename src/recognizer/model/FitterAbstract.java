package recognizer.model;

import java.util.List;
import recognizer.symbol.BasicSymbol;
import recognizer.symbol.IPoint;

/**
 * Abstract class for line and circle fitters to avoid code duplication.
 * The fit method sets the goodness of fit and basic symbol variable of each class.
 */
public abstract class FitterAbstract implements Fitter {

  protected List<IPoint> points;
  protected double goodness;
  protected BasicSymbol basicSymbol;


  public FitterAbstract(List<IPoint> points) {
    this.points = points;
    fit();
  }

  @Override
  public double getGoodness() {
    return goodness;
  }

  @Override
  public BasicSymbol getFittedBasicSymbol() {
    return basicSymbol;
  }

  /**
   * This method sets the goodness of fit and basic Symbol instance variable of the class.
   */
  public abstract void fit();
}
