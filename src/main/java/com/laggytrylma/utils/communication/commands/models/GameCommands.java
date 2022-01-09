package com.laggytrylma.utils.communication.commands.models;

public enum GameCommands implements IModelCommands {
  SETUP("setup"), // rules, nicknames, colors, player limit, game name etc
  START("start"),
  MOVE("move"), // player moves
  NEXT("next"),
  WIN("win"), // player win
  GAME_INFO("game_info"), // players' info, game info
  PLAYER_INFO("player_info"),
  LEAVE("leave"), // player left the game
  DELETE("delete"); // game has to be deleted

  private final String name;

  GameCommands(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "game";
  }

  public static GameCommands fromString(String text) {
    for (GameCommands g : GameCommands.values()) {
      if (g.name.equalsIgnoreCase(text)) {
        return g;
      }
    }
    return null;
  }
}
