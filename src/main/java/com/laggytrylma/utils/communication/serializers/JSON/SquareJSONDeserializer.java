package com.laggytrylma.utils.communication.serializers.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.common.Square;

import java.io.IOException;

public class SquareJSONDeserializer extends StdDeserializer<Square> {

  protected SquareJSONDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Square deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode root = jsonParser.readValueAsTree();

return null;
  }
}
