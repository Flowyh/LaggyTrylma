package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.BaseServerCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoker/executor for GoF's Command pattern.
 * BaseGameServer will have one instance of such executor and will execute new command objects on it.
 */
public class BaseGameServerCommandsExecutor {
  /**
   * List of already executed commands (it's a surprise tool that will help us later/mystery mouska-tool).
   */
  private final List<BaseServerCommand> baseServerCommands = new ArrayList<>();

  /**
   * Execute issued command class.
   * @param command BaseServerCommand cmd
   * @return execution status
   */
  public int executeCommand(BaseServerCommand command) {
    baseServerCommands.add(command);
    return command.execute();
  }
}
