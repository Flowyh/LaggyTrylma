package com.laggytrylma.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.Logger;

import java.awt.*;

public class Player {
    public Player(String name, Color color){
        this.name = name;
        this.color = color;
    }
    public String name;
    public Color color;

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
