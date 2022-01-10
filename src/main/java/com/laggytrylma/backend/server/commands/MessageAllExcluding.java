package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReciever;

public class MessageAllExcluding implements BaseServerCommand {
  private final BaseGameServerCommandsReciever emitter;

  public MessageAllExcluding(BaseGameServerCommandsReciever em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.messageAllExcluding();
  }
}
