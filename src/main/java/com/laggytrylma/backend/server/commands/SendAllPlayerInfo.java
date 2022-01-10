package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class SendAllPlayerInfo implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public SendAllPlayerInfo(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllPlayerInfo();
  }
}