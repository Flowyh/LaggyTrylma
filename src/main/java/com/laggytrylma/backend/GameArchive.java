package com.laggytrylma.backend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("games")
@EnableMongoAuditing
public class GameArchive {
    @Id
    ObjectId id;
    BasicDBObject savedGame;

    @Indexed
    @CreatedDate
    Date creationDate = new Date();
    @LastModifiedDate
    Date lastModifiedDate;

    public GameArchive(){

    }

    public GameArchive(Game game) {
        String serializedGame = ObjectJSONSerializer.serialize(game);
        if(serializedGame == null) savedGame = null;
        else savedGame = BasicDBObject.parse(serializedGame);
    }

    public void setGame(Game game) {
        String serializedGame = ObjectJSONSerializer.serialize(game);
        if(serializedGame == null) savedGame = null;
        else savedGame = BasicDBObject.parse(serializedGame);
    }

    public Game getGame(){
        if(savedGame == null) return null;
        return (Game) ObjectJSONSerializer.deserialize(savedGame.toJson(), Game.class);
    }
}
