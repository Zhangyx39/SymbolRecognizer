A brief description of our design: the model of the symbol recognizer.

Assignment 10 updates:

In this version, we did not change any functionality of the model from previous version.
We changed the imprecision threshold in CompositeSymbolAbstract class to a larger number
for better user experience.

Decorator on Symbol Class
We used the decorator design strategy to add two new features to the Symbol class,
without changing its original design.
- get symbol center, returns the center of each symbol as a point
- get symbol name, returns the symbol name as a string

                    +--------+
                    |        |
                    | Symbol <-----------------------------------------------------------+
                    |        |                                                           |
                    +---^----+                                                           |
                        |                                                                |
         +--------------+-----------+--------------------------------------+             |
         |                          |                                      |             |
 +-------+-------+       +----------+---------+                         +--+-------------+--+
 |               |       |                    |                         |   Abstract Class  |
 | Basic Symbol  |       | Composite Symboel  |                         +-------------------+
 |               |       |                    |                         |     Decorator     |
 +-----^---------+       +-----------+--------+                         +---------^---------+
       |                             |                                            |
       |                             |                                  +---------+---------+
    +--+----+            +--------------------------+                   |                   |
    |       |            |           |              |           +-------+-------+     +-----+---------+
+---+-+  +--+---+    +---+---+  +----+-----+  +-----+------+    | SymbolCenter  |     |  SymbolName   |
|Line |  |Circle|    |Snowman|  | Triangle |  |Equilateral |    +-+-------------+     +-+-------------+
+-----+  +------+    +-------+  +----------+  +------------+    | + getCenter() |     | + getName()   |
                                                                |               |     |               |
                                                                +---------------+     +---------------+



Model
The model class contains all the functionality of the recognizer project.
It stores a buffer of dragged points of user's doodle and fits these points to
either a line or a circle to be added to the list of symbols. Then the model
automatically recognize basic symbols into composite symbols and stores the
result to a list of symbols.

The model reuses the Recognizer class for detecting triangles and snowman while
adding a new feature to fit lines and circles to a list of points.
It is written with composition, using recognizer as a delegate. This way,
it does not change the recognizer class's original design.

Model features:
- add points, saves dragged points in a list
- get points, returns a copy of dragged points
- recognize, fits the dragged points to either a line or a circle with certain threshold,
add this basic shape to the list of shapes, and clears the list of stored dragged point after fitting.
- get symbols, returns a copy of list of symbols

View
The view serves as basic user interface. It contains a panel which repaints itself on
command of the controller, and prints out data points given by the controller.
The view has two mouse listeners set by the controller to gather user's inputs.

View methods:
- setMouseDragListener, to detect a dragging motion by user's mouse.
- setMouseListener, to detect a release motion by user's mouse button.
- repaint, to display points and strings given by the controller.

Controller
The controller is the main operation class of the entire program. On one hand,
it takes input from the view and decides what to do. On the other hand, it controls
how and when the model is used. It also controls what must be shown by the view and when.

Controller functionality:
First, the controller takes hold of the model and view.
Then, it sets mouse listeners to the view.

As the mouse listener detects a dragging movement, the controller calls the model to
stores those dragged points, and asks the view to display all these points.

When the mouse is released, the controller calls the model to fit these points into a line
or a circle, clears the points buffer, save the basic symbol to the list of symbols and recognize
into composite symbols. The controller also asks the view to display all the symbols.

The controller calls the model for adding a point, getting a list of points,
recognize(fit points to line and circle), get list of symbols, and set mouse listener


The Mouse Listeners
- MouseListener: MyMouseListener extends mouseAdapter and overrides three functions

for MousePressed and MouseDragged
- MousePressAndDragFunction class saves user doodle points to the controller.

for MouseReleased
- MouseReleaseFunction class fits the point to a basic symbol and clears all points

We let this two classes be public nested class inside the controller class.
This make the mouse listener easier to test.
Each mouse listener takes a MouseFunction object to construct.
The Function object carries the method that the mouse listener needs.



MVC Graph:

+-----------------------------------------------------------------------------------------------------------+
|                                                                                                           |
|                                               +------------------------------+                            |
|    +--------------------------+               |  View Class                  |           View             |
|    |         View             |               +------------------------------+                            |
|    +--------------------------+               | JPanel drawPanelbol)         |                            |
|    |                          |               | List^Point^ listOfPoints     |                            |
|    | + setMouseDragListener() |               | ListvSymbol^ listOfSymbols   |                            |
|    |                          +^--------------+ ...                          |                            |
|    | + setMouseListner()      |               +------------------------------+                            |
|    |                          |               +------------------------------+                            |
|    | + repaint()              |               | My Panel extends JPanel      |                            |
|    |                          |               +------------------------------+                            |
|    +----------+---------------+               | PaintComponent()             |                            |
|               |                               +------------------------------+                            |
|               |                                                                                           |
+-----------------------------------------------------------------------------------------------------------+
                |
                |
                |
