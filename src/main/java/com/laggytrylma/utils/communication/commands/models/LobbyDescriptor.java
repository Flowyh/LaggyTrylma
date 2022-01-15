package com.laggytrylma.utils.communication.commands.models;

/**
 * Class for holding Lobby info neatly.
 */
public class LobbyDescriptor {
    /**
     * Lobby's id.
     */
    private final int id;
    /**
     * Lobby's owner.
     */
    private final String owner;
    /**
     * Lobby's players count.
     */
    private final int players;

    /**
     * Class constructor.
     * @param id lobby's id.
     * @param owner String lobby's owner.
     * @param playersCount lobby's players count.
     */
    public LobbyDescriptor(int id, String owner, int playersCount){
        this.id = id;
        this.owner = owner;
        this.players = playersCount;
    }

    /**
     * Get lobby's owner.
     * @return String owner
     */
    public String getOwner(){
        return owner;
    }

    /**
     * Get lobby's id.
     * @return int lobby's id
     */
    public int getId(){
        return id;
    }

    /**
     * Get lobby's players count.
     * @return int lobby's players count
     */
    public int getPlayersCount(){
        return players;
    }
}
