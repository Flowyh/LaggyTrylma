package com.laggytrylma.backend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.Properties;

public class GameArchive {
    String serializedGame;

    public GameArchive(){

    }

    public GameArchive(Game game){
        serializedGame = ObjectJSONSerializer.serialize(game);
    }

    public Game getGame(){
        return (Game)ObjectJSONSerializer.deserialize(serializedGame, Game.class);
    }
}
