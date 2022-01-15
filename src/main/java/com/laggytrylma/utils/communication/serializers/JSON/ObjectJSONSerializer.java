package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.rules.RuleInterface;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.serializers.AbstractObjectSerializer;

import java.awt.*;

/**
 * Class for serializing various object into JSONs.
 * Uses GoF's Singleton pattern to keep only one instance of it.
 */
public class ObjectJSONSerializer extends AbstractObjectSerializer {
  /**
   * Jackson's object mapper
   */
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * GoF's singleton instance.
   */
  private static ObjectJSONSerializer instance = new ObjectJSONSerializer();

  /**
   * Get instance of this class using double-checked locking method.
   * @return ObjectJSONSerializer instance.
   */
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

  /**
   * ObjectJSONSerializer constructor.
   */
  private ObjectJSONSerializer() {
    setup();
  }

  /**
   * Setup serializers and deserializers.
   */
  @Override
  protected void setup() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Color.class, new ColorJSONSerializer());
    module.addSerializer(Square.class, new SquareJSONSerializer());
    module.addSerializer(Connection.class, new ConnectionJSONSerializer());
    module.addSerializer(RuleInterface.class, new RulesJSONSerializer());
    module.addSerializer(Piece.class, new PieceJSONSerializer());

    module.addDeserializer(Game.class, new GameJSONDeserializer());
    module.addDeserializer(Color.class, new ColorJSONDeserializer());
    module.addDeserializer(RuleInterface.class, new RulesJSONDeserializer());
    objectMapper.registerModule(module);

    objectMapper.readerFor(Color.class);
  }

  /**
   * Serializer given Object into JSON String.
   * @param o Object to be serialized
   * @return JSON String
   */
  public static String serialize(Object o) {
    try {
        return objectMapper.writeValueAsString(o);
    } catch(JsonProcessingException e) {
        Logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * Deserialize given JSON String into given Class type.
   * @param o JSON String
   * @param cl Class type to be deserialized into
   * @return new Class instance
   */
  public static Object deserialize(String o, Class<?> cl) {
    try {
      return objectMapper.readValue(o, cl);
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * Deserialize given JsonNode into given Class type.
   * @param node JsonNode
   * @param cl Class type to be deserialized into
   * @return new Class instance
   */
  public static Object deserialize(JsonNode node, Class<?> cl){
    try {
      return objectMapper.treeToValue(node, cl);
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
    }
    return null;
  }

}
