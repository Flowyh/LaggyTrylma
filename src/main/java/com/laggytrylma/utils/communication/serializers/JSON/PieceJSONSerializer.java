package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.MovementRulesInterface;
import com.laggytrylma.common.Piece;

import java.io.IOException;

public class PieceJSONSerializer extends StdSerializer<Piece> {
    protected PieceJSONSerializer() { this(null); }
    protected PieceJSONSerializer(Class<Piece> t) {
        super(t);
    }

    @Override
    public void serialize(Piece piece, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("id", piece.getId());
      jsonGenerator.writeNumberField("square", piece.getSquare().getId());
      jsonGenerator.writeNumberField("owner", piece.getOwner().getId());
      jsonGenerator.writeEndObject();
    }
}

