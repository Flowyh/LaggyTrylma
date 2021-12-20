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

    public float getY(){
        return y;
    }

    public float getX(){
        return x;
    }

    public void addConnection(Square near, Square far) {
        connections.add(new Connection(near, far));
    }

    public void setSpawnAndTarget(Player spawn, Player target){
        this.spawn = spawn;
        this.target = target;
    }

    Player getSpawn(){
        return spawn;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public List<Connection> getConnections(){
        return connections;
    }
}
