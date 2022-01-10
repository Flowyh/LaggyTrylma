package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class SendAllGame implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public SendAllGame(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.sendAllGame();
  }
}
