package com.laggytrylma.backend.servers.basegame;

import com.laggytrylma.backend.ctx.AbstractServer;
import com.laggytrylma.backend.ctx.AbstractSocket;
import com.laggytrylma.backend.ctx.AbstractSocketBuilder;
import com.laggytrylma.backend.servers.dummy.DummyServer;
import com.laggytrylma.backend.sockets.basegame.BaseGameSocket;
import com.laggytrylma.backend.sockets.basegame.BaseGameSocketBuilder;
import com.laggytrylma.common.*;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static java.util.Collections.shuffle;

public class BaseGameServer extends AbstractServer {
  private static AbstractServer instance = new BaseGameServer(21375, "BaseGame");
  private final AbstractSocketBuilder socketBuilder = new BaseGameSocketBuilder();
  private GameBuilderDirector gameBuilderDirector;
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

  private BaseGameServer(int port, String name) {
    super(port, name);
  }

  // DOUBLE-CHECKED LOCKING
  public static AbstractServer getInstance() {
    AbstractServer localRef = instance;
    if (localRef == null) {
      synchronized (DummyServer.class) {
        localRef = instance;
        if (localRef == null) {
          instance = localRef = new BaseGameServer(21375, "BaseGame");
        }
      }
    }
    return localRef;
  }

  protected void setGameBuilderDirector(GameBuilderDirector gBD) {
    this.gameBuilderDirector = gBD;
  }

  public static void removeClient(UUID uuid) {
    currentClients.remove(uuid);
    clients.remove(uuid);
  }

  public static void messageAllExcluding(Object msg, UUID toExclude)  {
    for (UUID key : clients.keySet()) {
      if(key == toExclude) continue;
      try {
        clients.get(key).writeOutput(msg);
      } catch(IOException e) {
        Logger.error(e.getMessage());
      }
    }
  }

  public static void sendCommandToPlayer(UUID uuid, Map<String, String> args, IModelCommands cmd) {
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(cmd, args);
    try {
      clients.get(uuid).writeOutput(msg.serialize());
    } catch(IOException e) {
      Logger.error(e.getMessage());
    }
  }

  public static void sendPlayerInfo(UUID uuid) {
    AbstractSocket client = clients.get(uuid);
    if(!(client instanceof BaseGameSocket)) return;
    Map<String, String> args = new HashMap<>();
    args.put("player", ObjectJSONSerializer.serialize(((BaseGameSocket) client).getPlayer()));
    sendCommandToPlayer(uuid, args, GameCommands.PLAYER_INFO);
  }

  public static void sendGame(UUID uuid) {
    Map<String, String> args = new HashMap<>();
    args.put("game", ObjectJSONSerializer.serialize(game));
    sendCommandToPlayer(uuid, args, GameCommands.START);
  }

  public static void sendAllPlayerInfo() {
    for(UUID uuid: clients.keySet()) {
      sendPlayerInfo(uuid);
    }
  }

  public static void sendAllGame() {
    for(UUID uuid: clients.keySet()) {
      sendGame(uuid);
    }
  }

  public static void sendAllNextPlayer() {
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(next));
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.NEXT, args);
    try {
      messageAll(msg.serialize());
    } catch(IOException e) {
      Logger.error(e.getMessage());
    }
  }

  public static int getClientPlayerIdByUUID(UUID uuid) {
      return ((BaseGameSocket) clients.get(uuid)).getPlayerId();
  }

  public static int getClientPlayerNameByUUID(UUID uuid) {
    return ((BaseGameSocket) clients.get(uuid)).getPlayerId();
  }

  public static Piece getPieceById(int id) {
    return game.getPieceById(id);
  }

  public static Square getSquareById(int id) {
    return game.getSquareById(id);
  }

  public static boolean movePiece(int pieceId, int destId) {
    Piece piece = getPieceById(pieceId);
    Square dest = getSquareById(destId);
    if(piece == null || dest == null) return false;
    return game.move(piece, dest);
  }

  public static boolean comparePieceOwnerAndClient(int pieceId, UUID client) {
    int owner = getPieceById(pieceId).getOwner().getId();
    return getClientPlayerIdByUUID(client) == owner;
  }

  public static void moveEvent() {
    moves++;
    UUID next_uuid = currentClients.get(moves % currentClients.size());
    next = getClientPlayerIdByUUID(next_uuid);
    Logger.debug("Current move: " + moves + " Player: " + getClientPlayerNameByUUID(next_uuid));
    sendAllNextPlayer();
  }

  @Override
  protected void setup() {
    setSocketBuilder(socketBuilder);
    setGameBuilderDirector(new GameBuilderDirector(new ClassicTrylmaBuilder()));
  }

  @Override
  public void listen() {
    while (running) {
      if (clients.size() == 6) { // Block connections above 6
        continue;
      }
      try {
        Socket clientSocket = serverSocket.accept();
        if (clients.size() == 0 && game != null) game = null; // Clear game, idk why
        if (!clientSocket.isClosed()) {
          AbstractSocket socket = createNewSocket(clientSocket);
          if(!(socket instanceof BaseGameSocket)) { // Wrong socket built
            socket.close();
            continue;
          }
          clients.put(socket.getUUID(), socket);
          Logger.debug("Current clients: " + clients.size());

          if(clients.size() == 1 && game == null) {
            shuffle(availablePlayerList); // No game was created, shuffle player queue, so we pick random players for new clients
            Logger.debug(availablePlayerList.toString());
          }
          ((BaseGameSocket) socket).setPlayer(availablePlayerList.get(clients.size() - 1));
          currentClients.add(socket.getUUID());

          if (clients.size() == 2 && game == null) { // If this is the 6th connection
            // Shuffle player queue
            shuffle(currentClients);
            // Set all players
            Player[] players = availablePlayerList.toArray(new Player[0]);
            gameBuilderDirector.setPlayers(players);
            next = getClientPlayerIdByUUID(currentClients.get(0));
            Logger.debug("Current move no.: " + moves + " Player: " + getClientPlayerNameByUUID(currentClients.get(0)));
            // Build and start a new game
            game = gameBuilderDirector.build();
            game.setCurrentPlayer(next);
            // Send game/player info to clients
            sendAllPlayerInfo();
            sendAllGame();
            sendAllNextPlayer();
          }
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
