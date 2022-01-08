package com.laggytrylma.common;

import java.awt.*;

public class Piece {
    public Piece(int id, Player owner, Square square){
        this.owner = owner;
        this.square = square;
        this.id = id;
    }
    private int id;
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

    public int getId() {
        return id;
    }
}
