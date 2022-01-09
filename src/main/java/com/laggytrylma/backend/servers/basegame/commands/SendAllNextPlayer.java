package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
