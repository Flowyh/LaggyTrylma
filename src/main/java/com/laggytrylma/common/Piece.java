package com.laggytrylma.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.Logger;

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

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch(JsonProcessingException e) {
            Logger.error(e.getMessage());
        }
        return null;
    }
}
