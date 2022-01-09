package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

public class SendPlayerInfo implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendPlayerInfo(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendPlayerInfo();
  }
}
