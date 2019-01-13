
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Moves {

//initializing 
  private Board myBoard;
  private Square[] mySquares;
  private Boolean validMove = false;

  public Moves(Board board) {
    myBoard = board;
    mySquares = myBoard.squares();

    myBoard.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
		//System.out.print("keypress:");
    	  
    	  	//first key listeners implemented are the R, Q commands. R will restart game with a confirmation message
        if (e.getKeyText(e.getKeyCode()).equals("R")) {
          System.out.println ("Restart");
          int confirm = JOptionPane.showConfirmDialog(null,"are you sure you want to Restart?","Restart",JOptionPane.YES_NO_OPTION, 1);
          if(confirm == 0) {
          	myBoard.clearBoard();
			myBoard.repaintBoard();
          }
        }
        //Q command will quit the game with a confirmation message. 
        else if (e.getKeyText(e.getKeyCode()).equals("Q")) {
          System.out.println ("Quit");
          int confirm = JOptionPane.showConfirmDialog(null,"are you sure you want to Exit?","Exit",JOptionPane.YES_NO_OPTION, 1);
          if(confirm == 0) {
            System.exit(0);
          }
        }
       
		else {
			//quick check to see if any moves are left at all and game can continued to be played 
			if (!myBoard.canMove()) {
			  myBoard.setLose( true );
			}
			
			//Switch statement listening for left, right, up and down keys.
	        //Once recognized as a valid keystroke system will call the specific move method for the corresponding key 
			if (!myBoard.win() && !myBoard.lose()) {
			  switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
				  System.out.print ("left");
				  left();
				  break;
				case KeyEvent.VK_RIGHT:
				  System.out.print ("right");
				  right();
				  break;
				case KeyEvent.VK_DOWN:
				  System.out.print ("down");
				  down();
				  break;
				case KeyEvent.VK_UP:
				  System.out.print ("up");
				  up();
				  break;
				default:
                  validMove = false;
				  System.out.println ("ILLEGAL KEY");
			  }
			}
			
			//This if statement checks for the validity of the moves and will out print in the console each moves validity or not  
			if (validMove) {
    			myBoard.incrMoveCount();
				System.out.println (" valid");
			}
			else {
				System.out.println (" invalid");
			}
			
			//One  more check to see if game can stil be played, if not will display loss.
			if (!myBoard.win() && !myBoard.canMove()) {
			  myBoard.setLose(true);
			}
			
			myBoard.repaintBoard();
			System.out.println("Max: " + myBoard.score() + " Moves: " + myBoard.moveCount() );
		}
      }
    });
    //calling the clearboard method 
    myBoard.clearBoard();
  }


  public void left() {
    validMove = false;
    for (int i = 0; i < 4; i++) {
      Square[] row = myBoard.getRow(i);
      //calls mergerLeft method by inputing the list from after moveLeft method was applied to the list.
      Square[] merged = mergeLeft(moveLeft(row));
      //replace old linked list with new list with merged values
      myBoard.setRow(i, merged);
      //check the two lists to make sure they are in fact different now and that the row was merged. 
      if (!compare(row, merged)) {
		validMove = true;
      }
    }
    //important note here is that the new randomly generated number will only be placed if the move made was valid. 
    if (validMove) {
      myBoard.placeRandomNumber();
    }
  }
  
  //same general logic as left() method
  public void right() {
    validMove = false;
    for (int i = 0; i < 4; i++) {
      Square[] row = myBoard.getRow(i);
      Square[] merged = mergeRight(moveRight(row));
      myBoard.setRow(i, merged);
      if (!compare(row, merged)) {
		validMove = true;
      }
    }

    if (validMove) {
      myBoard.placeRandomNumber();
    }
  }
  
  //same general logic as left() method
  public void up() {
    validMove = false;
    for (int i = 0; i < 4; i++) {
      Square[] col = myBoard.getCol(i);
      Square[] merged = mergeUp(moveUp(col));
      myBoard.setCol(i, merged);
      if (!compare(col, merged)) {
		validMove = true;
      }
    }

    if (validMove) {
      myBoard.placeRandomNumber();
    }
  }
  
  //same general logic as left() method
  public void down() {
    validMove = false;
    for (int i = 0; i < 4; i++) {
      Square[] col = myBoard.getCol(i);
      Square[] merged = mergeDown(moveDown(col));
      myBoard.setCol(i, merged);
      if (!compare(col, merged)) {
		validMove = true;
      }
    }

    if (validMove) {
      myBoard.placeRandomNumber();
    }
  }


