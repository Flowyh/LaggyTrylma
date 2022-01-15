package com.laggytrylma.common.models;

import java.awt.*;

/**
 * Trylma Piece model.
 */
public class Piece {
    /**
     * Piece class constructor.
     * @param id Piece id
     * @param owner Piece owner (Player object)
     * @param square Piece Square object
     */
    public Piece(int id, Player owner, Square square){
        this.owner = owner;
        this.square = square;
        this.id = id;
    }

    /**
     * Piece's id.
     */
    private int id;
    /**
     * Piece owner (Player object).
     */
    private Player owner;
    /**
     * Piece Square object.
     */
    private Square square;

    /**
     * Get owner's color.
     * @return Color of the owner
     */
    public Color getColor(){
        return owner.getColor();
    }

    /**
     * Get Piece's Square object.
     * @return Square object
     */
    public Square getSquare(){
        return square;
    }

    /**
     * Set Piece's Square object.
     * @param newSquare new Square object
     */
    public void setSquare(Square newSquare){
        this.square = newSquare;
    }

    /**
     * Get Piece's id.
     * @return int piece's id
     */
    public int getId() {
        return id;
    }

    /**
     * Get Piece owner's Player object.
     * @return Player object of the owner
     */
    public Player getOwner() {
        return owner;
    }
}
