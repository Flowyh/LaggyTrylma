package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

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
