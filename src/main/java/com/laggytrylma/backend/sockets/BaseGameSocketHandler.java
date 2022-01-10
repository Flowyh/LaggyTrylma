package com.laggytrylma.backend.sockets;

import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;
import com.laggytrylma.backend.server.commands.MessageAllExcluding;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.util.Map;
import java.util.UUID;

public class BaseGameSocketHandler extends AbstractCommandHandler {
  protected static BaseGameServer serv;

  public void bindServer(BaseGameServer s) {
    serv = s;
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

  static class GameCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      JSONCommandWrapper<?> cmdWrap = new JSONCommandWrapper<>(cmd, args);
      switch (cmd.command()) {
        case "setup" -> {
          return -1;
        }
        case "player_info" -> {
          return 1;
        }
        case "start" -> {
          return 3;
        }
        case "move" -> {
          return moveHandler(args.get("piece"), args.get("destination"), client, cmdWrap.serialize());
        }
        case "leave" -> {
          return 5;
        }
        case "delete" -> {
          return 6;
        }
        default -> {
          return 0;
        }
      }
    }

    static int moveHandler(String pieceJSON, String destJSON, UUID client, String res) {
      int pieceId = Integer.parseInt(pieceJSON);
      int destId = Integer.parseInt(destJSON);
      if(serv.gameState.movePiece(pieceId, destId)) {
        serv.gameState.moveEvent();
        if(serv.gameState.comparePieceOwnerAndClient(pieceId, client)) {
          serv.cmdExecutor.executeCommand(new MessageAllExcluding(new BaseGameServerCommandsReceiver(serv.getClients(), res, client)));
        }
        return 1;
      }
      return 0;
    }
  }

  // Placeholder
  static class ClientCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      return 0;
    }
  }

  // Placeholder
  static class LobbyCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      return 0;
    }
  }
}
