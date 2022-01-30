package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

/**
 * Send all clients serialized game object.
 */
public class StartGame implements BaseServerCommand {
  /**
   * Command emitter.
   */
  private final BaseGameServerCommandsReceiver emitter;

  /**
   * Class constructor
   * @param em new command emitter
   */
  public StartGame(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  /**
   * Execute specific method in emitter.
   * @return int execution status
   */
  @Override
  public int execute() {
    return emitter.startGame();
  }
}