+----------------------------------------------------------------------+            +----------------------------------------------------------------------+
|               |                                                      |            |                                                                      |
|               |                                                      |            |                                                                      |
|   +-----------+--------------+                                       |            |                +----------------------+                              |
|   |   Controller  Interface  | +-----------------------------------------------------------------+ |    Model  Interface  |                              |
|   +--------------------------+                                       |            |                +----------------------+                              |
|   |                          |                                       |            |                |                      |                 Model        |
|   | + getPoints()            |                                       |            |                | + getPoints()        |                              |
|   |                          |                                       |            |                |                      |                              |
|   | + recognizer()           |                                       |            |                | + recognize()        |                              |
|   |                          |                                       |            |                |                      |                              |
|   | + getAllSymbols()        |            Controller                 |            |                | + addPoints()        |                              |
|   |                          |                                       |            |                |                      |                              |
|   | + setMouseListner()      |                                       |            |                | + getSymbols()       |                              |
|   |                          |                                       |            |                |                      |                              |
|   +----------^---------------+                                       |            |                +----------^-----------+                              |
|              ^                                                       |            |                           |                                          |
|              |                                                       |            |                           |                                          |
|              |                                                       |            |                +----------+--------------------+                     |
|   +----------+------------+          +---------------------------+   |            |                |   Model Class                 |                     |
|   |  Controller Class     |          |  Mouse Function Interface |   |            |                +-------------------------------+                     |
|   +-----------------------+          |                           |   |            |                |points: List(Point)            |                     |
|   | symbols:List(Symbol)  |          |  +run()                   |   |            |                |IRecognizer recognizerDelegate |                     |
|   | points:List(Point)    |          +-------------^-------------+   |            |                |double THRESHOLD               |                     |
|   +-----------------------+                        |                 |            |                +-------------------------------+                     |
|   | ...                   |           +------------+--------+        +------------+                |  ...                          |                     |
|   +----+------------------+           |                     |        |            |                +^----------------------^-------+                     |
|        ^                    +---------+------------+        |        |            |                 |                      |                             |
|        |                    |MouseReleaseFunction  |        |        |            |                 |                      |                             |
|   +----+-----------+        +----------+-----------+        |        |            |  +--------------+------+             +-+-----------------------+     |
|   | MyMouseAdapter |                   |                    |        |            |  |Recognizer Interface |             |Fitter Interface         |     |
|   +----------------+                   |                    |        |            |  +---------------------+             +-------------------------+     |
|   |mouseRelease()  +-------------------+                    |        |            |  |                     |             |                         |     |
|   |mouseDragged()  |        +--------------------------+    |        |            |  |   +addBasicSymbol() |             | +getGoodness()          |     |
|   |mousePressed()  +--------+MousePressAndDragFunction +----+        |            |  |   +getSymbols()     |             |  getFittedBasicSymbol() |     |
|   +----------------+        +--------------------------+             |            |  |                     |             | +fit()                  |     |
|                                                                      |            |  |   +createLine()     |             |                         |     |
|                                                                      |            |  |   +createCircle()   |             +------------^------------+     |
|                                                                      |            |  |                     |                          |                  |
+----------------------------------------------------------------------+            |  +---------------------+                  +----------------+         |
                                                                                    |                                     +--------------+   +---v-------+ |
                                                                                    |                                     | CircleFitter |   |LineFitter | |
                                                                                    |                                     +--------------+   +-----------+ |
                                                                                    |                                     |              |   |           | |
                                                                                    |                                     |   +fit()     |   |  +fit()   | |
                                                                                    |                                     +--------------+   +-----------+ |
                                                                                    |                                                                      |
                                                                                    +----------------------------------------------------------------------+


Assumptions
1. Symbols are 2D symbols, and they can be represented in a Cartesian coordinate system.
2. Composite symbols are made of basic symbols. Composite symbols cannot make other composite symbols.
3. Symbols are added in sequence (e.g. We only check whether the last three line segments form a triangle).
4. Currently, there are only two types of basic symbols: line and circle.
5. Currently, there are only three types of composite symbols: snowman, triangle, and equilateral triangle.


Implementations of Recognition
Snowman:
1. Check if the input contains 3 circles.
2. Sort the circles in ascending order of radius.
3. Check if any of the two circles are in same size.
4. Check if the small circle is tangent to the medium circle with formula:
      Abs((radius1 + radius2) - distanceOfCenters)) < imprecision
5. Check if the medium circle is tangent to the large circle with the same formula.
6. Check if the distance from the center of small circle to the center of large circle equals to
   (radiusS + radiusM * 2 + radiusL). This can make sure the three circles are in a line and
   the medium circle is in the middle.

Triangle:
1. Check if the input contains 3 line segments.
2. Check if any of two line segments have same orientation (No triangle has parallel sides).
3. Compute delta(the largest distance allowed for two points count as one) with formula:
      delta = average(length of segments) * imprecision
