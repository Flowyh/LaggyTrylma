package com.laggytrylma.common;

import java.util.LinkedList;
import java.util.List;

public class Game {
    public Game(){

    }

    List<Piece> pieces = new LinkedList<>();
    List<Square> squares = new LinkedList<>();
    List<Player> players = new LinkedList<>();

    public void addSquare(Square square) {
        squares.add(square);
    }
}
