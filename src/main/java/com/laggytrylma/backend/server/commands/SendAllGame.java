package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

/**
 * Send all clients serialized game object.
 */
public class SendAllGame implements BaseServerCommand {
  /**
   * Command emitter.
   */
  private final BaseGameServerCommandsReceiver emitter;

  /**
   * Class constructor
   * @param em new command emitter
   */
  public SendAllGame(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  /**
   * Execute specific method in emitter.
   * @return int execution status
   */
  @Override
  public int execute() {
    return emitter.sendAllGame();
  }
}
