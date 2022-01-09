package com.laggytrylma.utils.communication.commandwrappers.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;

import java.util.Map;

@JsonSerialize(using = JSONCommandSerializer.class)
@JsonDeserialize(using = JSONCommandDeserializer.class)
public class JSONCommandWrapper<T extends IModelCommands> extends BaseCommandWrapper<T> {
  public JSONCommandWrapper(T cmd, Map<String, String> args) {
    super(cmd, args);
  }

  public JSONCommandWrapper(String serialized) {
    super(serialized);
    try {
      ObjectMapper mapper = new ObjectMapper();
      JSONCommandWrapper<T> deserialized = mapper.readValue(serialized, new TypeReference<>() {});
      this.setCommand(deserialized.getCommand());
      this.setArgs(deserialized.getArgs());
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
      this.setCommand(null);
      this.setArgs(null);
    }
  }

  @Override
  public String serialize() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(this);
    } catch(JsonProcessingException e) {
      Logger.error(e.getMessage());
    }
    return null;
  }
}
