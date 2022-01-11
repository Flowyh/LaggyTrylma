package com.laggytrylma.backend.sockets;

import com.laggytrylma.backend.server.*;
import com.laggytrylma.backend.server.commands.*;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseGameSocketHandler extends AbstractCommandHandler {
  protected static BaseGameServer serv;
  private static BaseGameServerCommandsExecutor cmdExecutor;
  private static BaseGameLobbyManager lobbyManager;

  public void bindServer(BaseGameServer s) {
    serv = s;
    cmdExecutor = s.cmdExecutor;
    lobbyManager = s.lobbyManager;
  }

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

  private static class GameCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      JSONCommandWrapper<?> cmdWrap = new JSONCommandWrapper<>(cmd, args);
      switch (cmd.command()) {
        case "player_info" -> {
          return playerInfoHandler(client);
        }
        case "start" -> {
          return startHandler(client);
        }
        case "move" -> {
          return moveHandler(args.get("piece"), args.get("destination"), client, cmdWrap.serialize());
        }
        default -> {
          return 0;
        }
      }
    }

    private static int playerInfoHandler(UUID client) {
      Map<UUID, BaseGameSocket> lobbyClients = lobbyManager.getClientsFromGameState(client);
      if (lobbyClients == null) return -1;
      cmdExecutor.executeCommand(new SendPlayerInfo(new BaseGameServerCommandsReceiver(lobbyClients, client)));
      return 1;
    }

    private static int startHandler(UUID client) {
      lobbyManager.startGame(client);
      return 1;
    }

    private static int moveHandler(String pieceJSON, String destJSON, UUID client, String res) {
      int pieceId = Integer.parseInt(pieceJSON);
      int destId = Integer.parseInt(destJSON);
      BaseGameState gameState = lobbyManager.getGameStateByClient(client);
      Map<UUID, BaseGameSocket> lobbyClients = lobbyManager.getClientsFromGameState(client);
      if (gameState.comparePieceOwnerAndClient(pieceId, client)) {
        if (gameState.movePiece(pieceId, destId)) {
          cmdExecutor.executeCommand(new MessageAllExcluding(new BaseGameServerCommandsReceiver(lobbyClients, res, client)));
          return 1;
        }
      }
      return 0;
    }
  }

  private static class ClientCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch (cmd.command()) {
        case "nickname" -> {
          return nicknameHandler(args.get("nickname"), client);
        }
        default -> {
          return 0;
        }
      }
    }

    private static int nicknameHandler(String nickname, UUID client) {
      serv.getPlayerByUUID(client).setName(nickname);
      return 1;
    }
  }

  private static class LobbyCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch (cmd.command()) {
        case "create" -> {
          return createHandler(client);
        }
        case "rules" -> {
          return 2;
        }
        case "info" -> {
          return infoHandler(args.get("id"), client);
        }
        case "join" -> {
          return joinHandler(args.get("id"), client);
        }
        case "delete" -> {
          return deleteHandler(args.get("id"), client);
        }
        case "leave" -> {
          return leaveHandler(client);
        }
        case "player_limit" -> {
          return playerLimitHandler(args.get("player_limit"), client);
        }
        case "list_all" -> {
          return listAllHandler(client);
        }
        default -> {
          return 0;
        }
      }
    }

    private static int createHandler(UUID client) {
      int newLobbyId = lobbyManager.createNewLobby(client);
      lobbyManager.addNewClient(newLobbyId, client);
      Map<String, String> args = new HashMap<>();
      args.put("id", Integer.toString(newLobbyId));
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.CREATE, args)));
    }

    private static int infoHandler(String id, UUID client) {
      int lobbyId = id == null ? lobbyManager.getLobbyIdByClient(client) : Integer.parseInt(id);
      if(lobbyId == -1) return -1;
      Map<String, String> args = new HashMap<>();
      args.put(Integer.toString(lobbyId), lobbyManager.getLobbyInfo(lobbyId));
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.INFO, args)));
    }

    private static int joinHandler(String id, UUID client) {
      lobbyManager.addNewClient(Integer.parseInt(id), client);
      return 1;
    }

    private static int deleteHandler(String id, UUID client) {
      int lobbyId = Integer.parseInt(id);
      lobbyManager.removeLobby(lobbyId, client);
      return 1;
    }

    private static int leaveHandler(UUID client) {
      lobbyManager.removeClient(client);
      return 1;
    }

    private static int playerLimitHandler(String player_limit, UUID client) {
      BaseGameState gameState = lobbyManager.getGameStateByClient(client);
      if(!gameState.isOwner(client)) return -1;
      gameState.setPlayerLimit(Integer.parseInt(player_limit));
      return 1;
    }

    static int listAllHandler(UUID client) {
      Map<String, String> args = lobbyManager.getAllLobbies();
      return cmdExecutor.executeCommand(new SendCommandToPlayer(new BaseGameServerCommandsReceiver(serv.getClients(), client, LobbyCommands.LIST_ALL, args)));
    }
  }
}
