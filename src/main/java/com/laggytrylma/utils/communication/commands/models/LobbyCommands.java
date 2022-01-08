package com.laggytrylma.utils.communication.commands.models;

public enum LobbyCommands implements IModelCommands {
  CREATE("create"),
  UPDATE("update"),
  INFO("info"),
  DELETE("delete"),
  JOIN("join");

  private final String name;

  LobbyCommands(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "lobby";
  }

  public static LobbyCommands fromString(String text) {
    for (LobbyCommands l : LobbyCommands.values()) {
      if (l.name.equalsIgnoreCase(text)) {
        return l;
      }
    }
    return null;
  }
}
