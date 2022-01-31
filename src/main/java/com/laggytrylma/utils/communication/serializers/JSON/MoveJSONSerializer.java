package com.laggytrylma.utils.communication.serializers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.models.Move;
import com.laggytrylma.common.models.Piece;

import java.io.IOException;


public class MoveJSONSerializer extends StdSerializer<Move> {

    protected MoveJSONSerializer() { this(null); }

    protected MoveJSONSerializer(Class<Move> t) {
        super(t);
    }


    @Override
    public void serialize(Move move, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("piece", move.piece.getId());
      jsonGenerator.writeNumberField("from", move.from.getId());
      jsonGenerator.writeNumberField("to", move.to.getId());
      jsonGenerator.writeEndObject();
    }
}

