package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.rules.MovementRulesInterface;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.serializers.AbstractObjectSerializer;

import java.awt.*;

public class ObjectJSONSerializer extends AbstractObjectSerializer {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // SINGLETON
  private static ObjectJSONSerializer instance = new ObjectJSONSerializer();
  // DOUBLE-CHECKED LOCKING
  public static ObjectJSONSerializer getInstance() {
    ObjectJSONSerializer localRef = instance;
    if (localRef == null) {
      synchronized (ObjectJSONSerializer.class) {
        localRef = instance;
        if (localRef == null) {
          instance = localRef = new ObjectJSONSerializer();
        }
      }
    }
    return localRef;
  }

  private ObjectJSONSerializer() {
    setup();
  }

  @Override
  protected void setup() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Color.class, new ColorJSONSerializer());
    module.addSerializer(Square.class, new SquareJSONSerializer());
    module.addSerializer(Connection.class, new ConnectionJSONSerializer());
    module.addSerializer(MovementRulesInterface.class, new RulesJSONSerializer());
    module.addSerializer(Piece.class, new PieceJSONSerializer());

    module.addDeserializer(Game.class, new GameJSONDeserializer());
    module.addDeserializer(Color.class, new ColorJSONDeserializer());
    module.addDeserializer(MovementRulesInterface.class, new RulesJSONDeserializer());
    objectMapper.registerModule(module);

    objectMapper.readerFor(Color.class);
  }

  public static String serialize(Object o) {
    try {
        return objectMapper.writeValueAsString(o);
    } catch(JsonProcessingException e) {
        Logger.error(e.getMessage());
    }
    return null;
  }

  public static Object deserialize(String o, Class<?> cl) {
    try {
      return objectMapper.readValue(o, cl);
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
    }
    return null;
  }

  public static Object deserialize(JsonNode node, Class<?> cl){
    try {
      return objectMapper.treeToValue(node, cl);
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
    }
    return null;
  }

}
