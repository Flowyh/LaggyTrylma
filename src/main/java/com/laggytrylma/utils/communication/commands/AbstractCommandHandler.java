package com.laggytrylma.utils.communication.commands;

import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.util.Map;
import java.util.UUID;

/**
 * Abstract client connection socket handler.
 */
public abstract class AbstractCommandHandler {
  /**
   * Deserializes sent object into proper BaseCommandWrapper and calls abstract handleCommand.
   * If sent object isn't a proper BaseCommandWrapper or there are no cmd/args present it doesn't handle given request.
   * @param o Object sent over socket connection
   * @param client issuer's uuid
   * @return Object response
   */
  public Object processInput(Object o, UUID client) {
    if(!(o instanceof BaseCommandWrapper<?>)) return 0;
    IModelCommands cmd = ((BaseCommandWrapper<?>) o).getCommand();
    Map<String, String> args = ((BaseCommandWrapper<?>) o).getArgs();
    if(cmd == null || args == null) return 0;
    return handleCommand(cmd, args, client);
  }

  /**
   * Handle given command.
   * It should be properly deserialized first in processInput method ({@link com.laggytrylma.utils.communication.commands.AbstractCommandHandler#processInput(Object, UUID) See here})
   * Then the proper command handler is determined by command's IModelCommands type, given arguments and issuer uuid.
   * @param cmd IModelCommands type
   * @param args Mapped command arguments to String keys and JSONs Strings.
   * @param client issuer uuid
   * @return Object command response
   */
  public abstract Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client);
}
