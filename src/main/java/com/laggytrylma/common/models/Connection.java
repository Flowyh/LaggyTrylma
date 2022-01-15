package com.laggytrylma.common.models;

/**
 * Class which holds Squares neighbours info.
 * Each square has one "far" and one "near" neighbour.
 * This class is used to retrieve them later in other parts of this common package.
 */
public class Connection {
    /**
     * Class constructor.
     * @param near Square's near neighbour.
     * @param far Square's far neighbour.
     */
    public Connection(Square near, Square far){
        this.near = near;
        this.far = far;
    }
    /**
     * Near and far Neighbour of given Square.
     */
    public Square near, far;
}
