package com.laggytrylma.backend.sockets.dummy;

import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.backend.servers.dummy.DummyServer;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class DummySocketHandler extends AbstractCommandHandler {

  @Override
  public Object processInput(Object o, UUID client) {
    if(o.equals("!quit")) {
      DummyServer.rmvClient(client);
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

  @Override
  public Object handleCommand(IModelCommands cmd, Map<String, String> args, Object o, UUID client) {
    return null;
  }
}
