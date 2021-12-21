package com.laggytrylma.utils.communication.commandwrappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;

import java.util.HashMap;
import java.util.Map;

public class BaseCommandWrapper<T extends IModelCommands> {
  private T command;
  private Map<String, String> args;

  public BaseCommandWrapper(T cmd, Map<String, String> args) {
    this.command = cmd;
    this.args = new HashMap<>(args);
  }

  // Override this
  public BaseCommandWrapper(String serialized) { }

  public T getCommand() {
    return this.command;
  }
  public Map<String, String> getArgs() {
    return args;
  }
  public void setCommand(T cmd) { this.command = cmd; }
  public void setArgs(Map<String, String> args) { this.args = args; }

  public String toString() {
    return "Model: " + command.model() + " Command: " + command.command() + " " + args.toString();
  }

  public String serialize() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(this);
  }
}
