package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.BaseServerCommand;

import java.util.ArrayList;
import java.util.List;

public class BaseGameServerCommandsExecutor {
  private final List<BaseServerCommand> baseServerCommands = new ArrayList<>();
  public int executeCommand(BaseServerCommand command) {
    baseServerCommands.add(command);
    return command.execute();
  }
}
