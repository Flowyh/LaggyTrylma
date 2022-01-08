package com.laggytrylma.utils.communication.commands.models;

public enum GameCommands implements IModelCommands {
  SETUP("setup"),
  START("start"),
  UPDATE("update"),
  INFO("info"),
  DELETE("delete");

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
