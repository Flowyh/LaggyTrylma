package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.models.Connection;

import java.io.IOException;

public class ConnectionJSONSerializer extends StdSerializer<Connection> {

    protected ConnectionJSONSerializer() { this(null); }
    protected ConnectionJSONSerializer(Class<Connection> t) {
      super(t);
    }

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
