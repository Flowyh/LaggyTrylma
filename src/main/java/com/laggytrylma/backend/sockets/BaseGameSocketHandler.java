package com.laggytrylma.backend.sockets;

import com.laggytrylma.backend.server.*;
import com.laggytrylma.backend.server.commands.*;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.utils.communication.commands.models.ClientCommands;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class for handling all the messages incoming from client connection sockets.
 */
public class BaseGameSocketHandler extends AbstractCommandHandler {
  /**
   * Bound BaseGameServer instance.
   */
  protected static BaseGameServer serv;
  /**
   * Bound BaseGameServerCommandsExecutor instance.
   */
  private static BaseGameServerCommandsExecutor cmdExecutor;
  /**
   * Bound BaseGameLobbyManager instance.
   */
  private static BaseGameLobbyManager lobbyManager;

  /**
   * Bind BaseGameServer and it's associated BaseGameServerCommandsExecutor and BaseGameLobbyManager instances.
   * @param s BaseGameServer instance.
   */
  public void bindServer(BaseGameServer s) {
    serv = s;
    cmdExecutor = s.cmdExecutor;
    lobbyManager = s.lobbyManager;
  }

  /**
   * Handle given command.
   * It should be properly deserialized first in processInput method ({@link com.laggytrylma.utils.communication.commands.AbstractCommandHandler#processInput(Object, UUID) See here})
   * Then the proper command handler is determined by command's IModelCommands type, given arguments and issuer uuid.
   * @param cmd IModelCommands type
   * @param args Mapped command arguments to String keys and JSONs Strings.
   * @param client issuer uuid
   * @return Object command response
   */
  @Override
  public Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
    Object result;
    switch (cmd.model()) {
      case "game" -> result = GameCommandHandler.handleCommand(cmd, args, client);
      case "client" -> result = ClientCommandHandler.handleCommand(cmd, args, client);
      case "lobby" -> result = LobbyCommandHandler.handleCommand(cmd, args, client);
      default -> {
        Logger.error("Unexpected command model provided");
        result = 0;
      }
    }
    return result;
  }

  /**
   * Class for handling commands associated with Game model.
   */
  private static class GameCommandHandler {
    /**
     * Handle Game commands.
     * @param cmd IModelCommands type
     * @param args Mapped command arguments to String keys and JSONs Strings
     * @param client issuer uuid
     * @return int execution status
     */
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      JSONCommandWrapper<?> cmdWrap = new JSONCommandWrapper<>(cmd, args);
      switch ((GameCommands) cmd) {
        case PLAYER_INFO -> {
          return playerInfoHandler(client);
        }
        case START -> {
          return startHandler(client);
        }
        case MOVE -> {
          return moveHandler(args.get("piece"), args.get("destination"), client, cmdWrap.serialize());
        }
        default -> {
          return 0;
        }
      }
    }

    /**
     * Send player info to issuer.
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int playerInfoHandler(UUID client) {
      Map<UUID, BaseGameSocket> lobbyClients = lobbyManager.getClientsFromGameState(client);
      if (lobbyClients == null) return -1;
      cmdExecutor.executeCommand(new SendPlayerInfo(new BaseGameServerCommandsReceiver(lobbyClients, client)));
      return 1;
    }

    /**
     * Start's game in issuer's lobby.
     * @param client issuer's uuid.
     * @return int execution status
     */
    private static int startHandler(UUID client) {
      lobbyManager.startGame(client);
      return 1;
    }

    /**
     * Move piece in given lobby.
     * Checks whether the move is legal and the piece was actually owned by the issuer.
     * @param pieceJSON JSON String holding piece's info
     * @param destJSON JSON String holding destination square's info
     * @param client issuer's uuid
     * @param res Response object to be send to all the clients if the move was successful
     * @return int execution status
     */
    private static int moveHandler(String pieceJSON, String destJSON, UUID client, String res) {
      int pieceId = Integer.parseInt(pieceJSON);
      int destId = Integer.parseInt(destJSON);
      BaseGameState gameState = lobbyManager.getGameStateByClient(client);
      Map<UUID, BaseGameSocket> lobbyClients = lobbyManager.getClientsFromGameState(client);
      if (gameState.comparePieceOwnerAndClient(pieceId, client)) {
        if (gameState.movePiece(pieceId, destId)) {
          Player winner = gameState.getGame().getWinner();
          if(winner != null) {
            serv.cmdExecutor.executeCommand(new SendAllWinner(new BaseGameServerCommandsReceiver(lobbyClients, winner)));
            lobbyManager.removeClient(client);
            return 2;
          }
          cmdExecutor.executeCommand(new MessageAllExcluding(new BaseGameServerCommandsReceiver(lobbyClients, res, client)));
          return 1;
        }
      }
      return 0;
    }
  }

  /**
   * Class for handling commands associated with Client model.
   */
  private static class ClientCommandHandler {
    /**
     * Handle Client commands.
     * @param cmd IModelCommands type
     * @param args Mapped command arguments to String keys and JSONs Strings
     * @param client issuer uuid
     * @return int execution status
     */
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch ((ClientCommands) cmd) {
        case NICKNAME -> {
          return nicknameHandler(args.get("nickname"), client);
        }
        default -> {
          return 0;
        }
      }
    }

    /**
     * Set client's nickname.
     * @param nickname String new nickname
     * @param client issuer uuid
     * @return int execution status
     */
    private static int nicknameHandler(String nickname, UUID client) {
      serv.getPlayerByUUID(client).setName(nickname);
      return 1;
    }
  }

  /**
   * Class for handling commands associated with Lobby model.
   */
  private static class LobbyCommandHandler {
    /**
     * Handle Lobby commands.
     * @param cmd IModelCommands type
     * @param args Mapped command arguments to String keys and JSONs Strings
     * @param client issuer uuid
     * @return int execution status
     */
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch ((LobbyCommands) cmd) {
        case CREATE -> {
          return createHandler(client);
        }
        case RULES -> {
          return 2;
        }
        case INFO -> {
          return infoHandler(args.get("id"), client);
        }
        case JOIN -> {
          return joinHandler(args.get("id"), client);
        }
        case DELETE -> {
          return deleteHandler(args.get("id"), client);
        }
        case LEAVE -> {
          return leaveHandler(client);
        }
        case PLAYER_LIMIT -> {
          return playerLimitHandler(args.get("player_limit"), client);
        }
        case LIST_ALL -> {
          return listAllHandler(client);
        }
        case LIST_ARCHIVE -> {
          return listArchiveHandler(client);
        }
        case GET_ARCHIVED_GAME -> {
          return getArchivedGameHandler(args.get("id"), client);
        }
        default -> {
          return 0;
        }
      }
    }

    /**
     * Create new lobby.
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int createHandler(UUID client) {
      int newLobbyId = lobbyManager.createNewLobby(client);
      lobbyManager.addNewClient(newLobbyId, client);
      Map<String, String> args = new HashMap<>();
      args.put("id", Integer.toString(newLobbyId));
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.CREATE, args)));
    }

    /**
     * Send info of a given lobby to the issuer.
     * If no id was specified we determine the lobby by issuer's uuid.
     * If both didn't yield a proper lobby id, the command cannot be executed.
     * @param id lobby id
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int infoHandler(String id, UUID client) {
      int lobbyId = id == null ? lobbyManager.getLobbyIdByClient(client) : Integer.parseInt(id);
      if(lobbyId == -1) return -1;
      Map<String, String> args = new HashMap<>();
      args.put(Integer.toString(lobbyId), lobbyManager.getLobbyInfo(lobbyId));
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.INFO, args)));
    }

    /**
     * Add new client to the lobby.
     * @param id lobby id
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int joinHandler(String id, UUID client) {
      lobbyManager.addNewClient(Integer.parseInt(id), client);
      return 1;
    }

    /**
     * Delete lobby by given id.
     * If issuer isn't the lobby owner, the command cannot be executed.
     * @param id lobby id
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int deleteHandler(String id, UUID client) {
      int lobbyId = Integer.parseInt(id);
      if(lobbyManager.getGameOwnerById(lobbyId) != client) return -1;
      lobbyManager.removeLobby(lobbyId, client);
      return 1;
    }

    /**
     * Remove issuer's uuid from their lobby on leave.
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int leaveHandler(UUID client) {
      lobbyManager.removeClient(client);
      return 1;
    }

    /**
     * Set new player limit.
     * @param player_limit new player limit
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int playerLimitHandler(String player_limit, UUID client) {
      BaseGameState gameState = lobbyManager.getGameStateByClient(client);
      if(!gameState.isOwner(client)) return -1;
      gameState.setPlayerLimit(Integer.parseInt(player_limit));
      return 1;
    }

    /**
     * Send info about all current lobbies to the issuer.
     * @param client issuer's uuid
     * @return int execution status
     */
    private static int listAllHandler(UUID client) {
      Map<String, String> args = lobbyManager.getAllLobbies();
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.LIST_ALL, args)));
    }

    private static int listArchiveHandler(UUID client) {
      Map<String, String> args = serv.repoWrapper.getArchivedIdsDates();
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.LIST_ARCHIVE, args)));
    }

    private static int getArchivedGameHandler(String id, UUID client) {
      return cmdExecutor.executeCommand(new SendArchivedGame(new BaseGameServerCommandsReceiver(serv.getClients(), client, serv.repoWrapper.getGameById(id))));
    }
  }
}
