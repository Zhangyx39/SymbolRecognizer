package recognizer.controller;

import java.awt.event.MouseEvent;

/**
 * An interface for the MouseFunction classes. List all functions that a
 * mouse functional class should support.
 */
public interface IMouseFunction {

  /**
   * This method takes a mouse event, a controller and a view to run functions.
   *
   * @param e A mouse event
   */
  void run(MouseEvent e);
}
