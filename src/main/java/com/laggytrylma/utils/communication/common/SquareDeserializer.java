package com.laggytrylma.utils.communication.common;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.common.Square;

public class SquareDeserializer extends StdDeserializer<Square> {

  protected SquareDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Square deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
    return null;
  }
}
