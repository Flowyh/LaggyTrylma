package com.laggytrylma.utils.communication.commands.models;

public enum Client implements IModelCommands {
  CREATE("create"),
  UPDATE("update"),
  INFO("info"),
  DELETE("delete");

  private final String name;

  Client(String str) { name = str; }

  @Override
  public String command() {
    return name;
  }

  @Override
  public String model() {
    return "client";
  }

  public static Client fromString(String text) {
    for (Client c : Client.values()) {
      if (c.name.equalsIgnoreCase(text)) {
        return c;
      }
    }
    return null;
  }
}
