package com.laggytrylma.utils.communication.commandwrappers;

import com.laggytrylma.utils.communication.commands.models.IModelCommands;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for wrapping command info sent over the sockets.
 * @param <T> Command enum type of given model (IModelCommands).
 */
public class BaseCommandWrapper<T extends IModelCommands> {
  /**
   * Given IModelCommands type.
   */
  private T command;
  /**
   * Command arguments.
   */
  private Map<String, String> args;

  /**
   * Class constructor.
   * @param cmd given IModelCommands type
   * @param args command arguments
   */
  public BaseCommandWrapper(T cmd, Map<String, String> args) {
    this.command = cmd;
    this.args = new HashMap<>(args);
  }

  /**
   * IMPORTANT: OVERRIDE THIS
   * @param serialized String serialized
   */
  public BaseCommandWrapper(String serialized) { }

  /**
   * Get wrapper's command type.
   * @return T IModelCommands type
   */
  public T getCommand() {
    return this.command;
  }

  /**
   * Get wrapper's arguments.
   * @return command arguments
   */
  public Map<String, String> getArgs() {
    return args;
  }

  /**
   * Set wrapper's command type.
   * @param cmd IModelCommands type
   */
  public void setCommand(T cmd) { this.command = cmd; }

  /**
   * Set wrapper's arguments.
   * @param args command arguments
   */
  public void setArgs(Map<String, String> args) { this.args = args; }

  /**
   * Get wrapper's info in String.
   * @return  wrapper's info as String.
   */
  public String toString() {
    return "Model: " + command.model() + " Command: " + command.command() + " " + args.toString();
  }


  /**
   * IMPORTANT: OVERRIDE THIS
   * Serialize wrapper into String
   * @return serialized String
   */
  public String serialize() { return null; }
}
