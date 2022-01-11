package com.laggytrylma.backend.server;

import com.laggytrylma.backend.sockets.BaseGameSocket;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseGameServerCommandsReceiver {
  private final Map<UUID, BaseGameSocket> clients;
  private UUID uuid;
  private Object msg;
  private Map<String, String> args;
  private IModelCommands cmd;
  private Game game;
  private Player player;

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients) {
    this.clients = clients;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg) {
    this.clients = clients;
    this.msg = msg;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid) {
    this.clients = clients;
    this.uuid = uuid;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Game game) {
    this.clients = clients;
    this.game = game;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Player player) {
    this.clients = clients;
    this.player = player;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg, UUID uuid) {
    this.clients = clients;
    this.msg = msg;
    this.uuid = uuid;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid, Game game) {
    this.clients = clients;
    this.uuid = uuid;
    this.game = game;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid, IModelCommands cmd, Map<String, String> args) {
    this.clients = clients;
    this.uuid = uuid;
    this.cmd = cmd;
    this.args = args;
  }

  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg, UUID uuid, Map<String, String> args) {
    this.clients = clients;
    this.msg = msg;
    this.uuid = uuid;
    this.args = args;
  }

  // clients, msg
  public int messageAll() {
    for (UUID key : clients.keySet()) {
      try {
        clients.get(key).writeOutput(msg);
      } catch(IOException e) {
        Logger.error(e.getMessage());
      }
    }
    return 1;
  }

  // clients, uuid, msg
  public int messageAllExcluding() {
    for (UUID key : clients.keySet()) {
      if(key == uuid) continue;
      try {
        clients.get(key).writeOutput(msg);
      } catch(IOException e) {
        Logger.error(e.getMessage());
      }
    }
    return 1;
  }

  // clients, uuid, cmd, args
  public int sendCommandToPlayer() {
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(cmd, args);
    try {
      clients.get(uuid).writeOutput(msg.serialize());
    } catch(IOException e) {
      Logger.error(e.getMessage());
      return -1;
    }
    return 1;
  }

  // clients, uuid
  public int sendPlayerInfo() {
    BaseGameSocket client = clients.get(uuid);
    if(client == null) return -1;
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(client.getPlayer().getId()));
    this.args = args;
    this.cmd = GameCommands.PLAYER_INFO;
    return sendCommandToPlayer();
  }

  // clients, uuid, game
  public int sendGame() {
    Map<String, String> args = new HashMap<>();
    args.put("game", ObjectJSONSerializer.serialize(game));
    this.args = args;
    this.cmd = GameCommands.START;
    return sendCommandToPlayer();
  }

  // clients
  public int sendAllPlayerInfo() {
    for(UUID uuid: clients.keySet()) {
      this.uuid = uuid;
      sendPlayerInfo();
    }
    return 1;
  }

  // clients, game
  public int sendAllGame() {
    for(UUID uuid: clients.keySet()) {
      this.uuid = uuid;
      sendGame();
    }
    return 1;
  }

  // clients, game
  public int sendAllNextPlayer() {
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(this.game.getCurrentPlayer().getId()));
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.NEXT, args);
    this.msg = msg.serialize();
    messageAll();
    return 1;
  }

  // clients, player
  public int sendAllWinner() {
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(this.player.getId()));
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.WIN, args);
    this.msg = msg.serialize();
    messageAll();
    return 1;
  }
}
