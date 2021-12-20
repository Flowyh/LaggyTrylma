package com.laggytrylma.common;

public class GameBuilderDirector {
    AbstractGameBuilder builder;
    public GameBuilderDirector(AbstractGameBuilder builder){
        this.builder = builder;
    }

    public Game build(){
        builder.instantiateBoard();
        builder.connectPlayers();
        builder.makeConnections();
        builder.addRules();
        return builder.getResult();
    }
}
