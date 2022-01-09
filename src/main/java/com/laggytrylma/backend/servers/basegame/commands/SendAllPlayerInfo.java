package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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