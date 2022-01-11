package com.laggytrylma.backend.server.commands;
import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendAllWinner implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendAllWinner(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllWinner();
  }
}
