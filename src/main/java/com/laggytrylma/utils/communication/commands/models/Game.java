package com.laggytrylma.utils.communication.commands.models;

public enum Game implements IModelCommands {
  SETUP("setup"),
  START("start"),
  UPDATE("update"),
  INFO("info"),
  DELETE("delete");

  private final String name;

  Game(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "game";
  }

  public static Game fromString(String text) {
    for (Game g : Game.values()) {
      if (g.name.equalsIgnoreCase(text)) {
        return g;
      }
    }
    return null;
  }
}
