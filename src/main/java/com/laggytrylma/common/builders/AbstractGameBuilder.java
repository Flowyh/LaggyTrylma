package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

/**
 * GoF's builder pattern for creating a new Game object.
 * Sets up all the game logic, and also acts as a id picker for given Game objects.
 */
public abstract class AbstractGameBuilder {
    abstract void makeInstance();
    abstract void instantiateBoard();
    abstract void makeConnections();
    abstract void connectPlayers(Player[] players);
    abstract void setSquareOwnership();
    abstract void addRules();
    abstract void createPieces();
    abstract Game getResult();

    /**
     * Current game object id.
     */
    private int free_id = 0;

    /**
     * Gets new game object id (increments it).
     * @return int new game object id
     */
    protected int getNewId(){
        return free_id++;
    }
}
