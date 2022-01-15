package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.models.Square;

import java.io.IOException;

/**
 * Custom Square.class JSON serializer.
 */
public class SquareJSONSerializer extends StdSerializer<Square> {
  /**
   * Empty constructor (Jackson requires this).
   */
    protected SquareJSONSerializer() { this(null); }
  /**
   * Class constructor, calls StdSerializer constructor.
   * @param t Square
   */
    protected SquareJSONSerializer(Class<Square> t) {
      super(t);
    }

  /**
   * Serialize Square object into JSON String using Jackson custom serializer.
   * @param square Square
   * @param jsonGenerator JsonGenerator
   * @param serializerProvider SerializerProvider
   * @throws IOException something bad happened.
   */
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
