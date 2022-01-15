package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

/**
 * GoF's builder director pattern.
 * Wraps all builder method calls into one method.
 * Also keeps some info about the current game players.
 */
public class GameBuilderDirector {
    /**
     * Current game builder.
     */
    AbstractGameBuilder builder;
    /**
     * Current game's array of player objects.
     */
    Player[] players = null;

    /**
     * Class constructor.
     * @param builder game builder
     */
    public GameBuilderDirector(AbstractGameBuilder builder){
        this.builder = builder;
    }

    /**
     * Set director's array of player objects.
     * @param players new Array of player objects
     */
    public void setPlayers(Player[] players) { this.players = players; }

    /**
     * Build a new Game instance using given builder.
     * @return new Game instance
     */
    public Game build(){
        builder.makeInstance();
        builder.instantiateBoard();
        builder.makeConnections();
        builder.connectPlayers(players);
        builder.setSquareOwnership();
        builder.createPieces();
        builder.addRules();
        return builder.getResult();
    }
}
