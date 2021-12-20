package com.laggytrylma.common;

public interface AbstractGameBuilder {
    void instantiateBoard();
    void makeConnections();
    void connectPlayers(Player[] players);
    void setSquareOwnership();
    void addRules();
    void createPieces();
    Game getResult();
}
