package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.SendAllGame;
import com.laggytrylma.backend.server.commands.SendAllNextPlayer;
import com.laggytrylma.backend.server.commands.SendAllPlayerInfo;
import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static java.util.Collections.shuffle;

public class BaseGameState {
  private static BaseGameServer serv;
  private static Game game = null;
  public static int moves = 0;
  public static int next = -1;
  private final Random R = new Random();
  private static final ArrayList<Player> availablePlayerList = new ArrayList<>(Arrays.asList(
          new Player(1, "1", new Color(249, 65, 68)),
          new Player(2, "2", new Color(248, 150, 30)),
          new Player(3, "3", new Color(249, 199, 79)),
          new Player(4, "4", new Color(144, 190, 109)),
          new Player(5, "5", new Color(67, 170, 139)),
          new Player(6, "6", new Color(87, 117, 144))
  ));
  private static final ArrayList<UUID> currentClients = new ArrayList<>();
  private GameBuilderDirector gameBuilderDirector;

  public BaseGameState() { }

  public void bindServer(BaseGameServer s) {
    serv = s;
  }

  protected void setGameBuilderDirector(GameBuilderDirector gBD) {
    this.gameBuilderDirector = gBD;
  }

  public int getClientPlayerIdByUUID(UUID uuid) {
    return ((BaseGameSocket) serv.getClients().get(uuid)).getPlayerId();
  }

  public int getClientPlayerNameByUUID(UUID uuid) {
    return ((BaseGameSocket) serv.getClients().get(uuid)).getPlayerId();
  }

  public Piece getPieceById(int id) {
    return game.getPieceById(id);
  }

  public Square getSquareById(int id) {
    return game.getSquareById(id);
  }

  public boolean movePiece(int pieceId, int destId) {
    Piece piece = getPieceById(pieceId);
    Square dest = getSquareById(destId);
    if(piece == null || dest == null) return false;
    return game.move(piece, dest);
  }

  public boolean comparePieceOwnerAndClient(int pieceId, UUID client) {
    int owner = getPieceById(pieceId).getOwner().getId();
    return getClientPlayerIdByUUID(client) == owner;
  }

  public void moveEvent() {


    moves++;
    UUID next_uuid = currentClients.get(moves % currentClients.size());
    next = getClientPlayerIdByUUID(next_uuid);
    game.setCurrentPlayer(game.getPlayerById(next));
    Logger.debug("Current move: " + moves + " Player: " + getClientPlayerNameByUUID(next_uuid));
    serv.cmdExecutor.executeCommand(new SendAllNextPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
  }

  public void removeClient(UUID uuid) {
    currentClients.remove(uuid);
  }

  public Player getPlayer(int index) {
    return availablePlayerList.get(index);
  }

  public void addNewClient(UUID newClient) {
    currentClients.add(newClient);
  }

  public void clearGame() {
    game = null;
  }

  public void shufflePlayerList() {
    shuffle(availablePlayerList);
  }

  public boolean doesGameExist() {
    return game != null;
  }

  public void startGame() {
    Logger.debug("Starting new game!");
    // Shuffle player queue
    shuffle(currentClients);
    // Set all players
    Player[] players = availablePlayerList.toArray(new Player[0]);
    gameBuilderDirector.setPlayers(players);
    next = getClientPlayerIdByUUID(currentClients.get(0));
    Logger.debug("Current move no.: " + moves + " Player: " + getClientPlayerNameByUUID(currentClients.get(0)));
    // Build and start a new game
    game = gameBuilderDirector.build();
    game.setCurrentPlayer(game.getPlayerById(next));
    // Send game/player info to clients
    serv.cmdExecutor.executeCommand(new SendAllGame(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
    serv.cmdExecutor.executeCommand(new SendAllPlayerInfo(new BaseGameServerCommandsReceiver(serv.getClients())));
    serv.cmdExecutor.executeCommand(new SendAllNextPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), game)));
  }
}
