package com.laggytrylma.backend.server;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.backend.sockets.BaseGameSocketBuilder;
import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.utils.Logger;
import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class BaseGameServer extends AbstractServer {
  private static AbstractServer instance = new BaseGameServer(21375, "BaseGame");
  private final AbstractSocketBuilder socketBuilder = new BaseGameSocketBuilder();
  public final BaseGameServerCommandsExecutor cmdExecutor = new BaseGameServerCommandsExecutor();
  public final BaseGameState gameState = new BaseGameState();

  private BaseGameServer(int port, String name) {
    super(port, name);
  }

  // DOUBLE-CHECKED LOCKING
  public static AbstractServer getInstance() {
    AbstractServer localRef = instance;
    if (localRef == null) {
      synchronized (BaseGameServer.class) {
        localRef = instance;
        if (localRef == null) {
          instance = localRef = new BaseGameServer(21375, "BaseGame");
        }
      }
    }
    return localRef;
  }

  public Map<UUID, AbstractSocket> getClients() {
    return clients;
  }

  public void removeClient(UUID uuid) {
    gameState.removeClient(uuid);
    clients.remove(uuid);
  }

  @Override
  protected void setup() {
    setSocketBuilder(socketBuilder);
    gameState.setGameBuilderDirector(new GameBuilderDirector(new ClassicTrylmaBuilder()));
    gameState.bindServer(this);
  }

  @Override
  public void listen() {
    while (running) {
      if (clients.size() == 6) { // Block connections above 6
        continue;
      }

      if (clients.size() == 0 && gameState.doesGameExist()) gameState.clearGame(); // Clear game on empty players, idk why

      try {
        Socket clientSocket = serverSocket.accept();
        if (!clientSocket.isClosed()) {
          AbstractSocket socket = createNewSocket(clientSocket);
          if(!(socket instanceof BaseGameSocket)) { // Wrong socket built
            socket.close();
            continue;
          }
          ((BaseGameSocket) socket).bindServer(this);
          clients.put(socket.getUUID(), socket); // Add new socket to clients list
          Logger.debug("Current clients: " + clients.size());

          // No game was created, shuffle player queue, so we pick random players for new clients
          if(clients.size() == 1 && !gameState.doesGameExist()) gameState.shufflePlayerList();
          ((BaseGameSocket) socket).setPlayer(gameState.getPlayer(clients.size() - 1));
          gameState.addNewClient(socket.getUUID());

          if (clients.size() == 2 && !gameState.doesGameExist()) gameState.startGame();

          Logger.debug("New player: " + ((BaseGameSocket) socket).getPlayerString());
          threadPool.execute(socket);
        }
      } catch (IOException ioException) {
        Logger.debug("Current clients count: " + clients.size());
        Logger.error(ioException.getMessage());
      }
    }
  }
}
