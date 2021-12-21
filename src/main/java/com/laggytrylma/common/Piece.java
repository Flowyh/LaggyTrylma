package com.laggytrylma.common;

import java.awt.*;

public class Piece {
    public Piece(Player owner, Square square){
        this.owner = owner;
        this.square = square;
    }
    private Player owner;
    private Square square;

    public Color getColor(){
        return owner.color;
    }

    public Square getSquare(){
        return square;
    }

    public void setSquare(Square newSquare){
        this.square = newSquare;
    }
}
