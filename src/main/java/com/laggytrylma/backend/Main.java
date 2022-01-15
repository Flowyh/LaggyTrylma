package com.laggytrylma.backend;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.utils.Logger;

/**
 * Main backend app.
 */
public class Main {
  /**
   * Main method.
   * @param args Command line args
   */
  public static void main(String[] args)  {
    Logger.setDepth(4);
    AbstractServer server = BaseGameServer.getInstance();
    server.startServer(100, null);
    server.listen();
  }
}
