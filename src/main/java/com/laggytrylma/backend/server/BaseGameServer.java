package com.laggytrylma.backend.server;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.backend.sockets.BaseGameSocketBuilder;
import com.laggytrylma.utils.Logger;
import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class BaseGameServer extends AbstractServer {
  private static AbstractServer instance = new BaseGameServer(21375, "BaseGame");
  private final AbstractSocketBuilder socketBuilder = new BaseGameSocketBuilder();
  public final BaseGameServerCommandsExecutor cmdExecutor = new BaseGameServerCommandsExecutor();
  private final Map<UUID, BaseGameSocket> clients = new HashMap<>();
  public final BaseGameLobbyManager lobbyManager = new BaseGameLobbyManager();
  private int clientID = 0;

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

  public Map<UUID, BaseGameSocket> getClients() {
    return clients;
  }

  public int getNewClientID() {
    return clientID++;
  }
  public Player getPlayerByUUID(UUID uuid) {
    return clients.get(uuid).getPlayer();
  }

  public void removeClient(UUID uuid) {
    lobbyManager.removeClient(uuid);
    clients.remove(uuid);
  }

  @Override
  protected void setup() {
    setSocketBuilder(socketBuilder);
    lobbyManager.bindServer(this);
  }

  @Override
  public void listen() {
    while (running) {
      try {
        Socket clientSocket = serverSocket.accept();

        Logger.debug("Clients count " + clients.size());

        if (!clientSocket.isClosed()) {
          // Create new socket
          BaseGameSocket socket = (BaseGameSocket) createNewSocket(clientSocket);
          socket.bindServer(this); // Bind server to socket
          clients.put(socket.getUUID(), socket); // Add new socket to clients list
          Logger.debug("Current clients: " + clients.size());
          // Create new player instance for current socket
          int newId = getNewClientID();
          Player clientPlayer = new Player(newId, Integer.toString(newId), null);
          socket.setPlayer(clientPlayer);
          Logger.debug("New player: " + socket.getPlayerString());
          // Run socket
          threadPool.execute(socket);
        }
      } catch (IOException ioException) {
        Logger.debug("Current clients count: " + clients.size());
        Logger.error(ioException.getMessage());
      }
    }
  }
}
