package com.laggytrylma.utils.communication.commands.models;

public class LobbyDescriptor {
    private int id;
    private String owner;
    private int players;

    public LobbyDescriptor(int id, String owner, int playersCount){
        this.id = id;
        this.owner = owner;
        this.players = playersCount;
    }

    public String getOwner(){
        return owner;
    }

    public int getId(){
        return id;
    }

    public int getPlayersCount(){
        return players;
    }
}
