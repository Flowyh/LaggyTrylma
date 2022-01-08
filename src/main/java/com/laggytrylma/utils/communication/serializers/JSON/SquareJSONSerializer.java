package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.Square;

import java.io.IOException;

public class SquareJSONSerializer extends StdSerializer<Square> {

    protected SquareJSONSerializer() { this(null); }
    protected SquareJSONSerializer(Class<Square> t) {
      super(t);
    }

    @Override
    public void serialize(Square square, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("id", square.getId());
      jsonGenerator.writeNumberField("x", square.getX());
      jsonGenerator.writeNumberField("y", square.getY());
      if(square.getPiece() != null)
        jsonGenerator.writeNumberField("piece", square.getPiece().getId());
      if(square.getSpawn() != null)
        jsonGenerator.writeNumberField("spawn", square.getSpawn().getId());
      if(square.getTarget() != null)
        jsonGenerator.writeNumberField("target", square.getTarget().getId());
      jsonGenerator.writeObjectField("connections", square.getConnections());
      jsonGenerator.writeEndObject();
    }
}
