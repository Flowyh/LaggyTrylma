package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.*;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.common.builders.AbstractGameBuilder;
import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.*;

/**
 * BaseGameServer lobby management component.
 * Holds all the lobby logic and objects.
 */
public class BaseGameLobbyManager {
  /**
   * Bound BaseGameServer instance.
   */
  public static BaseGameServer serv;
  /**
   * Map of stored game states.
   */
  public final Map<Integer, BaseGameState> games = new HashMap<>();
  /**
   * Current game id.
   */
  private int id = 0;

  /**
   * Bind server to this class.
   * @param s BaseGameServer instance
   */
  public void bindServer(BaseGameServer s) {
    serv = s;
  }

  /**
   * Get next game id (increment id field)
   * @return int new game id
   */
  public int getNewId() {
    return id++;
  }

  /**
   * Create new lobby object and set client, that issued this, as an owner.
   * @param client uuid of a client that has requested to create a new lobby
   * @return int new lobby id
   */
  public int createNewLobby(UUID client) {
    BaseGameState newLobby = new BaseGameState();
    newLobby.bindServer(serv);
    newLobby.setGameOwner(client);
    newLobby.setGameBuilderDirector(new GameBuilderDirector(new ClassicTrylmaBuilder())); // default builder
    int id = getNewId();
    games.put(id, newLobby);
    return id;
  }

  /**
   * Set new lobby's game builder.
   * @param id lobby id
   * @param builder new game builder
   */
  public void setLobbyGameBuilder(int id, AbstractGameBuilder builder) {
    BaseGameState lobby = games.get(id);
    lobby.setGameBuilderDirector(new GameBuilderDirector(builder));
  }

  /**
   * Get lobby info as serialized JSON string.
   * @param id lobby id
   * @return String serialized lobby info
   */
  public String getLobbyInfo(int id) {
    BaseGameState lobby = games.get(id);
    Map<String, String> args = new HashMap<>();
    args.put("owner", lobby.getOwnerName());
    args.put("players", Integer.toString(lobby.getCurrentPlayersCount()));
    return ObjectJSONSerializer.serialize(args);
  }

  /**
   * Get all lobbies info.
   * @return Map of serialized lobbies info
   */
  public Map<String, String> getAllLobbies() {
    Map<String, String> infos = new HashMap<>();
    for(int key: games.keySet()) {
      infos.put(Integer.toString(key), getLobbyInfo(key));
    }
    return infos;
  }

  /**
   * Get all clients (uuids and associated sockets) from given client's game.
   * @param client uuid of a client that has requested to get the map of clients
   * @return Map of clients' uuids and sockets
   */
  public Map<UUID, BaseGameSocket> getClientsFromGameState(UUID client) {
    Map<UUID, BaseGameSocket> result = new HashMap<>();
    BaseGameState lobby = getGameStateByClient(client);
    if(lobby == null) return null;
    ArrayList<UUID> clients_ids = lobby.getCurrentClients();
    for(UUID key : clients_ids) {
      result.put(key, serv.getClients().get(key));
    }
    return result;
  }

  /**
   * Get client's lobby, if they are in any.
   * @param client uuid of a client that has requested to get their lobby id
   * @return int lobby id (-1 if currently not in a lobby)
   */
  public int getLobbyIdByClient(UUID client) {
    for(int key: games.keySet()) {
      if(games.get(key).hasClient(client)) return key;
    }
    return -1;
  }

  /**
   * Get game object by client's uuid.
   * @param client uuid of a client that has requested to get their lobby's game object.
   * @return Game lobby game object
   */
  public Game getGameByClient(UUID client) {
    return getGameStateByClient(client).getGame();
  }

  /**
   * Get BaseGameState object by client's uuid.
   * @param client uuid of a client that has requested to get their lobby's BaseGameState object.
   * @return BaseGameState lobby baseGameState object
   */
  public BaseGameState getGameStateByClient(UUID client) {
    for(int key: games.keySet()) {
      if(games.get(key).hasClient(client)) return games.get(key);
    }
    return null;
  }

  /**
   * Get lobby's owner uuid by given lobby id.
   * @param id lobby id
   * @return UUID lobby's owner uuid
   */
  public UUID getGameOwnerById(int id) {
    return games.get(id).getOwner();
  }

  /**
   * Add new client to given lobby.
   * @param id lobby id
   * @param client new client's uuid
   */
  public void addNewClient(int id, UUID client) {
    BaseGameState lobby = games.get(id);
    lobby.addNewClient(client);
    if(lobby.isLobbyReady()) startGame(client);
  }

  /**
   * Remove given client from his lobby
   * @param client uuid of a client which needs to be removed
   */
  public void removeClient(UUID client) {
    BaseGameState lobby = getGameStateByClient(client);
    if(lobby != null){
      if(lobby.isOwner(client)){
        removeLobby(getLobbyIdByClient(client), client);
        return;
      }
      lobby.removeClient(client);
    }

    Map<UUID, BaseGameSocket> clients = getClientsFromGameState(client);
    if(clients == null){
      return;
    }
    serv.cmdExecutor.executeCommand(new MessageAll(new BaseGameServerCommandsReceiver(clients, "Client " + client + " has left.")));
  }

  /**
   * Remove given lobby and save game to archive
   * @param id lobby id
   * @param client uuid of a client that has issued lobby removal (used to determine other clients in the same lobby)
   */
  public void removeLobby(int id, UUID client) {
    Map<UUID, BaseGameSocket> clients = getClientsFromGameState(client);
    if(clients != null) {
      for (UUID key : clients.keySet()) {
        serv.cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(clients, key, LobbyCommands.DELETE, new HashMap<>())));
      }
    }
    games.get(id).archive();
    games.remove(id);
  }

  /**
   * Start game in given lobby
   * @param client uuid of a client that has issued game start.
   */
  public void startGame(UUID client) {
    BaseGameState lobby = getGameStateByClient(client);
    lobby.startGame();
    Game game = lobby.getGame();

    Map<UUID, BaseGameSocket> clients = getClientsFromGameState(client);
    // Send game/player info to clients
    serv.cmdExecutor.executeCommand(new StartGame(new BaseGameServerCommandsReceiver(clients, game)));
    serv.cmdExecutor.executeCommand(new SendAllPlayerInfo(new BaseGameServerCommandsReceiver(clients)));
    serv.cmdExecutor.executeCommand(new SendAllNextPlayer(new BaseGameServerCommandsReceiver(clients, game)));
  }
}
