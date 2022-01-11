package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.SendAllNextPlayer;
import com.laggytrylma.common.models.*;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.utils.Logger;

import java.util.ArrayList;
import java.util.UUID;

import static java.util.Collections.shuffle;

public class BaseGameState {
  private static BaseGameServer serv;
  private static Game game = null;
  public static int moves = 0;
  public static int next = -1;
  public int playerLimit = 6;
  private static final ArrayList<UUID> currentClients = new ArrayList<>();
  private static UUID gameOwner;
  private GameBuilderDirector gameBuilderDirector;

  public BaseGameState() { }

  public void bindServer(BaseGameServer s) {
    serv = s;
  }
  protected void setGameBuilderDirector(GameBuilderDirector gBD) {
    this.gameBuilderDirector = gBD;
  }

  public void setGameOwner(UUID owner) {
    gameOwner = owner;
  }

  public void setPlayerLimit(int playerLimit) {
    this.playerLimit = playerLimit;
  }

  public void setGameRules() {
    //?
  }

  public ArrayList<UUID> getCurrentClients() {
    return currentClients;
  }

  public Player getPlayerByIndex(int id) {
    return serv.getClients().get(currentClients.get(id)).getPlayer();
  }

  public int getClientPlayerIdByUUID(UUID uuid) {
    return serv.getClients().get(uuid).getPlayerId();
  }

  public String getClientPlayerNameByUUID(UUID uuid) {
    return serv.getClients().get(uuid).getPlayerName();
  }

  public Game getGame() { return game; }

  public UUID getOwner() { return gameOwner; }
  public String getOwnerName() {
    return getClientPlayerNameByUUID(gameOwner);
  }

  public Piece getPieceById(int id) {
    return game.getPieceById(id);
  }

  public Square getSquareById(int id) {
    return game.getSquareById(id);
  }

  public int getCurrentPlayersCount() {
    return currentClients.size();
  }

  public boolean hasClient(UUID client) {
    return currentClients.contains(client);
  }

  public boolean isOwner(UUID client) {
    return client == gameOwner;
  }

  public boolean isLobbyReady() {
    return currentClients.size() == playerLimit;
  }

  public void passOwnership(UUID client) {
    int ownerIndex = currentClients.indexOf(client);
    UUID nextOwner = client == null ? currentClients.get(ownerIndex + 1) : client;
    if(nextOwner == null) return;
    gameOwner = nextOwner;
  }

  public boolean comparePieceOwnerAndClient(int pieceId, UUID client) {
    int owner = getPieceById(pieceId).getOwner().getId();
    return getClientPlayerIdByUUID(client) == owner;
  }

  public boolean movePiece(int pieceId, int destId) {
    Piece piece = getPieceById(pieceId);
    Square dest = getSquareById(destId);
    if(piece == null || dest == null) return false;
    if(game.move(piece, dest)) {
      moves++;
      UUID next_uuid = currentClients.get(moves % currentClients.size());
      next = getClientPlayerIdByUUID(next_uuid);
      game.setCurrentPlayer(game.getPlayerById(next));
      Logger.debug("Current move: " + moves + " Player: " + getClientPlayerNameByUUID(next_uuid));
      serv.cmdExecutor.executeCommand(new SendAllNextPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
      return true;
    } else {
      return false;
    }
  }

  public void removeClient(UUID uuid) {
    currentClients.remove(uuid);
  }

  public void addNewClient(UUID newClient) {
    currentClients.add(newClient);
  }

  public void clearGame() {
    game = null;
  }

  public void shufflePlayerList() {
    shuffle(currentClients);
  }

  public boolean doesGameExist() {
    return game != null;
  }

  public ArrayList<Player> instantiatePlayers() {
    ColorAssigner.permutate();
    ArrayList<Player> players = new ArrayList<>();
    Player temp;
    int i = 0;
    for(UUID key: currentClients) {
      temp = serv.getPlayerByUUID(key);
      temp.setColor(ColorAssigner.getColor(i));
      players.add(temp);
      i++;
    }
    return players;
  }

  public void startGame() {
    Logger.debug("Starting new game!");
    // Shuffle player queue
    shufflePlayerList();
    // Set all players
    Player[] players = instantiatePlayers().toArray(new Player[0]);
    gameBuilderDirector.setPlayers(players);
    next = getClientPlayerIdByUUID(currentClients.get(0));
    Logger.debug("Current move no.: " + moves + " Player: " + getClientPlayerNameByUUID(currentClients.get(0)));
    // Build and start a new game
    game = gameBuilderDirector.build();
    game.setCurrentPlayer(game.getPlayerById(next));
  }
}
