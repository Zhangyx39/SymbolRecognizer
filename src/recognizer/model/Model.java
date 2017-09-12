package recognizer.model;

import java.util.ArrayList;
import java.util.List;

import recognizer.symbol.BasicSymbol;
import recognizer.symbol.IPoint;
import recognizer.symbol.Symbol;

/**
 * Implementation of the model class. It is the workhorse of the program.
 *
 * <p>It reuses the Recognizer class by composition. The class keeps a reference
 * to the Recognizer object by keeping it as an instance variable. It reuses its
 * getSymbols() method by calling the delegate methods.
 *
 * <p>It keeps track of the list of dragged points by mouse event, and recognize
 * them to basic symbols by fitting them to a Line or a Circle.
 *
 * <p>If the list of points has a line fitting goodness above a threshold, then
 * it is recognized to be a line, other wise a circle. A fitted basic symbol
 * will be added to the list of symbols if they are not null.
 */
public class Model implements IModel {

  private List<IPoint> points;
  private IRecognizer recognizerDelegate;
  private static final double THRESHOLD = 0.7;

  /**
   * Constructor to initialize points buffer and recognizer delegate.
   */
  public Model() {
    points = new ArrayList<>();
    recognizerDelegate = new Recognizer();
  }

  @Override
  public void recognize() {
    LineFitter lf = new LineFitter(points);
    CircleFitter cf = new CircleFitter(points);

    BasicSymbol resultLine = lf.getFittedBasicSymbol();
    BasicSymbol resultCircle = cf.getFittedBasicSymbol();

    //System.out.println("line goodness:" + lf.getGoodness());
    //System.out.println("circle goodness:" + cf.getGoodness());

    //if a line is not null, and has a high fitness, then add a line
    if (resultLine != null && lf.getGoodness() >= THRESHOLD) {
      recognizerDelegate.addBasicSymbol(resultLine);
    }
    //compare fitness of a line and a circle, add the higher one
    else if (resultLine != null && resultCircle != null) {
      recognizerDelegate.addBasicSymbol(
              lf.getGoodness() > cf.getGoodness() ? resultLine : resultCircle);
    }
    //if there is no line, then add circle if present
    else if (resultCircle != null) {
      recognizerDelegate.addBasicSymbol(resultCircle);
    }

    points.clear();
  }


  @Override
  public List<IPoint> getPoints() {
    List<IPoint> toReturn = new ArrayList<>();
    for (IPoint p : points) {
      toReturn.add(p.copy());
    }
    return toReturn;
  }

  @Override
  public void addPoint(IPoint p) {
    this.points.add(p);
  }

  @Override
  public List<Symbol> getSymbols() {
    return recognizerDelegate.getSymbols();
  }
}
