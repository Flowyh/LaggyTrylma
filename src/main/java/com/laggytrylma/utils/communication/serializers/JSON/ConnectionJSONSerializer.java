package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.models.Connection;

import java.io.IOException;

/**
 * Custom Connection.class JSON serializer.
 */
public class ConnectionJSONSerializer extends StdSerializer<Connection> {
  /**
   * Empty constructor (Jackson requires this).
   */
    protected ConnectionJSONSerializer() { this(null); }

  /**
   * Class constructor, calls StdSerializer constructor.
   * @param t Connection
   */
  protected ConnectionJSONSerializer(Class<Connection> t) {
      super(t);
    }

  /**
   * Serialize Connection object into JSON String using Jackson custom serializer.
   * @param connection Connection object
   * @param jsonGenerator JsonGenerator
   * @param serializerProvider SerializerProvider
   * @throws IOException something bad happened.
   */
    @Override
    public void serialize(Connection connection, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      if(connection.near != null)
        jsonGenerator.writeNumberField("near", connection.near.getId());
      if(connection.far != null)
        jsonGenerator.writeNumberField("far", connection.far.getId());
      jsonGenerator.writeEndObject();
    }
}
