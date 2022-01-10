package com.laggytrylma.backend.server.commands;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;

public class MessageAllExcluding implements BaseServerCommand {
  private final BaseGameServerCommandsReceiver emitter;

  public MessageAllExcluding(BaseGameServerCommandsReceiver em) {
    this.emitter = em;
  }

  @Override
  public int execute() {
    return emitter.messageAllExcluding();
  }
}
