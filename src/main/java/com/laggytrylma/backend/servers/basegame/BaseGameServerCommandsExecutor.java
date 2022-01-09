package com.laggytrylma.backend.servers.basegame;

import com.laggytrylma.backend.servers.basegame.commands.BaseServerCommand;

import java.util.ArrayList;
import java.util.List;

public class BaseGameServerCommandsExecutor {
  private final List<BaseServerCommand> baseServerCommands = new ArrayList<>();
  public int executeCommand(BaseServerCommand command) {
    baseServerCommands.add(command);
    return command.execute();
  }
}
