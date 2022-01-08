package com.laggytrylma.common;

public class BaseGame extends Game {
  public Piece findPiece(int x, int y) {
    for(Piece p: pieces) {
      if(p.getSquare().getX() == x && p.getSquare().getY() == y) return p;
    }
    return null;
  }

}
