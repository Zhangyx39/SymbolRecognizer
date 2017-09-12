package recognizer.symbol;

/**
 * The BasicSymbol interface extends the Symbol interface. It represents
 * basic symbols. Basic symbols can form composite symbols. For example,
 * three line segments form a triangle.
 * So far, there are only two types of basic symbols: Circle and Line.
 */
public interface BasicSymbol extends Symbol {

  /**
   * The format of the returned string should be:
   * (symbolType attribute1 attribute2 ...)
   * For example a circle should return:
   * (Circle (x.xx, y.yy) r.rr)
   * A line segment should return:
   * (Line (x.xx, y.yy) (x.xx, y.xx))
   *
   * @return a formatted string
   */
  String toString();
}
