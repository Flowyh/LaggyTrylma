package com.laggytrylma.utils.communication.commands;

import com.laggytrylma.utils.communication.commands.models.ClientCommands;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;

/**
 * Class for building IModelCommands enum types from strings.
 */
public class ModelCommandsEnumBuilder {
  /**
   * Builds enum from model and command string.
   * @param model Model enum String.
   * @param command Command enum type String.
   * @return IModelCommands enum
   */
  public static IModelCommands buildEnum(String model, String command) {
    switch (model) {
      case "lobby" -> { return LobbyCommands.fromString(command); }
      case "client" -> { return ClientCommands.fromString(command); }
      case "game" -> { return GameCommands.fromString(command); }
    }
    return null;
  }
}
