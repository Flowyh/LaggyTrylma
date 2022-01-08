package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laggytrylma.backend.servers.dummy.DummyServer;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.serializers.AbstractObjectSerializer;

import java.awt.*;

public class ObjectJSONSerializer extends AbstractObjectSerializer {
  private static ObjectMapper objectMapper = new ObjectMapper();

  // SINGLETON
  private static ObjectJSONSerializer instance = new ObjectJSONSerializer();
  // DOUBLE-CHECKED LOCKING
  public static ObjectJSONSerializer getInstance() {
    ObjectJSONSerializer localRef = instance;
    if (localRef == null) {
      synchronized (DummyServer.class) {
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
    module.addDeserializer(Color.class, new ColorJSONDeserializer());
    objectMapper.registerModule(module);
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
}
