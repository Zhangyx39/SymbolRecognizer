package recognizer.symbol;

/**
 * Abstract class for decorator. It implements symbol class and saves code duplication.
 * It uses composition to save a Symbol as a delegate instance variable.
 */
public abstract class Decorator implements Symbol {

  //so that subclasses of this class can automatically inhered.
  protected Symbol delegate;

  public Decorator(Symbol sym) {
    this.delegate = sym;
  }

  @Override
  public Symbol copy() {
    return delegate.copy();
  }
}
