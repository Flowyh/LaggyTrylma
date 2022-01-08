package com.laggytrylma.utils.communication.commands;

import com.laggytrylma.utils.communication.commands.models.ClientCommands;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;

public class ModelCommandsEnumBuilder {
  public static IModelCommands buildEnum(String model, String command) {
    switch (model) {
      case "lobby" -> { return LobbyCommands.fromString(command); }
      case "client" -> { return ClientCommands.fromString(command); }
      case "game" -> { return GameCommands.fromString(command); }
    }
    return null;
  }
}
