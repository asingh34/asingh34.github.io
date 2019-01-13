import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Board extends JPanel {
//setting up basic board 	
  private static final int BOARD_WIDTH = 640;
  private static final int BOARD_HEIGHT = 640;
  private static final Color BG_COLOR = new Color(0xffffff);
  private static final String FONT_NAME = "Arial";
  private static final int SQUARE_SIZE = 125;
  private static final int SQUARES_MARGIN = 18;

  //initializing Squares. win, loss, score, moves,
  private Square[] mySquares;
  boolean myWin = false;
  boolean myLose = false;
  int myScore = 0;
  int myMoves = 0;
  //creating board 
  public Board() {
    setPreferredSize(new Dimension(340, 400));
    setFocusable(true);
    clearBoard();
  }

  public boolean win() {
    return myWin;
  }
  public boolean lose() {
    return myLose;
  }
  public void setWin(boolean x) {
    myWin = x;
  }
  public void setLose(boolean x) {
    myLose = x;
  }
  public Square[]  squares() {
    return mySquares;
  }
  public int score () {
    return myScore;
  }
  //set score is reset every time the current score count is greater than the displayed current high schore
  public void setScore(int x) {
    if (x > myScore) {
		myScore = x;
	}
  }
  public int moveCount () {
    return myMoves;
  }
  public void incrMoveCount () {
    myMoves++;
  }

  public void  repaintBoard() {
    repaint();
  }
  //iterates through the rows to get values 
  public Square[] getRow(int index) {
    Square[] result = new Square[4];
    for (int i = 0; i < 4; i++) {
      result[i] = squareAt(i, index);
    }
    return result;
  }
  //iterates through the columns to get values (notice squareAt 'coordiantes' are at index, i vs i, index)
  public Square[] getCol(int index) {
    Square[] result = new Square[4];
    for (int i = 0; i < 4; i++) {
      result[i] = squareAt(index, i);
    }
    return result;
  }

  //sets a new row into the board called from move left and right if there has been a change to the linked list and values were merged
  public void setRow(int index, Square[] re) {
    for (int i = 0; i < 4; i++) {
        setSquareAt (i, index, re[i]);
    }
  }
 //sets a new column into the board called from move up and down if there has been a change to the linked list and values were merged\
  public void setCol(int index, Square[] re) {
    for (int i = 0; i < 4; i++) {
        setSquareAt (index, i, re[i]);
    }
  }
  //clear board method when called resets everything and places new randomly generated numbers
  public void clearBoard() {
    myScore = 0;
	myMoves = 0;
    myWin = false;
    myLose = false;
    
    //4*4 could also be written as 16 this was just for readabilities sake in terms of grasping the 4 x 4 matrix 
    mySquares = new Square[4 * 4];
    for (int i = 0; i < mySquares.length; i++) {
      mySquares[i] = new Square();
    }
    placeRandomNumber();
    placeRandomNumber();
  }
  
  //method that places a random number between the specified probabilities in the lab 
  public void placeRandomNumber() {
    List<Square> list = freeSpace();
    if (!freeSpace().isEmpty()) {
      int index = (int) (Math.random() * list.size()) % list.size();
      Square empty = list.get(index);
      empty.value = Math.random() < 0.8 ? 2 : 4;
      setScore(empty.value);
    }
  }
  
  //calculates empty spaces and returns a smaller list
  private List<Square> freeSpace() {
    final List<Square> list = new ArrayList<Square>(16);
    for (Square t : mySquares) {
      if (t.isEmpty()) {
        list.add(t);
      }
    }
    return list;
  }

  private boolean isFull() {
    return freeSpace().size() == 0;
  }
  
//checks to see if there are any valid moves left
  boolean canMove() {
    if (!isFull()) {
      return true;
    }
    for (int x = 0; x < 4; x++) {
      for (int y = 0; y < 4; y++) {
        Square t = squareAt(x, y);
        if ((x < 3 && t.value == squareAt(x + 1, y).value)
          || ((y < 3) && t.value == squareAt(x, y + 1).value)) {
          return true;
        }
        if ((x > 0 && t.value == squareAt(x - 1, y).value)
          || ((y > 0) && t.value == squareAt(x, y - 1).value)) {
          return true;
        }
      }
    }
    return false;
  }

  //gets a specific square from the array corresponding to column x and row y 
  public Square squareAt(int x, int y) {
    return mySquares[x + y * 4];
  }
  
  //sets a specific square at column x and row y 
  public void setSquareAt (int x, int y, Square val) {
    mySquares[ x + y*4 ] = val;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(BG_COLOR);
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 4; x++) {
        drawSquare(g, mySquares[x + y * 4], x, y);
      }
    }
  }
  
  //rendering the square
  private void drawSquare(Graphics g2, Square square, int x, int y) {
    Graphics2D g = ((Graphics2D) g2);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    int value = square.value;
    int xOffset = offsetCoors(x);
    int yOffset = offsetCoors(y);
    g.setColor(square.bgColor());
    g.fillRoundRect(xOffset, yOffset, SQUARE_SIZE, SQUARE_SIZE, 14, 14);
    g.setColor(square.textColor());
    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
    final Font font = new Font(FONT_NAME, Font.BOLD, size);
    g.setFont(font);

    String s = String.valueOf(value);
    final FontMetrics fm = getFontMetrics(font);

    final int w = fm.stringWidth(s);
    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

    if (value != 0)
      g.drawString(s, xOffset + (SQUARE_SIZE - w) / 2, yOffset + SQUARE_SIZE - (SQUARE_SIZE - h) / 2 - 2);

    if (myWin || myLose) {
      g.setColor(new Color(255, 255, 255, 30));
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(new Color(255, 0, 0));
      g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
      if (myWin) {
        g.drawString("You won!", 70, 140);
      }
      if (myLose) {
        g.drawString("Game over!", 60, 150);
        g.drawString("You lose!", 74, 200);
      }
      if (myWin || myLose) {
        g.setColor(new Color(0 , 0 , 0 , 30));
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        g.drawString("Press 'r' to play again", 10, BOARD_HEIGHT - 50);
      }
    }
    g.setColor(new Color(0,0,0, 30));
    g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
    g.drawString("Score: " + myScore + " Moves: " + myMoves, BOARD_WIDTH - 300, BOARD_HEIGHT - 50);

  }

  private static int offsetCoors(int arg) {
    return arg * (SQUARES_MARGIN + SQUARE_SIZE) + SQUARES_MARGIN;
  }

  public static void main(String[] args) {
    JFrame app = new JFrame();
    app.setTitle("2048 Project");
    app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    app.setSize(BOARD_WIDTH, BOARD_HEIGHT);
    app.setResizable(false);
	
	Board board = new Board();
    app.add(board);
	new Moves(board);

    app.setLocationRelativeTo(null);
    app.setVisible(true);
  }
}
