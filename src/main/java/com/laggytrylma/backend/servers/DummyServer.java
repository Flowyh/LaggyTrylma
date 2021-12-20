package com.laggytrylma.backend.servers;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.backend.ctx.AbstractSocket;
import com.laggytrylma.backend.ctx.AbstractSocketBuilder;
import com.laggytrylma.utils.Logger;

import java.io.IOException;
import java.net.Socket;

public class DummyServer extends AbstractServer {
  private boolean running = true;
  private static AbstractServer instance;
  static {
    try {
      instance = new DummyServer(21375, "Dummy");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  private DummyServer(int port, String name) throws IOException {
    super(port, name);
  }

  // DOUBLE-CHECKED LOCKING
  public static AbstractServer getInstance() throws IOException {
    AbstractServer localRef = instance;
    if (localRef == null) {
      synchronized (DummyServer.class) {
        localRef = instance;
        if (localRef == null) {
          instance = localRef = new DummyServer(21375, "Dummy");
        }
      }
    }
    return localRef;
  }

  private final AbstractSocketBuilder socketBuilder = new DummySocketBuilder();

  @Override
  protected void setup() {
    setSocketBuilder(socketBuilder);
  }

  @Override
  public void listen() {
    while (running) {
      try {
        Socket clientSocket = serverSocket.accept();
        if(!clientSocket.isClosed()) {
          AbstractSocket socket = createNewSocket(clientSocket);
          clients.put(socket.getUUID(), socket);
          Logger.debug("Current clients: " + clients.size());
          threadPool.execute(socket);
        }
      } catch (IOException ioException) {
        Logger.error(ioException.getMessage());
      }
    }
  }
}
