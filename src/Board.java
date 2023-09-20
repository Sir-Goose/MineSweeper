import java.util.ArrayList;
/*
 * This is the board class. It contains the code for game board. It is responsible for storing
 * the board data and contains all the methods that manipulate the board.
 */

public class Board
{

  public Cell[][] cells; // matrix of cell objects
  public int Width; // board width
  public int Height; // board height
  public int BoardsGenerated = 0; // number of boards generated

  /*
   * This is the board constructor. It takes in the desired width and height and generates a board
   * with the specified dimensions.
   */
  public Board(int width, int height)
  {
    this.Width = width; // sets the board width
    this.Height = height; // sets the board height
    generateBoard(); // calls the generateBoard() method to generate the board.
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the getBoardSize() method. It returns the size of the board.
   */
  public int getBoardSize()
  {
    return Width * Height; // returns the board size
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the generateBoard() method. It generates the board by creating a matrix of cell
   * objects. It then loops through the matrix and creates a cell object for each element. It also
   * keeps track of the number of mines generated.
   */
  private void generateBoard()
  {
    BoardsGenerated++; // increments the number of boards generated
    int mines = 0; // stores the number of mines generated
    boolean canBeMine = true; // boolean to determine if a cell can be a mine

    cells = new Cell[Width][Height]; // creates a matrix of cell objects
    for (int i = 0; i < Width; i++)
    {
      for (int j = 0; j < Height; j++)
      {
        if (mines >= 10) { // if the number of mines generated is greater than or equal to 10, the
                           // cell cannot be a mine.
          canBeMine = false;
        }
        cells[i][j] = new Cell(i, j, canBeMine);
        if (cells[i][j].IsMine)
        {
          mines++; // increments the number of mines generated
        }
      }
    }
    makeCellsSelfAware(); // calls the makeCellsSelfAware() method to make the cells aware of their
                          // surroundings and where they are in the matrix.
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the makeCellsSelfAware() method. It loops through the matrix of cell objects and calls
   * the findAdjacentMines() method for each cell object.
   */
  private void makeCellsSelfAware()
  {
    for (int i = 0; i < Width; i++)
    {
      for (int j = 0; j < Height; j++)
      {
        cells[i][j].findAdjacentMines(cells); // calls the findAdjacentMines() method for each cell
                                               // object.
      }
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the print() method. It prints the board to the console.
   */
  public void print()
  {
    int rowNumber = 0; // stores the row number

    System.out.print("   ");
    while (rowNumber < Width) // prints the row numbers
    {
      System.out.print("   " + rowNumber);
      rowNumber++;
    }
    System.out.println();

    // prints the top border
    for (int i = 0; i < Width; i++)
    {
      System.out.print(i + "  ");
      for (int j = 0; j < Height; j++)
      {
        System.out.print(" | "); // prints the vertical bar for each cell
        System.out.print(cells[i][j]); // prints the cell
      }
      System.out.print(" |"); // Print the last vertical bar for each row
      System.out.println(); // Move to the next line for the next row
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the isValidIndex() method. It takes in the row and column and checks if they are valid
   * indices for the board.
   */
  private boolean isValidIndex(int row, int col, int numRows, int numCols)
  {
    return row >= 0 && row < numRows && col >= 0 && col < numCols; // returns true if the row and
                                                                   // column are valid indices for
                                                                   // the board.
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the runRevealCells() method. It takes in the row and column and calls the revealCells()
   * method 100 times.
   */
  public void runRevealCells(int row, int col)
  {
    int i = 0;

    while (i < 100)
    {
      revealCells(row, col); // calls the revealCells() method
      getMarkedCells(); // calls the getMarkedCells() method
      i++;
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the revealCells() method. It takes in the row and column and reveals the cell at that
   * location. It then loops through the directly adjacent cells and reveals them as well if they
   * are not mines. It also marks the cells that are adjacent to mines if they themselves have
   * 0 adjacent mines.
   */
  public void revealCells(int row, int col)
  {
    cells[row][col].markCell();
    int[][] directions =  // array of directions to check
      {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
      };
    // loop through the directions array
    for (int[] direction : directions)
    {
      int newRow = row + direction[0];
      int newCol = col + direction[1];

      if (isValidIndex(newRow, newCol, cells.length, cells[0].length)) // if the new row and column
                                                                       // are valid indices for the
                                                                       // board, reveal the cell
                                                                       // at that location.
      {
        if (!cells[newRow][newCol].IsMine)
        {
          cells[newRow][newCol].reveal();
          if (cells[newRow][newCol].AdjacentMines == 0) // if the cell has 0 adjacent mines, mark
                                                        // the cell.
          {
            cells[newRow][newCol].markCell(); // mark the cell if it has 0 adjacent mines
          }
        }
      }
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the getMarkedCells() method. It loops through the board and adds all the marked cells
   * to an array list. It then loops through the array list and calls the revealCells() method for
   * each cell in the array list.
   */
  public void getMarkedCells()
  {
    ArrayList<Cell> markedCells = new ArrayList<>();
    for (int i = 0; i < Width; i++)
    {
      for (int j = 0; j < Height; j++)
      {
        if (cells[i][j].IsMarked)
        {
          markedCells.add(cells[i][j]); // adds the marked cell to the array list
        }
      }
    }
    for (Cell cell : markedCells)
    {
      revealCells(cell.Row, cell.Col); // calls the revealCells() method for each cell in the array
                                       // list.
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the endGameRevealAll() method. It loops through the board and reveals all the cells.
   */
  public void endGameRevealAll()
  {
    for (int i = 0; i < Width; i++)
    {
      for (int j = 0; j < Height; j++)
      {
        cells[i][j].reveal(); // reveals the cell
      }
    }
  }
}
//---------------------------------------END-OF-FILE----------------------------------------------//
