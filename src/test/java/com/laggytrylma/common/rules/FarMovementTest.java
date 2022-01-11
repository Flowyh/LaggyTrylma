package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import org.junit.Test;

import java.awt.*;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class FarMovementTest {
  @Test
  public void testNearMovementHasVisitables() {
    FarMovement test = new FarMovement();
    Square square = new Square(1, 1, 1);
    Square dest = new Square(2, 2, 2);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, spawn, square);
    square.setPiece(piece);
    square.addConnection(square, dest); //square is occupied, dest not
    Set<Square> targets = test.getAllowedMoves(piece);
    assertFalse(targets.isEmpty());
    assertEquals(targets, test.filterBannedMoves(piece, targets));
  }

  @Test
  public void testNearMovementNoVisitables() {
    FarMovement test = new FarMovement();
    Square square = new Square(1, 1, 1);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Piece piece = new Piece(1, spawn, square);
    Set<Square> targets = test.getAllowedMoves(piece);
    assertTrue(targets.isEmpty());
    assertEquals(targets, test.filterBannedMoves(piece, targets));
  }
}

