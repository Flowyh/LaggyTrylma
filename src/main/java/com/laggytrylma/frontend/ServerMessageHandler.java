package com.laggytrylma.frontend;

import com.fasterxml.jackson.databind.JsonNode;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.Map;
import java.util.UUID;

public class ServerMessageHandler extends AbstractCommandHandler {
  private static TestServerUI UI;

  public void bindUI(TestServerUI ui) {
    UI = ui;
  }

  @Override
  public Object handleCommand(IModelCommands cmd, Map<String, String> args, Object o, UUID client) {
    switch (cmd.model()) {
      case "game" -> {
        return GameCommandHandler.handleCommand(cmd, args, client, o);
      }
      case "client" -> {
        return  ClientCommandHandler.handleCommand(cmd, args, client, o);
      }
      case "lobby" -> {
        return  LobbyCommandHandler.handleCommand(cmd, args, client, o);
      }
      default ->  {
        Logger.error("Unexpected command model provided");
        return 0;
      }
    }
  }

  static class GameCommandHandler {
    static Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client, Object o) {
      Object result;
      switch (cmd.command()) {
        case "start" -> {
          return startHandler(args.get("game"));
        }
        case "next" -> {
          return nextHandler(args.get("player"));
        }
        case "move" -> {
          return moveHandler(args.get("piece"), args.get("destination"));
        }
        case "player_info" -> {
          return playerInfoHandler(args.get("player"));
        }
        case "game_info" -> {
          return 6;
        }
        default -> {
          return 0;
        }
      }
    }

    // Message from server
    static int moveHandler(String pieceJSON, String destJSON) {
      JsonNode piece = (JsonNode) ObjectJSONSerializer.deserialize(pieceJSON, JsonNode.class);
      JsonNode dest = (JsonNode) ObjectJSONSerializer.deserialize(destJSON, JsonNode.class);
      if(piece == null || dest == null) return -1;
      Piece pieceId = UI.game.getPieceById(Integer.parseInt(piece.get("id").toString()));
      Square destId = UI.game.getSquareById(Integer.parseInt(dest.get("id").toString()));
      UI.game.move(pieceId, destId); // The move SHOULD be legal if it were sent by server
      return 1;
    }

    static int startHandler(String gameJSON) {
      Game deserialized = (Game) ObjectJSONSerializer.deserialize(gameJSON, Game.class);
      if(deserialized != null) {
        UI.setGame(deserialized);
        UI.attachGameToDisplay();
        return 1;
      }
      return -1;
    }

    static int playerInfoHandler(String playerJSON) {
      Player deserialized = (Player) ObjectJSONSerializer.deserialize(playerJSON, Player.class);
      if(deserialized != null) {
        UI.setPlayer(deserialized);
        return 1;
      }
      return -1;
    }

    static int nextHandler(String playerJSON) {
      if(playerJSON == null) return -1;
      int nextPlayer = Integer.parseInt(playerJSON);
      UI.game.setCurrentPlayer(nextPlayer);
      return 1;
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