// basic compare method I made for lists. Used for checking to see if the merged list and the old list are different. 
  private boolean compare(Square[] list1, Square[] list2) {
    if (list1 == list2) {
      return true;
    } else if (list1.length != list2.length) {
      return false;
    }
    
    //any two corresponding values not equal implies false
    for (int i = 0; i < list1.length; i++) {
      if (list1[i].value != list2[i].value) {
        return false;
      }
    }
    return true;
  }

//moveLeft method for moving square NOT merging.
  private Square[] moveLeft(Square[] oldList) {
    LinkedList<Square> l = new LinkedList<Square>();
//Checks to see if there are any non empty values in the list, if true then it moves the value to the last position.
    for (int i = 0; i < 4; i++) {
      if (!oldList[i].isEmpty())
        l.addLast(oldList[i]);
    }
    if (l.size() == 0) {
      return oldList;
//Pads the row with blank squares after all the values (if any) have been moved next to each other
    } else {
      Square[] newList = new Square[4];
      padBottomToSize(l, 4);
      for (int i = 0; i < 4; i++) {
        newList[i] = l.removeFirst();
      }
      return newList;
    }
  }

//same general logic as moveLeft important to note that it increments in the other direction since it is going right.
  private Square[] moveRight(Square[] oldList) {
    LinkedList<Square> l = new LinkedList<Square>();
    for (int i = 3; i >= 0; i--) {
      if (!oldList[i].isEmpty())
        l.addFirst(oldList[i]);
    }
    if (l.size() == 0) {
      return oldList;
    } else {
      Square[] newList = new Square[4];
      padTopToSize(l, 4);
      for (int i = 0; i < 4; i++) {
        newList[i] = l.removeFirst();
      }
      return newList;
    }
  }

  //since java works with one long list vs a matrix the up and down methods work by calling left and right on them respectively. 
  private Square[] moveUp(Square[] oldList) {
    return moveLeft(oldList);
  }

  private Square[] moveDown(Square[] oldList) {
    return moveRight(oldList);
  }

  //merge methods 
  private Square[] mergeLeft(Square[] oldLine) {
    LinkedList<Square> list = new LinkedList<Square>();
    
    //after the move for loop iterates thru (square must have a value)
    //checks to see if num and the value next to it is the same 
    //if it is the same then multiply num by 2. This acts as the "merge"
    for (int i = 0; i < 4 && !oldLine[i].isEmpty(); i++) {
    		int num = oldLine[i].value;
      if (i < 3 && num== oldLine[i + 1].value) {
        num *= 2;
        myBoard.setScore(num);
        //checks to see if you've reached 2048 AKA a win 
        int ourTarget = 2048;
        if (myBoard.score() == ourTarget) {
          myBoard.setWin( true );
        }
        i++;
      }
      list.add(new Square(num));
    }
    if (list.size() == 0) {
      return oldLine;
      //pads the list again like in the move method to ensure to board has it's blank squares
    } else {
      padBottomToSize(list, 4);
      return list.toArray(new Square[4]);
    }
  }
//same logic as mergeLeft, but increments in the other direction and checks the other side of the squar for a similar value
  private Square[] mergeRight(Square[] oldLine) {
    LinkedList<Square> list = new LinkedList<Square>();
    for (int i = 3; i >= 0 && !oldLine[i].isEmpty(); i--) {
      int num = oldLine[i].value;
      if (i > 0 && oldLine[i].value == oldLine[i - 1].value) {
        num *= 2;
        myBoard.setScore(num);
        int ourTarget = 2048;
        if (num == ourTarget) {
          myBoard.setWin( true );
        }
        i--;
      }
      list.add(0,new Square(num));
    }
    if (list.size() == 0) {
      return oldLine;
    } else {
      padTopToSize(list, 4);
      return list.toArray(new Square[4]);
    }
  }
  
  //the same logic with moving up and down and how it is mirrored by left and right applies to the merge methods as well 
  private Square[] mergeUp(Square[] oldLine) {
    return mergeLeft(oldLine);
  }

  private Square[] mergeDown(Square[] oldLine) {
    return mergeRight(oldLine);
  }

  //fills lists to size 4 with empty squares on bottom 
  private static void padBottomToSize(java.util.List<Square> l, int s) {
    while (l.size() != s) {
      l.add(new Square());
    }
  }
  //fills lists to size 4 with empty squares on top 
  private static void padTopToSize(java.util.List<Square> l, int s) {
    while (l.size() != s) {
      l.add(0, new Square());
    }
  }



}
