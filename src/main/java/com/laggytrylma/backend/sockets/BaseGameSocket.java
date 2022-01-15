package com.laggytrylma.backend.sockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Class for handling client connections to the server.
 * It is instantiated for every new client connection to BaseGameServer.
 * Holds a Player object which is used in other parts of the application.
 * Has implemented listen/setup/close methods form AbstractSocket class.
 */
public class BaseGameSocket extends AbstractSocket {
  /**
   * Client's Player object.
   */
  protected Player player = null;
  /**
   * Bound BaseGameServer instance.
   */
  public BaseGameServer serv;

  /**
   * Class constructor. Calls AbstractSocket constructor.
   * @param socket client's connection socket.
   */
  public BaseGameSocket(Socket socket) {
    super(socket);
  }

  /**
   * Bind server to this instance.
   * Also binds the server to BaseGameSocket's socket handler.
   * @param serv BaseGameServer instance.
   */
  public void bindServer(BaseGameServer serv) {
    this.serv = serv;
    ((BaseGameSocketHandler) this.socketHandler).bindServer(serv);
  }

  /**
   * Sets Player object for this instance.
   * @param p Player object
   */
  public void setPlayer(Player p) {
    this.player = p;
  }

  /**
   * Get Player object from this instance.
   * @return Player object
   */
  public Player getPlayer() { return player; }

  /**
   * Get Player string from this object.
   * @return Player info to String
   */
  public String getPlayerString() {
    String color;
    if(player.getColor() == null){
      color = "None";
    } else{
      color = player.getColor().toString();
    }
    return "Player: " + this.player.getName() + " Color: " + color;
  }

  /**
   * Setups socket info.
   */
  @Override
  public void setup() {
    Logger.info(Logger.logTime() + this.getUUID() + " joined!");
  }

  /**
   * Get player's id from Player object.
   * @return int player's id
   */
  public int getPlayerId() {
    return player.getId();
  }

  /**
   * Get player's name from Player object.
   * @return String player's name
   */
  public String getPlayerName() {
    return player.getName();
  }

  /**
   * Check whether given string is a valid JSON (using Jackson).
   * @param json JSON as a String.
   * @return True if given String is a valid JSON, False if otherwise.
   */
  private boolean isJSONValid(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(json);
      return true;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  /**
   * Listen loop for client's connection socket.
   */
  @Override
  public void listen() {
    JSONCommandWrapper<?> reqJSON = null;
    while (true) {
      try {
        Object o = readInput();
        if(!(o instanceof String)) continue;
        if(!isJSONValid((String) o)) continue; // Skip if invalid json was sent
        reqJSON = new JSONCommandWrapper<>((String) o);
        Object res = this.socketHandler.processInput(reqJSON, this.getUUID());
        if(res.equals(-1)) break;
      } catch(SocketTimeoutException ignored) {
      } catch(EOFException e) { // Socket closing
        break;
      } catch (IOException | ClassNotFoundException e) {
        Logger.error(e.getMessage());
        break;
      }
      if(reqJSON != null) Logger.debug("Incoming command: " + reqJSON);
    }
    close();
  }


  /**
   * Closes socket connection. Removes client from server/lobby manager.
   */
  @Override
  public void close() {
    Logger.debug("My close");
    try{
      super.close();
    } catch (IOException e) {
      Logger.error(e.getMessage());
    }

    serv.lobbyManager.removeClient(getUUID());
    serv.removeClient(getUUID());
  }
}
