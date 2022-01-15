package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

/**
 * Send a specific command to given client.
 */
public class SendCommandToPlayer implements BaseServerCommand {
  /**
   * Command emitter.
   */
  private final BaseGameServerCommandsReceiver emitter;

  /**
   * Class constructor
   * @param em new command emitter
   */
  public SendCommandToPlayer(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  /**
   * Execute specific method in emitter.
   * @return int execution status
   */
  @Override
  public int execute() {
    return emitter.sendCommandToPlayer();
  }
}
