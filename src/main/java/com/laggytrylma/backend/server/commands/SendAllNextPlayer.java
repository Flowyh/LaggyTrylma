package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class SendAllNextPlayer implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendAllNextPlayer(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllNextPlayer();
  }
}
