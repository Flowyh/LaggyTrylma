package com.laggytrylma.utils.communication.commands.models;

public enum LobbyCommands implements IModelCommands {
  CREATE("create"),
  RULES("rules"),
  SETUP("setup"), // nicknames, colors, player limit, game name etc
  INFO("info"),
  DELETE("delete"),
  JOIN("join"),
  LEAVE("leave"),
  PLAYER_LIMIT("player_limit"),
  LIST_ALL("list_all");

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
