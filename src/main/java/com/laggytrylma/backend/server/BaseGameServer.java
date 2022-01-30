package com.laggytrylma.backend.server;

import com.laggytrylma.backend.GameRepoWrap;
import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.backend.sockets.BaseGameSocketBuilder;
import com.laggytrylma.utils.Logger;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * BaseGameServer class.
 * Uses singleton GoF pattern to keep only it only as a one instance.
 */
public class BaseGameServer extends AbstractServer {
  /**
   * Singleton instance of this server.
   */
  private static BaseGameServer instance = new BaseGameServer(21375, "BaseGame");
  /**
   * Socket builder object.
   */
  private final AbstractSocketBuilder socketBuilder = new BaseGameSocketBuilder();
  /**
   * Command pattern executor object.
   */
  public final BaseGameServerCommandsExecutor cmdExecutor = new BaseGameServerCommandsExecutor();
  /**
   * List of currently connected players to this server.
   */
  private final Map<UUID, BaseGameSocket> clients = new HashMap<>();
  /**
   * Lobby management handler object.
   */
  public final BaseGameLobbyManager lobbyManager = new BaseGameLobbyManager();
  /**
   * Current client/player id.
   */
  private int clientID = 0;

  /**
   * Class constructor
   * @param port server's port
   * @param name server's name
   */
  private BaseGameServer(int port, String name) {
    super(port, name);
  }

  /**
   * Get instance of this server using double-checked locking method.
   * @return AbstractServer server's instance.
   */
  public static BaseGameServer getInstance() {
    BaseGameServer localRef = instance;
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

  /**
   * Get map of current clients' uuids and their sockets.
   * @return Map
   */
  public Map<UUID, BaseGameSocket> getClients() {
    return clients;
  }

  /**
   * Get next client/player id (increment it).
   * @return int client/player id
   */
  public int getNewClientID() {
    return clientID++;
  }

  /**
   * Get player object from client's uuid
   * @param uuid client's uuid
   * @return Player client's player object
   */
  public Player getPlayerByUUID(UUID uuid) {
    return clients.get(uuid).getPlayer();
  }

  /**
   * Remove client from clients map
   * @param uuid uuid of a client to be removed
   */
  public void removeClient(UUID uuid) {
    lobbyManager.removeClient(uuid);
    clients.remove(uuid);
  }

  /**
   * Setup server.
   */
  @Override
  protected void setup() {
    setSocketBuilder(socketBuilder);
    lobbyManager.bindServer(this);
  }

  /**
   * Start listening for new connections loop.
   */
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
