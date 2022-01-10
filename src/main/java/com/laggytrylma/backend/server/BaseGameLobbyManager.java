package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.MessageAll;
import com.laggytrylma.backend.server.commands.SendAllGame;
import com.laggytrylma.backend.server.commands.SendAllNextPlayer;
import com.laggytrylma.backend.server.commands.SendAllPlayerInfo;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.common.builders.AbstractGameBuilder;
import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.*;

public class BaseGameLobbyManager {
  public static BaseGameServer serv;
  public final Map<Integer, BaseGameState> games = new HashMap<>();
  private int id = 0;

  public void bindServer(BaseGameServer s) {
    serv = s;
  }

  public int getNewId() {
    return id++;
  }

  public int createNewLobby(UUID client) {
    BaseGameState newLobby = new BaseGameState();
    newLobby.bindServer(serv);
    newLobby.setGameOwner(client);
    newLobby.setGameBuilderDirector(new GameBuilderDirector(new ClassicTrylmaBuilder())); // default builder
    int id = getNewId();
    games.put(id, newLobby);
    return id;
  }

  public void setLobbyGameBuilder(int id, AbstractGameBuilder builder) {
    BaseGameState lobby = games.get(id);
    lobby.setGameBuilderDirector(new GameBuilderDirector(builder));
  }

  public String getLobbyInfo(int id) {
    BaseGameState lobby = games.get(id);
    Map<String, String> args = new HashMap<>();
    args.put("owner", lobby.getOwnerName());
    args.put("players", Integer.toString(lobby.getCurrentPlayersCount()));
    return ObjectJSONSerializer.serialize(args);
  }

  public Map<String, String> getAllLobbys() {
    Map<String, String> infos = new HashMap<>();
    for(int key: games.keySet()) {
      infos.put(Integer.toString(key), getLobbyInfo(key));
    }
    return infos;
  }


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

  public int getLobbyIdByClient(UUID client) {
    for(int key: games.keySet()) {
      if(games.get(key).hasClient(client)) return key;
    }
    return -1;
  }

  public Game getGameByClient(UUID client) {
    return getGameStateByClient(client).getGame();
  }

  public BaseGameState getGameStateByClient(UUID client) {
    for(int key: games.keySet()) {
      if(games.get(key).hasClient(client)) return games.get(key);
    }
    return null;
  }

  public UUID getGameOwnerById(int id) {
    return games.get(id).getOwner();
  }

  public void addNewClient(int id, UUID client) {
    BaseGameState lobby = games.get(id);
    lobby.addNewClient(client);
  }

  public void removeClient(UUID client) {
    BaseGameState lobby = getGameStateByClient(client);
    if(lobby.isOwner(client)) lobby.passOwnership(null);
    lobby.removeClient(client);
    Map<UUID, BaseGameSocket> clients = serv.lobbyManager.getClientsFromGameState(client);
    serv.cmdExecutor.executeCommand(new MessageAll(new BaseGameServerCommandsReceiver(clients, "Client " + client + " has left.")));
  }

  public void removeLobby(int id, UUID client) {
    if(client != getGameOwnerById(id)) return;
    games.remove(id);
  }

  public void startGame(UUID client) {
    BaseGameState lobby = getGameStateByClient(client);
    lobby.startGame();
    Game game = lobby.getGame();
    // Send game/player info to clients
    serv.cmdExecutor.executeCommand(new SendAllGame(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
    serv.cmdExecutor.executeCommand(new SendAllPlayerInfo(new BaseGameServerCommandsReceiver(serv.getClients())));
    serv.cmdExecutor.executeCommand(new SendAllNextPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
  }
}
