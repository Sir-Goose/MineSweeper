import java.util.Random;

/*
 * This is the cell class. It contains the code for the cell objects. It is responsible for storing
 * the cell data and contains all the methods that manipulate cells. Cells can be empty or contain
 * a mine. The cells are aware of their position on the board. They also keep track of the number of
 * adjacent mines.
 */
public class Cell {

  int AdjacentMines;  // number of adjacent mines
  boolean IsRevealed; // boolean to determine if the cell is revealed
  boolean IsMine; // boolean to determine if the cell is a mine
  int Row; // cell row
  int Col; // cell column
  boolean IsMarked; // boolean to determine if the cell is marked

  /*
   * This is the cell constructor. It takes in the cell row and column and a boolean to determine if
   * the cell can be a mine. It then sets the cell row and column and calls the isMine() method to
   * determine if the cell is a mine.
   */
  Cell(int i, int j, boolean canBeMine)
  {
    AdjacentMines = 0;
    IsRevealed = false;
    IsMine = isMine(canBeMine);
    Row = i;
    Col = j;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the markCell() method. It marks the cell by setting the IsMarked boolean to true.
   */
  public void markCell()
  {
    IsMarked = true;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the hideCell() method. It hides the cell by setting the IsRevealed boolean to false.
   */
  public void hideCell()
  {
    IsRevealed = false;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the isMine() method. It takes in a boolean to determine if the cell is a mine. It
   * then generates a random number between 0 and 6. If the number is 0, the cell is a mine.
   */
  boolean isMine(boolean canBeMine)
  {
    if (canBeMine)
    {
      Random random = new Random();
      int randomNumber = random.nextInt(7); // generates a random number between 0 and 6
      return randomNumber == 0; // returns true if the number is 0
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the reveal() method. It reveals the cell by setting the IsRevealed boolean to true.
   */
  public void reveal()
  {
    IsRevealed = true;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the findAdjacentMines() method. It takes in the board and loops through the adjacent
   * cells to determine the number of adjacent mines.
   */
  public void findAdjacentMines(Cell[][] cells)
  {
    if (IsMine)
    {
      return; // if the cell is a mine, return.
    }
    // array of directions to check for adjacent mines
    int[][] directions =
      {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
      };
    // loop through the directions array
    for (int[] direction : directions)
    {
      int newRow = Row + direction[0];
      int newCol = Col + direction[1];

      // if the new row and column are valid indices for the board, increment the number of adjacent
      // mines.
      if (isValidIndex(newRow, newCol, cells.length, cells[0].length))
      {
        if (cells[newRow][newCol].IsMine)
        {
          AdjacentMines++;
        }
      }
    }
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the isValidIndex() method. It takes in the row and column and the number of rows and
   * columns in the board. It returns true if the row and column are valid indices for the board.
   */
  public boolean isValidIndex(int row, int col, int numRows, int numCols)
  {
    return row >= 0 && row < numRows && col >= 0 && col < numCols;
  }
  //----------------------------------------------------------------------------------------------//
  /*
   * This is the overrided toString() method. It returns a string representation of the cell. The
   * return value changes depending on the status of the cell. If the cell is revealed, the return
   * value is the number of adjacent mines. If the cell is not revealed, the return value is a
   * question mark. If the cell is a mine and revealed, the return value is an asterisk.
   */
  @Override
  public String toString()
  {
    if (IsRevealed)
    {
      if (IsMine)
      {
        return "*";
      }
      else
      {
        return AdjacentMines + "";
      }
    }
    else
    {
      return "?";
    }
  }
}
//-----------------------------------------END-OF-FILE--------------------------------------------//
