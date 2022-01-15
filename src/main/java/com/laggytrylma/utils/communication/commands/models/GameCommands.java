package com.laggytrylma.utils.communication.commands.models;

/**
 * Enum holding all Game model possible commands.
 */
public enum GameCommands implements IModelCommands {
  /**
   * Start game.
   */
  START("start"),
  /**
   * Move piece.
   */
  MOVE("move"), // player moves
  /**
   * Next player info.
   */
  NEXT("next"),
  /**
   * Winner info.
   */
  WIN("win"), // player win
  /**
   * Current game's info.
   */
  GAME_INFO("game_info"), // players' info, game info
  /**
   * Current player's info.
   */
  PLAYER_INFO("player_info");

  /**
   * Command name.
   */
  private final String name;

  /**
   * Enum constructor.
   * @param str String command name.
   */
  GameCommands(String str) { name = str; }

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
    return "game";
  }

  /**
   * Get given command enum type from String
   * @param text String enum type
   * @return GameCommands enum type if String was OK. Null if not.
   */
  public static GameCommands fromString(String text) {
    for (GameCommands g : GameCommands.values()) {
      if (g.name.equalsIgnoreCase(text)) {
        return g;
      }
    }
    return null;
  }
}
