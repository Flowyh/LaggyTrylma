package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class MessageAll implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public MessageAll(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.messageAll();
  }
}
