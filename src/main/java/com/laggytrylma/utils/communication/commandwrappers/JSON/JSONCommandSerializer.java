package com.laggytrylma.utils.communication.commandwrappers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.io.IOException;
import java.util.Map;

public class JSONCommandSerializer extends StdSerializer<BaseCommandWrapper<?>> {

  protected JSONCommandSerializer() { this(null); }
  protected JSONCommandSerializer(Class<BaseCommandWrapper<?>> t) {
    super(t);
  }

  @Override
  public void serialize(BaseCommandWrapper<?> commandWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("model", commandWrapper.getCommand().model());
    jsonGenerator.writeStringField("command", commandWrapper.getCommand().command());
    jsonGenerator.writeObjectFieldStart("args");
    Map<String, String> args = commandWrapper.getArgs();
    for(String key : args.keySet()) {
      jsonGenerator.writeStringField(key, args.get(key));
    }
    jsonGenerator.writeEndObject();
    jsonGenerator.writeEndObject();
  }
}
