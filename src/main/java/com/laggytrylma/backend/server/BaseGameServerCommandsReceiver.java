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

/**
 * Receiver for GoF's Command pattern.
 * Here lies all the logic which is needed for a successful command pattern execution.
 */
public class BaseGameServerCommandsReceiver {
  /**
   * Command client pool.
   */
  private final Map<UUID, BaseGameSocket> clients;
  /**
   * Client's uuid.
   */
  private UUID uuid;
  /**
   * Object to be sent over the socket.
   */
  private Object msg;
  /**
   * Map of arguments for cmd wrapping.
   */
  private Map<String, String> args;
  /**
   * Current command IModelCommands type.
   */
  private IModelCommands cmd;
  /**
   * Client's game object.
   */
  private Game game;
  /**
   * Client's player object.
   */
  private Player player;

  /**
   * Class constructor
   * @param clients command client pool
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients) {
    this.clients = clients;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param msg object to be sent over the socket
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg) {
    this.clients = clients;
    this.msg = msg;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param uuid client's uuid
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid) {
    this.clients = clients;
    this.uuid = uuid;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param game client's game object
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Game game) {
    this.clients = clients;
    this.game = game;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param player client's player object
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Player player) {
    this.clients = clients;
    this.player = player;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param msg object to be sent over the socket
   * @param uuid client's uuid
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg, UUID uuid) {
    this.clients = clients;
    this.msg = msg;
    this.uuid = uuid;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param uuid client's uuid
   * @param game client's game object
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid, Game game) {
    this.clients = clients;
    this.uuid = uuid;
    this.game = game;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param uuid client's uuid
   * @param cmd current command IModelCommands type
   * @param args map of arguments for cmd wrapping
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, UUID uuid, IModelCommands cmd, Map<String, String> args) {
    this.clients = clients;
    this.uuid = uuid;
    this.cmd = cmd;
    this.args = args;
  }

  /**
   * Class constructor
   * @param clients command client pool
   * @param msg object to be sent over the socket
   * @param uuid client's uuid
   * @param args map of arguments for cmd wrapping
   */
  public BaseGameServerCommandsReceiver(Map<UUID, BaseGameSocket> clients, Object msg, UUID uuid, Map<String, String> args) {
    this.clients = clients;
    this.msg = msg;
    this.uuid = uuid;
    this.args = args;
  }



  /**
   * Message all client's command logic.
   * Requires: clients, msg fields
   * @return int execution status
   */
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

  /**
   * Message all clients excluding one command logic.
   * Requires: clients, uuid, msg fields
   * @return int execution status
   */
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

  /**
   * Send a specific command to given client command logic.
   * Requires: clients, uuid, cmd, args fields
   * @return int execution status
   */
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

  /**
   * Send serialized player object to given player command logic.
   * Requires: clients, uuid fields
   * @return int execution status
   */
  public int sendPlayerInfo() {
    BaseGameSocket client = clients.get(uuid);
    if(client == null) return -1;
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(client.getPlayer().getId()));
    this.args = args;
    this.cmd = GameCommands.PLAYER_INFO;
    return sendCommandToPlayer();
  }

  /**
   * Send serialized game object to a given player command logic.
   * Requires: clients, uuid, game fields
   * @return int execution status
   */
  public int sendGame() {
    Map<String, String> args = new HashMap<>();
    args.put("game", ObjectJSONSerializer.serialize(game));
    this.args = args;
    this.cmd = GameCommands.START;
    return sendCommandToPlayer();
  }

  /**
   * Send all clients their serialized player objects command logic.
   * Requires: clients fields
   * @return int execution status
   */
  public int sendAllPlayerInfo() {
    for(UUID uuid: clients.keySet()) {
      this.uuid = uuid;
      sendPlayerInfo();
    }
    return 1;
  }

  /**
   * Send all clients serialized game object command logic.
   * Requires: clients, game fields
   * @return int execution status
   */
  public int sendAllGame() {
    for(UUID uuid: clients.keySet()) {
      this.uuid = uuid;
      sendGame();
    }
    return 1;
  }

  /**
   * Send all clients who is the next player in given game command logic.
   * Requires: clients, game fields
   * @return int execution status
   */
  public int sendAllNextPlayer() {
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(this.game.getCurrentPlayer().getId()));
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.NEXT, args);
    this.msg = msg.serialize();
    messageAll();
    return 1;
  }

  /**
   * Send all clients who is the game winner command logic.
   * Requires: clients, player fields
   * @return int execution status
   */
  public int sendAllWinner() {
    Map<String, String> args = new HashMap<>();
    args.put("player", Integer.toString(this.player.getId()));
    JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.WIN, args);
    this.msg = msg.serialize();
    messageAll();
    return 1;
  }
}
