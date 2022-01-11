package com.laggytrylma.backend;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.utils.Logger;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    Logger.setDepth(4);
    //AbstractServer server = DummyServer.getInstance();
    AbstractServer server = BaseGameServer.getInstance();
    server.startServer(100, null);
    server.listen();
  }
}
