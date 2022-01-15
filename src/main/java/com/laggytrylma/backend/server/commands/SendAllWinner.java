package com.laggytrylma.backend.server.commands;
import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

/**
 * Send all clients who is the game winner.
 */
public class SendAllWinner implements BaseServerCommand {
  /**
   * Command emitter.
   */
  private final BaseGameServerCommandsReceiver emitter;

  /**
   * Class constructor
   * @param em new command emitter
   */
  public SendAllWinner(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  /**
   * Execute specific method in emitter.
   * @return int execution status
   */
  @Override
  public int execute() {
    return emitter.sendAllWinner();
  }
}
