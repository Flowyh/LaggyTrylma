package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendPlayerInfo implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendPlayerInfo(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendPlayerInfo();
  }
}
