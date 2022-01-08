package com.laggytrylma.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.common.SquareDeserializer;
import com.laggytrylma.utils.communication.common.SquareSerializer;

import java.util.LinkedList;
import java.util.List;

//@JsonSerialize(using = SquareSerializer.class)
//@JsonDeserialize(using = SquareDeserializer.class)
public class Square {
    public Square(int id, float x, float y){
        connections = new LinkedList<>();
        this.x = x;
        this.y = y;
        this.id = id;
    }
    private List<Connection> connections;
    private Player spawn, target;
    private Piece piece;
    private int id;

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

    public Player getSpawn(){
        return spawn;
    }
    public Player getTarget() { return target; }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public List<Connection> getConnections(){
        return connections;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean occpuied() {
        return piece != null;
    }

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch(JsonProcessingException e) {
            Logger.error(e.getMessage());
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
