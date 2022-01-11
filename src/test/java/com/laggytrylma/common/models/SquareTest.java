package com.laggytrylma.common.models;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class SquareTest {
  @Test
  public void testSquareGetters() {
    Square test = new Square(1, 1, 1);
    assertNotNull(test);
    assertEquals(1, test.getX(), 0.0);
    assertEquals(1, test.getY(), 0.0);
    assertTrue(test.getConnections().isEmpty());
  }

  @Test
  public void testSetters() {
    Square test = new Square(1, 1, 1);
    Player spawn = new Player(1, "1", new Color(0, 0, 0));
    Player target = new Player(2, "2", new Color(1, 1, 1));
    Piece piece = new Piece(1, spawn, test);
    test.setPiece(piece);
    test.setSpawnAndTarget(spawn, target);
    assertEquals(test.getTarget().toString(), "Player: 2 Color: java.awt.Color[r=1,g=1,b=1] Id: 2");
    assertEquals(test.getPiece().getId(), 1);
    assertTrue(test.occupied());
  }
}
