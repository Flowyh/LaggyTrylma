package com.laggytrylma.utils.communication.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.common.Square;

import java.io.IOException;
import java.util.Map;

public class SquareSerializer extends StdSerializer<Square> {

    protected SquareSerializer() { this(null); }
    protected SquareSerializer(Class<Square> t) {
      super(t);
    }

    @Override
    public void serialize(Square commandWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
//      jsonGenerator.writeStartObject();
//      jsonGenerator.writeStringField("x", Float.toString(commandWrapper.getX()));
//      jsonGenerator.writeStringField("y", Float.toString(commandWrapper.getY()));
//      jsonGenerator.writeStringField("spawn", commandWrapper.getSpawn().toJSON());
//      jsonGenerator.writeObjectFieldStart("target", commandWrapper.getTarget().toJSON());
//      Map<String, String> args = commandWrapper.getArgs();
//      for(String key : args.keySet()) {
//        jsonGenerator.writeStringField(key, args.get(key));
//      }
//      jsonGenerator.writeEndObject();
//      jsonGenerator.writeEndObject();
//    }
  }
}
