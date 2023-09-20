import java.util.Scanner;

/*
 * This is the game class. It contains the code for running the game. It is responsible for storing
 *  the game and the main game loop. It contains methods for checking the win condition as well as
 * methods for making moves and checking if a move is in bounds.
 */
public class Game
{

  Scanner Scanner; // used to get user input
  Board Board;  // stores the game board
  int Row;  // stores the row number
  int Col;  // stores the column number

  /*
   * This is the game constructor. It creates a Scanner object.
   */
  public Game()
  {
    Scanner = new Scanner(System.in);
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the start() method. It is called from the main method in Main.java. It is responsible
   * for setting up the board and beginning the game loop.
   */
  public void start()
  {
    setUpBoard(); // calls the setUpBoard() method to set up the board.

    while (true) // infinite loop to keep the game loop running.
    {
      gameLoop();
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the gameLoop() method. It is responsible for running the game loop. It calls the
   * checkWinCondition() method to check if the win condition has been met. It then calls the
   * print() method from the Board class to print the board. It then gets the user's move and calls
   * the makeMove() method to make the move.
   */
  private void gameLoop()
  {
    Board.print(); // calls the print() method from the Board class to print the board.
    System.out.println();
    if (checkWinCondition()) // calls the checkWinCondition() method to check if the win condition
                             // has been met.
    {
      System.out.println("You win!");
      System.exit(0);
    }

    Row = 1000; // sets the row number to 1000
    Col = 1000; // sets the column number to 1000

    while (!checkMoveInBounds(Row, Col)) // loops until the user enters a valid move.
    {
      System.out.println("Enter a row number: ");
      Row = Scanner.nextInt();
      System.out.println("Enter a column number: ");
      Col = Scanner.nextInt();
    }
    makeMove(Row, Col); // calls the makeMove() method to make the move.
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the setUpBoard() method. It is responsible for setting up the board. It asks the user
   * for the board size and then creates a new Board object.
   */
  private void setUpBoard()
  {
    System.out.println("WELCOME TO MINESWEEPER \n"); // prints the welcome message

    System.out.println("Choose board size or enter 0 for default: \n"); // asks the user for the
                                                                         // board size
    System.out.println("Height: ");
    int height = Scanner.nextInt();
    System.out.println("Width: ");
    int width = Scanner.nextInt();

    if (height == 0 || width == 0) // if the user enters 0 for the height or width, the default
                                   // height and width are used.
    {
      height = 10;
      width = 10;
    }
    Board = new Board(width, height); // creates a new Board object.
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the makeMove() method. It takes in the row and column numbers and makes the move. If
   * the move is a mine, the game ends. If the move is not a mine, the cell is revealed and the
   * revealCells() method from the Board class is called.
   */
  void makeMove(int row, int col)
  {
    if (Board.cells[row][col].IsMine) // if the cell is a mine, the game ends.
    {
      Board.endGameRevealAll();
      Board.print();
      System.out.println("You lose!"); // prints the lose message
      System.exit(0);
    }
    else
    {
      // if the cell is not a mine, the cell is revealed and the revealCells() method from the Board
      Board.cells[row][col].reveal();
      Board.runRevealCells(row, col);
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the checkMoveInBounds() method. It takes in the row and column numbers and checks if
   * they are in bounds.
   */
  boolean checkMoveInBounds(int row, int col)
  { // checks if the row and column numbers are in bounds.
    if (row < 0 || row >= Board.Width)
    {
      return false;
    }
    if (col < 0 || col >= Board.Height)
    {
      return false;
    }
    return true;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the checkWinCondition() method. It checks if the win condition has been met.
   */
  boolean checkWinCondition()
  {
    int cellsRevealed = 0; // stores the number of cells revealed

    for (int i = 0; i < Board.Width; i++)
    {
      for (int j = 0; j < Board.Height; j++)
      {
        if (Board.cells[i][j].IsRevealed)
        {
          cellsRevealed++; // increments the number of cells revealed
        }
      }
    }
    return cellsRevealed == (Board.Width * Board.Height) - 10; // returns true if the number of
                                                               // cells revealed is equal to the
                                                               // number of cells minus the number
                                                               // of mines.
  }
}
