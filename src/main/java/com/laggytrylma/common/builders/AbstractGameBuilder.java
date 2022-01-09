package com.laggytrylma.common.builders;

import com.laggytrylma.common.Game;
import com.laggytrylma.common.models.Player;

public abstract class AbstractGameBuilder {
    abstract void instantiateBoard();
    abstract void makeConnections();
    abstract void connectPlayers(Player[] players);
    abstract void setSquareOwnership();
    abstract void addRules();
    abstract void createPieces();
    abstract Game getResult();

    private int free_id = 0;
    protected int getNewId(){
        return free_id++;
    }
}
