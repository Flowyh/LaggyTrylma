package com.laggytrylma.common.models;

import java.awt.*;

/**
 * Trylma Player model.
 */
public class Player {
    /**
     * Player class constructor.
     * @param id Player id
     * @param name Player name
     * @param color Player color
     */
    public Player(int id, String name, Color color){
        this.name = name;
        this.color = color;
        this.id = id;
    }

    /**
     * Empty constructor, idk why it has to be defined but ok Java.
     */
    public Player() {}

    /**
     * Player's id.
     */
    private int id;
    /**
     * Player's name.
     */
    private String name;
    /**
     * Player's Color.
     */
    private Color color;

    /**
     * Wrap Player's info into a String.
     * @return Player's info String.
     */
    public String toString() {
        return "Player: " + this.name + " Color: " + this.color.toString() + " Id: " + this.id;
    }

    /**
     * Get Player's id.
     * @return int Player's id
     */
    public int getId() {
        return id;
    }

    /**
     * Get Player's Color object.
     * @return Color object of this Player
     */
    public Color getColor(){return color;}

    /**
     * Get Player's name String
     * @return name of this Player
     */
    public String getName(){return name;}

    /**
     * Set Player's Color object.
     * @param color new Color object
     */
    public void setColor(Color color) { this.color = color; }

    /**
     * Set Player's name.
     * @param name new Player's name
     */
    public void setName(String name) { this.name = name; }

}
