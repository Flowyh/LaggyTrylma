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

public class JSONCommandDeserializer extends StdDeserializer<JSONCommandWrapper<?>>  {

  public JSONCommandDeserializer() { this(null); }
  public JSONCommandDeserializer(Class<BaseCommandWrapper> t) {
    super(t);
  }

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
