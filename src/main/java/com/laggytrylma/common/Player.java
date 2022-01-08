package com.laggytrylma.common;

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

}
