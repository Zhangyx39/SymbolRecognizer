package recognizer.symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represents a composite symbol with a list of basic
 * symbols. The default imprecision is 0.01. It means there is a 1%
 * imperfection allowed for the basic symbols to form a composite symbol.
 * This class makes it easier to add support for other composite symbols in the
 * future.
 */

/**
 * As of assignment 10, we changed the imprecision to 0.1 to allow easier construction
 * of composite triangle by the user on graphic user interface.
 */
public abstract class CompositeSymbolAbstract implements CompositeSymbol {

  protected final List<Symbol> symbols;
  protected double imprecision;

  public CompositeSymbolAbstract() {
    this.symbols = new ArrayList<>();
    this.imprecision = 0.1;
  }

  @Override
  public List<Symbol> getComponents() {
    List<Symbol> newList = new ArrayList<>();
    for (Symbol s : symbols) {
      newList.add(s.copy());
    }
    return newList;
  }
}
