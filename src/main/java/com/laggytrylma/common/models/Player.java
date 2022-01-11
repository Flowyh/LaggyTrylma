package com.laggytrylma.common.models;

import java.awt.*;

public class Player {
    public Player(int id, String name, Color color){
        this.name = name;
        this.color = color;
        this.id = id;
    }
    public Player() {}
    private int id;
    private String name;
    private Color color;

    public String toString() {
        return "Player: " + this.name + " Color: " + this.color.toString() + " Id: " + this.id;
    }

    public int getId() {
        return id;
    }
    public Color getColor(){return color;}
    public String getName(){return name;}

    public void setColor(Color color) { this.color = color; }
    public void setName(String name) { this.name = name; }

}
