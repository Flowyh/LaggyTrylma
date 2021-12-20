package com.laggytrylma.common;

public interface AbstractGameBuilder {
    void instantiateBoard();
    void makeConnections();
    void connectPlayers();
    void setSquareOwnership();
    void addRules();
    Game getResult();
}
