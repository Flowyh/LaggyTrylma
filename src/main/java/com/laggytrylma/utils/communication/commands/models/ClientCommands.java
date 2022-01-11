package com.laggytrylma.utils.communication.commands.models;

public enum ClientCommands implements IModelCommands {
  NICKNAME("nickname");

  private final String name;

  ClientCommands(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "client";
  }

  public static ClientCommands fromString(String text) {
    for (ClientCommands c : ClientCommands.values()) {
      if (c.name.equalsIgnoreCase(text)) {
        return c;
      }
    }
    return null;
  }
}
