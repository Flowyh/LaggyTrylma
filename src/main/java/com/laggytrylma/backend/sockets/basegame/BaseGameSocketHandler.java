package com.laggytrylma.backend.sockets.basegame;

import com.laggytrylma.backend.ctx.ISocketHandler;
import com.laggytrylma.backend.servers.basegame.BaseGameServer;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;

import java.io.IOException;
import java.util.UUID;

public class BaseGameSocketHandler implements ISocketHandler {
  @Override
  public Object processInput(Object o, UUID client) {
    if(!(o instanceof BaseCommandWrapper<?>)) return 0;
//    if(o.equals("!quit")) {
//      BaseGameServer.removeClient(client);
//      try {
//        BaseGameServer.messageAll("Client " + client + " has left.");
//      } catch (IOException ioException) {
//        Logger.error(ioException.getMessage());
//      }
//      return -1;
//    } else {
//      try {
//        BaseGameServer.messageAll(client + ": " + o);
//      } catch (IOException ioException) {
//        Logger.error(ioException.getMessage());
//      }
//      return 1;
//    }
    return 1;
  }
}