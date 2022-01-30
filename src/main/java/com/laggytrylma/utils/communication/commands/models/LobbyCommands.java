package com.laggytrylma.utils.communication.commands.models;

/**
 * Enum holding all Lobby model possible commands.
 */
public enum LobbyCommands implements IModelCommands {

  LIST_ARCHIVE("list_archive"),

  GET_ARCHIVED_GAME("get_archived_game"),

  /**
   * Create new model.
   */
  CREATE("create"),
  /**
   * Update lobby rules.
   */
  RULES("rules"),
  /**
   * Lobby setup.
   */
  SETUP("setup"), // nicknames, colors, player limit, game name etc
  /**
   * Lobby info.
   */
  INFO("info"),
  /**
   * Delete lobby
   */
  DELETE("delete"),
  /**
   * Client joined lobby.
   */
  JOIN("join"),
  /**
   * Client left lobby.
   */
  LEAVE("leave"),
  /**
   * Set lobby's player limit.
   */
  PLAYER_LIMIT("player_limit"),
  /**
   * List all available lobbies.
   */
  LIST_ALL("list_all");



  /**
   * Command name.
   */
  private final String name;

  /**
   * Enum constructor.
   * @param str String command name.
   */
  LobbyCommands(String str) { name = str; }

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
    return "lobby";
  }

  /**
   * Get given command enum type from String
   * @param text String enum type
   * @return LobbyCommands enum type if String was OK. Null if not.
   */
  public static LobbyCommands fromString(String text) {
    for (LobbyCommands l : LobbyCommands.values()) {
      if (l.name.equalsIgnoreCase(text)) {
        return l;
      }
    }
    return null;
  }
}
