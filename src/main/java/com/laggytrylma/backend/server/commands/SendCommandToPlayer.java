package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class SendCommandToPlayer implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendCommandToPlayer(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendCommandToPlayer();
  }
}
