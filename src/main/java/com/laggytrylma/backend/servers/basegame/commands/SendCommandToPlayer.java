package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
