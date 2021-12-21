package com.laggytrylma.utils.communication.commands;

import com.laggytrylma.utils.communication.commands.models.Client;
import com.laggytrylma.utils.communication.commands.models.Game;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.Lobby;

public class ModelCommandsEnumBuilder {
  public static IModelCommands buildEnum(String model, String command) {
    switch (model) {
      case "lobby" -> { return Lobby.fromString(command); }
      case "client" -> { return Client.fromString(command); }
      case "game" -> { return Game.fromString(command); }
    }
    return null;
  }
}
