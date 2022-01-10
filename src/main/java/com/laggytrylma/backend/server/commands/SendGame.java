package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class SendGame implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendGame(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendGame();
  }
}
