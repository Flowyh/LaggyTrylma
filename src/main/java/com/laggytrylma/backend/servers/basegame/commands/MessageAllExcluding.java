package com.laggytrylma.backend.servers.basegame.commands;

import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;

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
