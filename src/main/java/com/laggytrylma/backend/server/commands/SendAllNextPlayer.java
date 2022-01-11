package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendAllNextPlayer implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendAllNextPlayer(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllNextPlayer();
  }
}
