package recognizer.symbol;

import java.util.List;

/**
 * This CompositeSymbol interface extends the Symbol interface. A composite
 * symbol contains several basic symbols. Different composite symbols have
 * different rules to construct.
 */
public interface CompositeSymbol extends Symbol {

  /**
   * The format of the returned string should be:
   * (symbolType component1 component2 ...)
   * For example a snowman should return:
   * (Snowman Circle1 CirCle2 Circle3))
   *
   * @return a formatted string
   */
  String toString();

  /**
   * Return a copy of the list of basic symbols that form this composite symbol.
   * @return a list of BasicSymbol
   */
  List<Symbol> getComponents();

  /**
   * Given a list of basic symbols check if they can form this composite symbol.
   * This method should be called in the constructor of every composite symbol.
   * @param symbols a list of BasicSymbol
   * @return true if they satisfy the rule to form this composite symbol
   */
  boolean canForm(List<Symbol> symbols);
}
