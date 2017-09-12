package recognizer;

import recognizer.controller.Controller;
import recognizer.controller.IController;
import recognizer.model.IModel;
import recognizer.model.Model;
import recognizer.view.IView;
import recognizer.view.View;

/**
 * A runnable main class for recognizer assignment.
 */
public class ARunnable {

  /**
   * The main method to construct the .jar file.
   */
  public static void main(String[] s) {
    IModel model = new Model();
    IView view = new View(600, 600);
    IController controller = new Controller(model, view);
  }
}
