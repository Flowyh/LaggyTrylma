package com.laggytrylma.utils.communication.commands.models;

/**
 * Enum holding all Client model possible commands.
 */
public enum ClientCommands implements IModelCommands {
  /**
   * Change client nickname.
   */
  NICKNAME("nickname");

  /**
   * Command name.
   */
  private final String name;

  /**
   * Enum constructor.
   * @param str String command name.
   */
  ClientCommands(String str) { name = str; }

  /**
   * Get current enum's command as a String.
   * @return String command
   */
  @Override
  public String command() {
    return name;
  }

  /**
   * Get current enum's model as a String.
   * @return String model
   */
  @Override
  public String model() {
    return "client";
  }

  /**
   * Get given command enum type from String
   * @param text String enum type
   * @return ClientCommands enum type if String was OK. Null if not.
   */
  public static ClientCommands fromString(String text) {
    for (ClientCommands c : ClientCommands.values()) {
      if (c.name.equalsIgnoreCase(text)) {
        return c;
      }
    }
    return null;
  }
}
