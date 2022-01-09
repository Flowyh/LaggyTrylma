package com.laggytrylma.backend.sockets.basegame;

import com.fasterxml.jackson.databind.JsonNode;
import com.laggytrylma.backend.servers.basegame.BaseGameServerCommandsReciever;
import com.laggytrylma.backend.servers.basegame.commands.MessageAllExcluding;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.backend.servers.basegame.BaseGameServer;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.Map;
import java.util.UUID;

public class BaseGameSocketHandler extends AbstractCommandHandler {
  protected static BaseGameServer serv;

  public void bindServer(BaseGameServer s) {
    serv = s;
  }

  @Override
  public Object handleCommand(IModelCommands cmd, Map<String, String> args, Object o, UUID client) {
    Object result;
    switch (cmd.model()) {
      case "game" -> result = GameCommandHandler.handleCommand(cmd, args, client, o);
      case "client" -> result = ClientCommandHandler.handleCommand(cmd, args, client, o);
      case "lobby" -> result = LobbyCommandHandler.handleCommand(cmd, args, client, o);
      default ->  {
        Logger.error("Unexpected command model provided");
        result = 0;
      }
    }
    return result;
  }

  static class GameCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client, Object o) {
      int result;
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
          return moveHandler(args.get("piece"), args.get("destination"), client, o);
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

    static int moveHandler(String pieceJSON, String destJSON, UUID client, Object o) {
      JsonNode piece = (JsonNode) ObjectJSONSerializer.deserialize(pieceJSON, JsonNode.class);
      JsonNode dest = (JsonNode) ObjectJSONSerializer.deserialize(destJSON, JsonNode.class);
      if(piece == null || dest == null) return 0;
      int pieceId = Integer.parseInt(piece.get("id").toString());
      int destId = Integer.parseInt(dest.get("id").toString());
      if(serv.gameState.movePiece(pieceId, destId)) {
        serv.gameState.moveEvent();
        if(serv.gameState.comparePieceOwnerAndClient(pieceId, client)) {
          //BaseGameServer.messageAllExcluding(o, client);
          serv.cmdExecutor.executeCommand(new MessageAllExcluding(new BaseGameServerCommandsReciever(serv.getClients(), o, client)));
        }
        return 1;
      }
      return 0;
    }
  }

  // Placeholder
  static class ClientCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client, Object o) {
      return 0;
    }
  }

  // Placeholder
  static class LobbyCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client, Object o) {
      return 0;
    }
  }
}
