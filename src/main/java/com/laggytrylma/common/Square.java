package com.laggytrylma.common;

import java.util.LinkedList;
import java.util.List;

public class Square {
    public Square(float x, float y){
        connections = new LinkedList<>();
        this.x = x;
        this.y = y;

    }
    private List<Connection> connections;
    private Player spawn, target;
    private Piece piece;

    // display position
    private float x, y;

    public void addConnection(Square near, Square far) {
    }
}
