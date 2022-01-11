package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendGame implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendGame(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendGame();
  }
}
