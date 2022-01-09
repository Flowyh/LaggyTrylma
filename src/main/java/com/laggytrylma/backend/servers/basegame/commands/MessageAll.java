package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
