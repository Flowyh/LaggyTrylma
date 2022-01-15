package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

/**
 * Message all clients excluding one command.
 */
public class MessageAllExcluding implements BaseServerCommand {
  /**
   * Command emitter.
   */
  private final BaseGameServerCommandsReceiver emitter;

  /**
   * Class constructor
   * @param em new command emitter
   */
  public MessageAllExcluding(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  /**
   * Execute specific method in emitter.
   * @return int execution status
   */
  @Override
  public int execute() {
    return emitter.messageAllExcluding();
  }
}
