package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AllPiecesInTargetWinRuleTest {
  @Test
  public void testAllPiecesInTargetWinRuleTestWin() {
    Game game = new Game();
    AllPiecesInTargetWinRule test = new AllPiecesInTargetWinRule();
    Square square = new Square(1, 1, 1);
    Player winner = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, winner, square);
    game.addPlayer(winner);
    game.addPiece(piece);
    game.addSquare(square);
    square.setSpawnAndTarget(null, winner); // Win on spawn
    assertNotNull(test.getWinner(game));
  }

  @Test
  public void testAllPiecesInTargetWinRuleTestNoWinners() {
    Game game = new Game();
    AllPiecesInTargetWinRule test = new AllPiecesInTargetWinRule();
    Square square = new Square(1, 1, 1);
    Player notAWinner = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, notAWinner, square);
    game.addPlayer(notAWinner);
    game.addPiece(piece);
    game.addSquare(square);
    square.setSpawnAndTarget(notAWinner, null); // Win on spawn
    assertNull(test.getWinner(game));
  }
}
