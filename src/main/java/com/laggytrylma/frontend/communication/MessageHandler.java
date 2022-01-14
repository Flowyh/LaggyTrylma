package com.laggytrylma.frontend.communication;

import com.fasterxml.jackson.databind.JsonNode;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.frontend.managers.GameManager;
import com.laggytrylma.frontend.managers.LobbyManager;
import com.laggytrylma.frontend.states.Context;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MessageHandler extends AbstractCommandHandler {
  static private GameManager gm;
  static private Game game;
  static private LobbyManager lm;
  static private Context ctx;

  public MessageHandler(Context ct, GameManager g, LobbyManager l){
    ctx = ct;
    gm = g;
    lm = l;
  }

  @Override
  public Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
    switch (cmd.model()) {
      case "game" -> {
        return GameCommandHandler.handleCommand(cmd, args, client);
      }
      case "client" -> {
        return  ClientCommandHandler.handleCommand(cmd, args, client);
      }
      case "lobby" -> {
        return  LobbyCommandHandler.handleCommand(cmd, args, client);
      }
      default ->  {
        Logger.error("Unexpected command model provided");
        return 0;
      }
    }
  }

  static class GameCommandHandler {
   static Object handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch (cmd.command()) {
        case "start" -> {
          return start(args.get("game"));
        }
        case "next" -> {
          return next(args.get("player"));
        }
        case "move" -> {
          return move(args.get("piece"), args.get("destination"));
        }
        case "player_info" -> {
          return player(args.get("player"));
        }
        case "game_info" -> {
          return 6;
        }
        case "win" -> {
          return win(args.get("player"));
        }
        default -> {
          return 0;
        }
      }
    }

    private static int win(String player) {
      int playerId = Integer.parseInt(player);
      gm.win(playerId);
      ctx.leave();
      return 0;
    }

    // Message from server
    static int move(String pieceJSON, String destJSON) {
      if(pieceJSON == null || destJSON == null) return -1;
      int pieceId = Integer.parseInt(pieceJSON);
      int destId = Integer.parseInt(destJSON);

      gm.remoteMove(pieceId, destId);
      return 1;
    }

    static int start(String gameJSON) {
      Logger.debug("Received the serialized Game!");
      Game deserialized = (Game) ObjectJSONSerializer.deserialize(gameJSON, Game.class);
      if(deserialized != null) {
        game = deserialized;
        gm.startGame(game);
        return 1;
      }
      return -1;
    }

    static int player(String playerJSON) {
     if(playerJSON == null){
       return -1;
     }
     int playerId = Integer.parseInt(playerJSON);

     // TODO: error handling
     gm.assignPlayer(playerId);
     return 1;
    }

    static int next(String playerJSON) {
      if(playerJSON == null) return -1;
      int nextPlayer = Integer.parseInt(playerJSON);
      gm.setCurrentPlayer(nextPlayer);
      return 1;
    }
  }

  static class ClientCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      return 0;
    }
  }


  static class LobbyCommandHandler {
    static int handleCommand(IModelCommands cmd, Map<String, String> args, UUID client) {
      switch (cmd.command()) {
        case "list_all" -> {
          return list_all(args);
        }
        case "delete" -> {
          return delete();
        }
        default -> {return 0;}
      }
    }

    private static int delete() {
      ctx.leave();
      return 0;
    }

    private static int list_all(Map<String, String> args) {
      List<LobbyDescriptor> lobbies = new LinkedList<>();
        for(Map.Entry<String, String> entry : args.entrySet()){
          int id = Integer.parseInt(entry.getKey());
          JsonNode node = (JsonNode) ObjectJSONSerializer.deserialize(entry.getValue(), JsonNode.class);
          if(node == null){
            continue;
          }
          String owner = node.get("owner").asText();
          int playersCount = node.get("players").asInt();

          lobbies.add(new LobbyDescriptor(id, owner, playersCount));
        }
        lm.lobbiesList(lobbies);
        return 0;
    }
  }
}
