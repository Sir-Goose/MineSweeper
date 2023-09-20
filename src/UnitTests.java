import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitTests
{
  @Test
  void testBoardSize()
  {
    Board board = new Board(5, 5);
    Assertions.assertEquals(25, board.getBoardSize());
  }

  @Test
  void testMineGeneration()
  {
    int mines = 0;
    Board board = new Board(5, 5);

    for (int i = 0; i < board.Width; i++)
    {
      for (int j = 0; j < board.Height; j++)
      {
        if (board.cells[i][j].IsMine)
        {
          mines++;
        }
      }
    }
    Assertions.assertTrue(mines <= 10);
  }

  @Test
  void testIsValidIndex()
  {
    Board board = new Board(5, 5);
    Assertions.assertTrue(board.cells[0][0].isValidIndex(0, 0, 5, 5));
    Assertions.assertFalse(board.cells[0][0].isValidIndex(0, 5, 5, 5));
    Assertions.assertFalse(board.cells[0][0].isValidIndex(5, 0, 5, 5));
    Assertions.assertFalse(board.cells[0][0].isValidIndex(5, 5, 5, 5));
  }

  @Test
  void testMoveInBounds()
  {
    Game game = new Game();
    game.Board = new Board(5, 5);

    Assertions.assertTrue(game.checkMoveInBounds(0, 0));
    Assertions.assertFalse(game.checkMoveInBounds(0, 5));
    Assertions.assertFalse(game.checkMoveInBounds(5, 0));
    Assertions.assertFalse(game.checkMoveInBounds(5, 5));
  }

  @Test
  void testEndGameRevealAll()
  {
    Board board = new Board(5, 5);
    board.endGameRevealAll();
    for (int i = 0; i < board.Width; i++)
    {
      for (int j = 0; j < board.Height; j++)
      {
        Assertions.assertTrue(board.cells[i][j].IsRevealed);
      }
    }
  }

  @Test
  void testCheckWinCondition()
  {
    Board board = new Board(100, 100);
    Game game = new Game();
    game.Board = board;

    for (int i = 0; i < board.Width; i++)
    {
      for (int j = 0; j < board.Height; j++) {
        board.cells[i][j].reveal();
      }
    }
    for (int i = 0; i < 10; i++)
    {
      board.cells[i][i].hideCell();
    }
    Assertions.assertTrue(game.checkWinCondition());
  }
}
