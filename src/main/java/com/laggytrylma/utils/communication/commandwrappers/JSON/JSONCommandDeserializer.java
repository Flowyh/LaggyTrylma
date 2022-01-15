package com.laggytrylma.utils.communication.commandwrappers.JSON;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.ModelCommandsEnumBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom JSONCommandWrapper.class JSON deserializer.
 */
public class JSONCommandDeserializer extends StdDeserializer<JSONCommandWrapper<?>>  {
  /**
   * Empty constructor (Jackson requires this).
   */
  public JSONCommandDeserializer() { this(null); }
  /**
   * Class constructor, calls StdSerializer constructor.
   * @param t Class<BaseCommandWrapper>
   */
  public JSONCommandDeserializer(Class<BaseCommandWrapper> t) {
    super(t);
  }

  /**
   * Deserialize JSONCommandWrapper JSON String into JSONCommandWrapper object using Jackson custom deserializer.
   * @param jsonParser JsonParser
   * @param deserializationContext DeserializationContext
   * @return deserialized JSONCommandWrapper object
   * @throws IOException something bad happened.
   */
  @Override
  public JSONCommandWrapper<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    // Deserialize command
    String model = node.get("model").asText();
    String command = node.get("command").asText();
    IModelCommands cmd = ModelCommandsEnumBuilder.buildEnum(model, command);
    // Deserialize args
    String args = node.get("args").toString();
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
    Map<String, String> map = mapper.readValue(args, typeRef);
    // Return new object
    return new JSONCommandWrapper<>(cmd, map);
  }
}
