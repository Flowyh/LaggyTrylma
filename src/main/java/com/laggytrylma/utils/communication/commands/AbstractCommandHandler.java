package com.laggytrylma.utils.communication.commands;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractCommandHandler {
  public Object processInput(Object o, UUID client) {
    if(!(o instanceof BaseCommandWrapper<?>)) return 0;
    IModelCommands cmd = ((BaseCommandWrapper<?>) o).getCommand();
    Map<String, String> args = ((BaseCommandWrapper<?>) o).getArgs();
    if(cmd == null || args == null) return 0;
    return handleCommand(cmd, args, client);
  }

  public abstract Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client);
}
