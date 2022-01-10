package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class MessageAll implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public MessageAll(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.messageAll();
  }
}
