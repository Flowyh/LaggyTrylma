package com.laggytrylma.utils.communication.commands.models;

public enum Lobby implements IModelCommands {
  CREATE("create"),
  UPDATE("update"),
  INFO("info"),
  DELETE("delete"),
  JOIN("join");

  private final String name;

  Lobby(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "lobby";
  }

  public static Lobby fromString(String text) {
    for (Lobby l : Lobby.values()) {
      if (l.name.equalsIgnoreCase(text)) {
        return l;
      }
    }
    return null;
  }
}