4. Create a set of points. This set cannot accept a point with a distance to any point in the set
    that is less than delta.
5. Try to add all end points of all line segments to the set.
6. Check if the set has exactly three points, which means they form a triangle. The only situation
    that the set has three points but they cannot form a triangle is when two line segments
    are the same. We have checked this in step two.

Equilateral Triangle:
1. Check if the input can form a triangle.
2. Check if the three sides are equal in length with formula:
      Abs(length1 - length2) < imprecision


Explain our design:
Following the principle of "open to extend, close for modification", we designed our model with the structure shown below.


IRecognizer - is the interface that lists all the public methods that the model should support.
Two additional methods that we require: one to create a circle and the other to create a line segment.
These are similar to the getDeck method in Freecell, so the model can create basic symbols itself.


Recognizer - implements IRecognizer interface.
When a basic symbol is added, it checks if the last several symbols can form a composite symbol by trying to construct one.
If an exception is caught, they can not form this and try another one.
We leave the methods to recognize composite symbols inside the composite symbol classes.
So that if we want to recognize a rectangle or a balloon in the future, we can just write new classes and do not have to modify the model much.
This makes the model easy to extend.


Symbol -  is the interface that represents all kind of symbols.

BasicSymbol - is an interface that extends Symbol and it has two implementation: Circle and Line.


CompositeSymbol - extends Symbol as well, and it contains a list of basic symbols that forms it.
Each composite symbol has a method called canForm.
It checks if a list of basic symbols can form this composite symbol.
This method is invoked in the constructor.
If it returns false, the constructor will throw an exception.
More composite symbols can be supported in the future.
New composite symbols can extends the CompositeSymbolAbstract class and provide a reasonable canForm() method.


In the future, we can package all symbols and make their constructors private,
and provide only some factory methods.

This can avoid those symbols being exposed to the user. But we don't have to do this in this assignment.



Here is the class diagram of our design:

+----------------------------------------+               +---------------+
|               interface                |               |   interface   |
+----------------------------------------+               +---------------+
|              IRecognizer               |<>-------------|    Symbol     |
+----------------------------------------+               +---------------+
|addSymbol(BasicSymbol)                  |               |copy():Symbol  |
|getSymbol():List<Symbol>                |               +-------+-------+
|createCircle(double...):BasicSymbol     |                       ^
|createLineSegment(doubel..):BasicSymbol |       +---------------+-----------------+
+----------------+-----------------------+       |                                 |
                 ^                               |                                 |
                 |                               |                                 |
     +-----------+----------+          +---------+--------+      +-----------------+-----------------+
     |         class        |          |     interface    |      |            interface              |
     +----------------------+          +------------------+      +-----------------------------------+
     |     Recognizer       |          |    BasicSymbol   |----<>|          CompositeSymbol          |
     +----------------------+          +------------------+      +-----------------------------------+
     |symbols:List<Symbol>  |          |copy():Symbol     |      |copy():Symbol                      |
     +----------------------+          |toString():String |      |toString():String                  |
     |...                   |          +--------+---------+      |getComponents():List<BasicSymbol>  |
     +----------------------+                   ^                |canForm(List<BasicSymbol>):boolean |
                               +----------------+                +------------------+----------------+
                               |                |                                   ^
                      +--------+-----+    +-----+--------+                          |
                      |     class    |    |    class     |        +-----------------+-----------------+
                      +--------------+    +--------------+        |           abstract class          |
                      |     Line     |    |    Circle    |        +-----------------------------------+
                      +--------------+    +--------------+        |       CompositeSymbolAbstract     |
                      |end1:IPoint   |    |center:IPoint |        +-----------------------------------+
                      |end2:IPoint   |    |radius:double |        |basicSymbols:ListvBasicSymbolv     |
                      +--------------+    +--------------+        |imprecision:double                 |
                      |...           |    |...           |        +-----------------------------------+
                      +--------------+    +--------------+        |getComponents():List<BasicSymbol>  |
                             ^                  ^                 +-----------------+-----------------+
         +-------------------v------+           v                                   ^
         |        interface         |           |                  +----------------+-------+
         +--------------------------+           |                  |                        |
         |          IPoint          +-----------+         +--------+-----+            +-----+--------+
         +--------------------------+                     |     class    |            |    class     |
         |copy():IPoint             |                     +--------------+            +--------------+
         |distanceTo(IPoint):double |                     |   Triangle   |            |   Snowman    |
         |getX():double             |                     +--------------+            +--------------+
         |getY():double             |                     |...           |            |...           |
         +-------------+------------+                     +-------+------+            +--------------+
                       ^                                          ^
                +------+------+                                   |
                |    class    |                         +---------+----------+
                +-------------+                         |        class       |
                |    Point    |                         +--------------------+
                +-------------+                         |EquilateralTriangle |
                |x:double     |                         +--------------------+
                |y:double     |                         |...                 |
                +-------------+                         +--------------------+
                |...          |
                +-------------+
