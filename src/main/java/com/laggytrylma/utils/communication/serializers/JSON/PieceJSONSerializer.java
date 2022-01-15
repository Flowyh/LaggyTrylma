package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.models.Piece;

import java.io.IOException;

/**
 * Custom Piece.class JSON serializer.
 */
public class PieceJSONSerializer extends StdSerializer<Piece> {
  /**
   * Empty constructor (Jackson requires this).
   */
    protected PieceJSONSerializer() { this(null); }
  /**
   * Class constructor, calls StdSerializer constructor.
   * @param t Class<Piece>
   */
    protected PieceJSONSerializer(Class<Piece> t) {
        super(t);
    }

  /**
   * Serialize Piece object into JSON String using Jackson custom serializer.
   * @param piece Piece object
   * @param jsonGenerator JsonGenerator
   * @param serializerProvider SerializerProvider
   * @throws IOException something bad happened.
   */
    @Override
    public void serialize(Piece piece, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("id", piece.getId());
      jsonGenerator.writeNumberField("square", piece.getSquare().getId());
      jsonGenerator.writeNumberField("owner", piece.getOwner().getId());
      jsonGenerator.writeEndObject();
    }
}

