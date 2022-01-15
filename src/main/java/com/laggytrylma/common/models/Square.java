package com.laggytrylma.common.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Trylma Square model.
 */
public class Square {
    /**
     * Square class constructor.
     * @param id Square id
     * @param x Square x UI display position
     * @param y Square y UI display position
     */
    public Square(int id, float x, float y){
        connections = new LinkedList<>();
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Square's list of connections.
     */
    private List<Connection> connections;
    /**
     * Square's spawn and target Player objects.
     */
    private Player spawn, target;
    /**
     * Square's piece (if there's any there).
     */
    private Piece piece;
    /**
     * Square's id.
     */
    private int id;


    /**
     * Square's x/y UI display position.
     */
    private float x, y;

    /**
     * Get Square's x UI display position.
     * @return float x
     */
    public float getY(){
        return y;
    }

    /**
     * Get Square's y UI display position.
     * @return float y
     */
    public float getX(){
        return x;
    }

    /**
     * Add new connection to the Square.
     * @param near "near" neighbouring Square object (if any)
     * @param far "far" neighbouring Square object (if any)
     */
    public void addConnection(Square near, Square far) {
        connections.add(new Connection(near, far));
    }

    /**
     * Set Square's spawn/target Player objects.
     * @param spawn spawn's Player object
     * @param target target's Player object
     */
    public void setSpawnAndTarget(Player spawn, Player target){
        this.spawn = spawn;
        this.target = target;
    }

    /**
     * Get Square's spawn Player object.
     * @return Player object
     */
    public Player getSpawn(){
        return spawn;
    }
    /**
     * Get Square's target Player object.
     * @return Player object
     */
    public Player getTarget() { return target; }

    /**
     * Set Square's Piece object.
     * @param piece new Piece object.
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Get Square's list of Connections.
     * @return List of Connection objects.
     */
    public List<Connection> getConnections(){
        return connections;
    }

    /**
     * Get Square's Piece object.
     * @return Piece object
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Check whether Piece object is present in given Square.
     * @return True if there's an instantiated (not null) Piece in this Square, False if otherwise.
     */
    public boolean occupied() {
        return piece != null;
    }

    /**
     * Get Square's id.
     * @return int Square's id
     */
    public int getId() {
        return id;
    }
}
