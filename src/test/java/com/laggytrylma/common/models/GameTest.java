package com.laggytrylma.common.models;

import com.laggytrylma.common.rules.AllPiecesInTargetWinRule;
import com.laggytrylma.common.rules.FarMovement;
import com.laggytrylma.common.rules.NearMovement;
import com.laggytrylma.common.rules.RuleInterface;
import com.laggytrylma.helpers.AbstractSystemOutCatch;
import com.laggytrylma.utils.Logger;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest extends AbstractSystemOutCatch {
  @Test
  public void testGameSetCurrentPlayer() {
    Game test = new Game();
    Player player = new Player(1, "1", new Color(0, 0, 0));
    test.setCurrentPlayer(player);
    assertEquals(test.getCurrentPlayer().toString(), "Player: 1 Color: java.awt.Color[r=0,g=0,b=0] Id: 1");
  }

  @Test
  public void testGameAddWrongRule() {
    Logger.setDoLogTime(false);
    Game test = new Game();
    test.addRule(new RuleInterface() {
      @Override
      public int hashCode() {
        return super.hashCode();
      }
    });
    assertEquals("[[31mERROR[0m] Unknown rule type" + System.lineSeparator(), outContent.toString());
    Logger.setDoLogTime(true);
  }

  @Test
  public void testGameGetAllowedMoves() {
    Game test = new Game();
    Square square = new Square(1, 1, 1);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, spawn, square);
    test.addPiece(piece);
    test.addRule(new FarMovement()); // There shouldn't be any legal far movement, because we have only one piece
    assertEquals(test.getPieces().size(), 1);
    assertTrue(test.getAllowedMoves(piece).isEmpty());
  }

  @Test
  public void testGameGetWinner() {
    Game test = new Game();
    Square square = new Square(1, 1, 1);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, spawn, square);
    test.addPiece(piece);
    AllPiecesInTargetWinRule win = mock(AllPiecesInTargetWinRule.class);
    test.addRule(win);
    assertNull(test.getWinner());
    when(win.getWinner(test)).thenReturn(spawn);
    verify(win).getWinner(any());
    assertNotNull(test.getWinner());
  }

  @Test
  public void testGameMove() {
    Game test = new Game();
    Square square = new Square(1, 1, 1);
    Square dest = new Square(2, 2, 2);
    square.addConnection(dest, null);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, spawn, square);
    test.addPiece(piece);
    test.addRule(new FarMovement()); // There shouldn't be any legal far movement, because we have only one piece
    assertEquals(test.getPieces().size(), 1);
    assertTrue(test.getAllowedMoves(piece).isEmpty());
    assertFalse(test.move(piece, dest));
    test.addRule(new NearMovement()); // Now there's one legal move
    assertTrue(test.move(piece, dest));
  }

  @Test
  public void testGameGettersFail() {
    Game test = new Game();
    assertNull(test.getPieceById(0));
    assertNull(test.getPlayerById(0));
    assertNull(test.getSquareById(0));
    assertTrue(test.getRules().isEmpty());
  }
}
