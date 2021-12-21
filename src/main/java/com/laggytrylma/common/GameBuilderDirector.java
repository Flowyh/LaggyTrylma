package com.laggytrylma.common;

public class GameBuilderDirector {
    AbstractGameBuilder builder;
    public GameBuilderDirector(AbstractGameBuilder builder){
        this.builder = builder;
    }

    public Game build(){
        builder.instantiateBoard();
        builder.makeConnections();
        builder.connectPlayers(null);
        builder.setSquareOwnership();
        builder.createPieces();
        builder.addRules();
        return builder.getResult();
    }
}
