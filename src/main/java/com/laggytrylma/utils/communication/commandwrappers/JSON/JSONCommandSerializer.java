package com.laggytrylma.utils.communication.commandwrappers.JSON;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.io.IOException;
import java.util.Map;

/**
 * Custom BaseCommandWrapper.class JSON serializer.
 */
public class JSONCommandSerializer extends StdSerializer<BaseCommandWrapper<?>> {
  /**
   * Empty constructor (Jackson requires this).
   */
  protected JSONCommandSerializer() { this(null); }
  /**
   * Class constructor, calls StdSerializer constructor.
   * @param t BaseCommandWrapper
   */
  protected JSONCommandSerializer(Class<BaseCommandWrapper<?>> t) {
    super(t);
  }

  /**
   * Serialize BaseCommandWrapper object into JSON String using Jackson custom serializer.
   * @param commandWrapper BaseCommandWrapper object
   * @param jsonGenerator JsonGenerator
   * @param serializerProvider SerializerProvider
   * @throws IOException something bad happened.
   */
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
