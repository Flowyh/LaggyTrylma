package com.laggytrylma.backend.sockets.dummy;

import com.laggytrylma.backend.ctx.ISocketHandler;
import com.laggytrylma.backend.servers.dummy.DummyServer;
import com.laggytrylma.utils.Logger;
import java.io.IOException;
import java.util.UUID;

public class DummySocketHandler implements ISocketHandler {

  @Override
  public Object processInput(Object o, UUID client) {
    if(o.equals("!quit")) {
      DummyServer.removeClient(client);
      try {
        DummyServer.messageAll("Client " + client + " has left.");
      } catch (IOException ioException) {
        Logger.error(ioException.getMessage());
      }
      return -1;
    } else {
      try {
        DummyServer.messageAll(client + ": " + o);
      } catch (IOException ioException) {
        Logger.error(ioException.getMessage());
      }
      return 0;
    }
  }
}
