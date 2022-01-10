package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendAllPlayerInfo implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendAllPlayerInfo(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllPlayerInfo();
  }
}