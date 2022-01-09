package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.common.*;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.movementrules.MovementRulesInterface;


import java.io.IOException;
import java.util.Iterator;

public class GameJSONDeserializer extends StdDeserializer<Game> {

    public GameJSONDeserializer() { this(null); }
    public GameJSONDeserializer(Class<Game> t) {
        super(t);
    }

    @Override
    public Game deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode root = jsonParser.readValueAsTree();
        JsonNode piecesNode = root.get("pieces");
        JsonNode rulesNode = root.get("rules");
        JsonNode squaresNode = root.get("squares");
        JsonNode playersNode = root.get("players");

        Game game = new Game();

        for (Iterator<JsonNode> it = rulesNode.elements(); it.hasNext(); ) {
            MovementRulesInterface rule = (MovementRulesInterface) ObjectJSONSerializer.deserialize(it.next(), MovementRulesInterface.class);
            game.addRule(rule);
        }

        for (Iterator<JsonNode> it = playersNode.elements(); it.hasNext(); ){
            Player player = (Player) ObjectJSONSerializer.deserialize(it.next(), Player.class);
            game.addPlayer(player);
        }

        for (Iterator<JsonNode> it = squaresNode.elements(); it.hasNext(); ){
            JsonNode squareNode = it.next();
            int id = squareNode.get("id").asInt();
            float x = (float)squareNode.get("x").asDouble();
            float y = (float)squareNode.get("y").asDouble();
            Square square = new Square(id, x, y);


            Player spawn=null, target=null;
            if(squareNode.has("spawn")){
                int spawnId = squareNode.get("spawn").asInt();
                spawn = game.getPlayerById(spawnId);
            }
            if(squareNode.has("target")){
                int targetId = squareNode.get("target").asInt();
                target = game.getPlayerById(targetId);
            }
            square.setSpawnAndTarget(spawn, target);

            game.addSquare(square);
        }

        for(Iterator<JsonNode> it = piecesNode.elements();it.hasNext();){
            JsonNode pieceNode = it.next();
            int id = pieceNode.get("id").asInt();
            int ownerId = pieceNode.get("owner").asInt();
            int squareId = pieceNode.get("square").asInt();

            Square square = game.getSquareById(squareId);
            Player owner = game.getPlayerById(ownerId);

            Piece piece = new Piece(id, owner, square);
            square.setPiece(piece);
            game.addPiece(piece);
        }

        for (Iterator<JsonNode> it = squaresNode.elements(); it.hasNext(); ) {
            JsonNode squareNode = it.next();
            int id = squareNode.get("id").asInt();
            Square square = game.getSquareById(id);

            JsonNode connections = squareNode.get("connections");
            for (Iterator<JsonNode> it2 = connections.elements(); it2.hasNext(); ) {
                JsonNode connectionNode = it2.next();
                Square near=null, far=null;
                if(connectionNode.has("near")){
                    int nearId = connectionNode.get("near").asInt();
                    near = game.getSquareById(nearId);
                }

                if(connectionNode.has("far")){
                    int farId = connectionNode.get("far").asInt();
                    far = game.getSquareById(farId);
                }

                square.addConnection(near, far);
            }

        }

        return game;
    }
}
