package com.laggytrylma.common;

public class BaseGameBuilderDirector extends GameBuilderDirector {
    public BaseGameBuilderDirector(AbstractGameBuilder builder){
        super(builder);
    }
    protected Player[] players;

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    @Override
    public Game build(){
        builder.instantiateBoard();
        builder.makeConnections();
        builder.connectPlayers(this.players);
        builder.setSquareOwnership();
        builder.createPieces();
        builder.addRules();
        return builder.getResult();
    }
}
