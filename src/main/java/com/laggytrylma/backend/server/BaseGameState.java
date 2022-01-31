package com.laggytrylma.backend.server;

import com.laggytrylma.backend.server.commands.SendAllNextPlayer;
import com.laggytrylma.backend.server.commands.SendAllWinner;
import com.laggytrylma.common.models.*;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.utils.Logger;

import java.util.ArrayList;
import java.util.UUID;

import static java.util.Collections.shuffle;

/**
 * Class which holds all the game logic and manipulation methods.
 */
public class BaseGameState {
  /**
   * Bound BaseGameServer instance.
   */
  private BaseGameServer serv;
  /**
   * Current game object.
   */
  private Game game = null;
  /**
   * Current number of performed moves.
   */
  public int moves = 0;
  /**
   * Id of next player that has to move.
   */
  public int next = -1;
  /**
   * Limit of players in current game.
   */
  public int playerLimit = 6;
  /**
   * List of game's current clients/players.
   */
  private final ArrayList<UUID> currentClients = new ArrayList<>();
  /**
   * UUID of game's owner.
   */
  private UUID gameOwner;
  /**
   * Game's builder director.
   */
  private GameBuilderDirector gameBuilderDirector;

  /**
   * Empty constructor
   */
  public BaseGameState() { }

  /**
   * Bind BaseGameServer to this instance
   * @param s BaseGameServer instance
   */
  public void bindServer(BaseGameServer s) {
    serv = s;
  }

  /**
   * Set new gameBuilderDirector.
   * @param gBD GameBuilderDirector object.
   */
  protected void setGameBuilderDirector(GameBuilderDirector gBD) {
    this.gameBuilderDirector = gBD;
  }

  /**
   * Set game's owner.
   * @param owner uuid of an owner
   */
  public void setGameOwner(UUID owner) {
    gameOwner = owner;
  }

  /**
   * Set game's player limit.
   * @param playerLimit new player's limit
   */
  public void setPlayerLimit(int playerLimit) {
    this.playerLimit = playerLimit;
  }

  /**
   * Set game's rules (currently unused).
   */
  public void setGameRules() {
    //?
  }

  /**
   * Get current list of game's current players' uuids.
   * @return ArrayList of current game clients
   */
  public ArrayList<UUID> getCurrentClients() {
    return currentClients;
  }

  /**
   * Get player object by client/player id.
   * @param id client/player id
   * @return Player associated object
   */
  public Player getPlayerByIndex(int id) {
    return serv.getClients().get(currentClients.get(id)).getPlayer();
  }

  /**
   * Get player id by client's uuid.
   * @param uuid client's uuid
   * @return int client/player id
   */
  public int getClientPlayerIdByUUID(UUID uuid) {
    return serv.getClients().get(uuid).getPlayerId();
  }

  /**
   * Get client's player name by their uuid.
   * @param uuid client's uuid
   * @return String player name of given client
   */
  public String getClientPlayerNameByUUID(UUID uuid) {
    return serv.getClients().get(uuid).getPlayerName();
  }

  /**
   * Get game object from this instance.
   * @return Game object
   */
  public Game getGame() { return game; }

  /**
   * Get owner's uuid from this instance.
   * @return UUID of an owner
   */
  public UUID getOwner() { return gameOwner; }

  /**
   * Get owner's name from this instance.
   * @return String owner's name
   */
  public String getOwnerName() {
    return getClientPlayerNameByUUID(gameOwner);
  }

  /**
   * Get given piece from Game object by it's id.
   * @param id piece's id
   * @return Piece object
   */
  public Piece getPieceById(int id) {
    return game.getPieceById(id);
  }

  /**
   * Get given square from Game object by it's id.
   * @param id square's id
   * @return Square object
   */
  public Square getSquareById(int id) {
    return game.getSquareById(id);
  }

  /**
   * Get current number of connected players.
   * @return int number of game's players
   */
  public int getCurrentPlayersCount() {
    return currentClients.size();
  }

  /**
   * Check whether uuid exists in current players list.
   * @param client uuid to be checked
   * @return True if player is in this game, False if otherwise
   */
  public boolean hasClient(UUID client) {
    return currentClients.contains(client);
  }

  /**
   * Check whether uuid is current game's owner.
   * @param client uuid to be checked
   * @return True if player is the owner, False if otherwise
   */
  public boolean isOwner(UUID client) {
    return client == gameOwner;
  }

  /**
   * Check whether the lobby is ready.
   * @return True if number of currently connected players equals the player limit, False if otherwise
   */
  public boolean isLobbyReady() {
    return currentClients.size() == playerLimit;
  }

  /**
   * Pass ownership to another client in the list or to specified client.
   * @param client UUID of the next owner
   */
  public void passOwnership(UUID client) {
    int ownerIndex = currentClients.indexOf(client);
    UUID nextOwner = client == null ? currentClients.get(ownerIndex + 1) : client;
    if(nextOwner == null) return;
    gameOwner = nextOwner;
  }

  /**
   * Compare piece's owner and given client.
   * @param pieceId id of a piece to be checked
   * @param client uuid of a client to be compared with piece's owner
   * @return True if given client is the owner of given piece, False if otherwise
   */
  public boolean comparePieceOwnerAndClient(int pieceId, UUID client) {
    int owner = getPieceById(pieceId).getOwner().getId();
    return getClientPlayerIdByUUID(client) == owner;
  }

  /**
   * Move piece to new location.
   * Also sends an info about next player to every connected player if move was successful.
   * @param pieceId id of a piece to be moved
   * @param destId id of a square to move the piece to
   * @return True if the move was succesful, False if otherwise
   */
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

  /**
   * Remove client from this game
   * @param uuid uuid of a client to be removed
   */
  public void removeClient(UUID uuid) {
    currentClients.remove(uuid);
  }

  /**
   * Add new client to current client's list.
   * @param newClient uuid of a client to be added
   */
  public void addNewClient(UUID newClient) {
    currentClients.add(newClient);
  }

  /**
   * Set game object as null.
   */
  public void clearGame() {
    game = null;
  }

  /**
   * Shuffle the list of current clients.
   */
  public void shufflePlayerList() {
    shuffle(currentClients);
  }

  /**
   * Check whether the game exists.
   * @return True if game exists, False if otherwise
   */
  public boolean doesGameExist() {
    return game != null;
  }

  /**
   * Create Player object from current client's list.
   * Get's a random color from ColorAssigner static class and assigns it to every client object.
   * @return ArrayList of instantiated Players
   */
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

  /**
   * Start this game.
   */
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

    public void archive() {
      serv.repoWrapper.create(game);
    }
}
