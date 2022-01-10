package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class SendAllGame implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendAllGame(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllGame();
  }
}
