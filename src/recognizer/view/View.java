package recognizer.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import recognizer.symbol.BasicSymbol;
import recognizer.symbol.Circle;
import recognizer.symbol.CompositeSymbol;
import recognizer.symbol.IPoint;
import recognizer.symbol.Line;
import recognizer.symbol.Symbol;
import recognizer.symbol.DecoratorSymbolCenter;
import recognizer.symbol.DecoratorSymbolName;

/**
 * The implementation of view extending JFrame. It contains a panel which repaints
 * itself on command of the controller with data points given by the controller.
 */
public class View extends JFrame implements IView {

  private JPanel drawPanel;
  private List<IPoint> listOfPoints;
  private List<Symbol> listOfSymbols;


  /**
   * Constructor of the view sets Title, Width, Height, Close button, JPanel and visibility
   * of the JFrame.
   *
   * @param width frame width
   * @param height frame height
   */
  public View(int width, int height) {
    super();
    setTitle("Recognizer");
    setSize(width, height);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    listOfPoints = new ArrayList<>();
    listOfSymbols = new ArrayList<>();

    drawPanel = new MyPanel();

    this.add(drawPanel);
    setVisible(true);
  }

  @Override
  public void setMouseListener(MouseAdapter listener) {
    drawPanel.addMouseMotionListener(listener);
    drawPanel.addMouseListener(listener);
  }


  @Override
  public void repaint(List<IPoint> points, List<Symbol> symbols) {
    this.listOfPoints = points;
    this.listOfSymbols = symbols;
    drawPanel.repaint();
  }

  /**
   * The panel class represents the main drawing board.
   * Overriding paint component method allows program to paint symbols onto the board.
   */
  private class MyPanel extends JPanel {

    /**
     * Constructor sets a white background.
     */
    public MyPanel() {
      super();
      setBackground(Color.white);
    }

    /**
     * Paints user doodle, symbols, and symbol names.
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      //paints the user doodle
      drawDoodle(g);

      //paints all the symbols
      drawSymbol(g);
    }

    /**
     * Paint the doodle points stored in buffer of list of points in black.
     *
     * @param g Graphics
     */
    private void drawDoodle(Graphics g) {
      g.setColor(Color.black);
      if (listOfPoints.size() != 0) {
        //draw the line
        for (int i = 0; (i + 1) < listOfPoints.size(); i++) {
          //System.out.println(points.get(i).toString());
          IPoint start = listOfPoints.get(i);
          IPoint end = listOfPoints.get(i + 1);
          g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
        }
      }
    }

    /**
     * Paint the list of symbols in red, with their names in center.
     * If it is a composite symbol, draw its component first.
     *
     * @param g Graphics
     */
    private void drawSymbol(Graphics g) {
      g.setColor(Color.red);
      for (Symbol s : listOfSymbols) {

        //basic symbol
        if (s instanceof BasicSymbol) {
          drawBasicSymbol(g, (BasicSymbol) s);
        }

        //composite symbol
        else if (s instanceof CompositeSymbol) {
          drawCompositeSymbol(g, (CompositeSymbol) s);
        }

        //draw name at center of each symbol
        String dName = new DecoratorSymbolName(s).getNameString();
        IPoint dPos = new DecoratorSymbolCenter(s).getCenter();
        g.drawString(dName, (int) dPos.getX(), (int) dPos.getY());
      }
    }

    private void drawBasicSymbol(Graphics g, BasicSymbol s) {
      if (s instanceof Line) {
        IPoint p1 = ((Line) s).getEnd1();
        IPoint p2 = ((Line) s).getEnd2();
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
      }

      //oval: upper left x, upper left y, width, height
      if (s instanceof Circle) {
        IPoint c = ((Circle) s).getCenter();
        int r = (int) ((Circle) s).getRadius();
        g.drawOval((int) c.getX() - r, (int) c.getY() - r, r * 2, r * 2);
      }
    }

    private void drawCompositeSymbol(Graphics g, CompositeSymbol s) {
      List<Symbol> symbols = s.getComponents();
      for (Symbol symbol : symbols) {
        if (symbol instanceof BasicSymbol) {
          drawBasicSymbol(g, (BasicSymbol) symbol);
        } else if (symbol instanceof CompositeSymbol) {
          drawCompositeSymbol(g, (CompositeSymbol) symbol);
        }
      }
    }
  }

}
