package com.laggytrylma.common.models;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PieceTest {
  @Test
  public void testPiece() {
    Square square = new Square(1, 1, 1);
    Player owner = new Player(1, "1", new Color(0, 0, 0));
    Piece test = new Piece(1, owner, square);
    assertNotNull(test);
    assertEquals(test.getColor().toString(), "java.awt.Color[r=0,g=0,b=0]");
    assertEquals(test.getSquare().getId(), 1);
    assertEquals(test.getOwner().toString(), "Player: 1 Color: java.awt.Color[r=0,g=0,b=0] Id: 1");

    Square newSquare = new Square(2, 2, 2);
    test.setSquare(newSquare);
    assertEquals(test.getSquare().getId(), 2);
  }
}
