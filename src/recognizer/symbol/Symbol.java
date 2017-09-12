package recognizer.symbol;

/**
 * This interface require all methods that a symbol should support. A Symbol
 * can be one of a BasicSymbol or a CompositeSymbol.
 */
public interface Symbol {

  /**
   * Return a copy of the symbol. Everything of the returned symbol should be
   * the same as the original one, except the reference.
   * @return a copy of the symbol
   */
  Symbol copy();
}
