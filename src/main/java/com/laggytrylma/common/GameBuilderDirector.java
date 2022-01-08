package com.laggytrylma.common;

public class GameBuilderDirector {
    AbstractGameBuilder builder;
    Player[] players = null;
    public GameBuilderDirector(AbstractGameBuilder builder){
        this.builder = builder;
    }
    public void setPlayers(Player[] players) { this.players = players; }

    public Game build(){
        builder.instantiateBoard();
        builder.makeConnections();
        builder.connectPlayers(players);
        builder.setSquareOwnership();
        builder.createPieces();
        builder.addRules();
        return builder.getResult();
    }
}
