//package com.laggytrylma.backend.servers.basegame;
//
//import com.laggytrylma.backend.ctx.AbstractServer;
//import com.laggytrylma.backend.ctx.AbstractSocket;
//import com.laggytrylma.backend.ctx.AbstractSocketBuilder;
//import com.laggytrylma.backend.servers.dummy.DummyServer;
//import com.laggytrylma.backend.sockets.basegame.BaseGameSocket;
//import com.laggytrylma.backend.sockets.basegame.BaseGameSocketBuilder;
//import com.laggytrylma.common.*;
//import com.laggytrylma.utils.Logger;
//import com.laggytrylma.utils.communication.commands.models.GameCommands;
//import com.laggytrylma.utils.communication.commands.models.IModelCommands;
//import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
//
//import java.awt.*;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.*;
//
//import static java.util.Collections.shuffle;
//
//public class BaseGameServer extends AbstractServer {
//  private static AbstractServer instance = new BaseGameServer(21375, "BaseGame");
//  private final AbstractSocketBuilder socketBuilder = new BaseGameSocketBuilder();
//  private GameBuilderDirector gameBuilderDirector;
//  private static Game game = null;
//  public static int moves = 0;
//  public static UUID next = null;
//  private final Random R = new Random();
//  private static final ArrayList<Player> availablePlayerList = new ArrayList<>(Arrays.asList(
//    new Player(1, "1", new Color(0,0,0)),
//    new Player(2, "2", new Color(100,0,0)),
//    new Player(3, "3", new Color(0,100,0)),
//    new Player(4, "4", new Color(0,0,100)),
//    new Player(5, "5", new Color(0,100,100)),
//    new Player(6, "6", new Color(100,100,0))
//  ));
//  private static final ArrayList<UUID> currentClients = new ArrayList<>();
//
//  private BaseGameServer(int port, String name) {
//    super(port, name);
//  }
//
//  // DOUBLE-CHECKED LOCKING
//  public static AbstractServer getInstance() {
//    AbstractServer localRef = instance;
//    if (localRef == null) {
//      synchronized (DummyServer.class) {
//        localRef = instance;
//        if (localRef == null) {
//          instance = localRef = new BaseGameServer(21375, "BaseGame");
//        }
//      }
//    }
//    return localRef;
//  }
//
//  protected void setGameBuilderDirector(GameBuilderDirector gBD) {
//    this.gameBuilderDirector = gBD;
//  }
//
//  public static void removeClient(UUID uuid) {
//    availablePlayerList.add(((BaseGameSocket) clients.get(uuid)).getPlayer());
//    currentClients.remove(uuid);
//    clients.remove(uuid);
//  }
//
//  public static void sendCommandToPlayer(UUID uuid, Map<String, String> args, IModelCommands cmd) {
//    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(cmd, args);
//    try {
//      clients.get(uuid).writeOutput(msg.serialize());
//    } catch(IOException e) {
//      Logger.error(e.getMessage());
//    }
//  }
//
//  public static void sendPlayerInfo(UUID uuid) {
//    AbstractSocket client = clients.get(uuid);
//    Map<String, String> args = new HashMap<>();
//    args.put("player", ((BaseGameSocket) client).getPlayer().toJSON());
//    sendCommandToPlayer(uuid, args, GameCommands.INFO);
//  }
//
//  public static void sendGame(UUID uuid) {
//    AbstractSocket client = clients.get(uuid);
//    Map<String, String> args = new HashMap<>();
//    args.put("game", game.toJSON());
//    sendCommandToPlayer(uuid, args, GameCommands.START);
//  }
//
//  public static void sendAllPlayerInfo() {
//    for(UUID uuid: clients.keySet()) {
//      sendPlayerInfo(uuid);
//    }
//  }
//
//  public static void sendAllGame() {
//    for(UUID uuid: clients.keySet()) {
//      sendGame(uuid);
//    }
//  }
//
//  @Override
//  protected void setup() {
//    setSocketBuilder(socketBuilder);
//    setGameBuilderDirector(new BaseGameBuilderDirector(new ClassicTrylmaBuilder()));
//  }
//
//  @Override
//  public void listen() {
//    while (running) {
//      if (clients.size() == 6) {
//        continue;
//      }
//      try {
//        Socket clientSocket = serverSocket.accept();
//        if (clients.size() == 0 && game != null) game = null; // Clear game, idk why
//        if (!clientSocket.isClosed()) {
//          AbstractSocket socket = createNewSocket(clientSocket);
//
//          int randomPlayer = R.nextInt(availablePlayerList.size());
//          ((BaseGameSocket) socket).setPlayer(availablePlayerList.get(randomPlayer));
//          availablePlayerList.remove(randomPlayer);
//
//          currentClients.add(socket.getUUID());
//          if (clients.size() == 5 && game == null) { // If this is the 6th connection
//            // Set all players
//            ((BaseGameBuilderDirector) gameBuilderDirector).setPlayers((Player[]) availablePlayerList.toArray());
//            // Shuffle players queue
//            shuffle(currentClients);
//            next = currentClients.get(0);
//            Logger.debug("Current move: " + moves + " Player: " + next);
//            // Build and start a new game
//            game = gameBuilderDirector.build();
//            // Send game/player info to clients
//            sendAllPlayerInfo();
//            sendAllGame();
//          }
//          clients.put(socket.getUUID(), socket);
//          Logger.debug("Current clients: " + clients.size());
//          Logger.debug("Current player: " + ((BaseGameSocket) socket).getPlayerString());
//          threadPool.execute(socket);
//        }
//      } catch (IOException ioException) {
//        Logger.error(ioException.getMessage());
//      }
//    }
//  }
//
//  public static void moveEvent() {
//    moves++;
//    next = currentClients.get(moves % currentClients.size());
//    Logger.debug("Current move: " + moves + " Player: " + next);
//  }
//}
