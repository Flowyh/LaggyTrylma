package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
