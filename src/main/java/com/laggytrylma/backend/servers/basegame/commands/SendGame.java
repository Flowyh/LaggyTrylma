package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
