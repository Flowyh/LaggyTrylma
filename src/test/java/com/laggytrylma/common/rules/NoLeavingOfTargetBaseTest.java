package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class NoLeavingOfTargetBaseTest {
  @Test
  public void testNoLeavingOfTargetBaseCantLeave() {
    Game game = new Game();
    NoLevingOfTargetBase test = new NoLevingOfTargetBase();
    NearMovement nearMovement = new NearMovement();
    Square square = new Square(1, 1, 1);
    Square dest = new Square(2,2,2);
    Player one = new Player(1, "1", new Color(0, 0, 0));
    Player two = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, one, square);
    square.addConnection(dest, null);
    game.addPlayer(one);
    game.addPlayer(two);
    game.addPiece(piece);
    game.addSquare(square);
    game.addRule(test);
    game.addRule(nearMovement);
    dest.setSpawnAndTarget(two, one); // One can't move out of dest if already in it
    assertTrue(game.move(piece, dest));
    assertFalse(game.move(piece, square));
  }
}
