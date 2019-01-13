Anant Singh 
CSC 172 Project 1: 2048
0//9/18
asingh34
3009295
I did not collaborate with anyone on this project

Welcome to 2048. I have split my project into three classes. Board.java, Moves.java, and Square.java. 
The file names are representative of what each class does. In the Board class I have build a GUI out of java 
graphics. The board class also contains my win and loss variables, and getters and setters for them as 
well. It also contains helpful getters and setters for the columns and rows of the 4X4. The squareAt method 
returns the square at a specific x and y coordinate on the matrix. The board class contains other various 
methods which are helpful to the program and game in general. Some of these include clearing the board, placing
a random number, checking for free space on the board, checks to see if there any moves left on the board, drawing 
the various labels, and the main method. 

The Moves class contains the meat for how the game works in terms of play. There are action event listeners set up 
to monitor keystrokes and return specific outcomes. I implemented the R and Q (restart and quit) methods first. 
These methods will first ask for a confirmation on both commands. When executed R will clear the board and repaint 
one ready for new play. When executed Q will exit the game completely. Next the game always checks to see if it still 
possible to play by analyzing if there are any valid moves left. Next the switch statement is what allows the game 
to be played. I did not implement ASWD movement but instead opted for the the left,right,up, and down keystrokes. When 
any of the directional keys are pressed a method created for the specific direction is called: left(); right(); etc. 
Below the switch statement is a validity checker. Only valid moves are counted in this game and in result the move
incremented only increments on valid moves and only valid moves are reported on the screen. In the console there will 
be print messages explicitly saying the validity of each move. The move methods work as follows: The left(); , right(); 
methods work by the use of 4 other methods for moving the squares and merging the squares. Each direction has it’s own 
move and merge. The move methods move all the squares to the desired direction as far as possible by creating a new list 
from the existing and padding the empty spaces in the list with empty squares. When this list is created it is inputed 
into one of the merge methods (depends on direction) the merge methods iterate through the columns and rows and checks 
for squares that are next to each other of the same value. If the squares have the same value a new list is created with 
the value of the compared square multiplied by 2. This was my logic for how to complete merges. Every “merge” is essentially 
multiplying one of the desired merge squares by 2. So by creating a new list with the one of the numbers completely deleted 
and the other multiplied by 2 acts as a merge. 

The third and final class is the square object which creates the squares used for play. The comments in the code will describe 
the methods more in depth and my thinking on them and how they work. My game ends and you win when you reach the number 2048, 
I was not able to implement going passed it.


 
