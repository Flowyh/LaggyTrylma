package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendCommandToPlayer implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendCommandToPlayer(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendCommandToPlayer();
  }
}
