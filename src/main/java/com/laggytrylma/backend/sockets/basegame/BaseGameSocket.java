package com.laggytrylma.backend.sockets.basegame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.backend.ctx.AbstractSocket;
import com.laggytrylma.backend.servers.basegame.BaseGameServer;
import com.laggytrylma.common.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

public class BaseGameSocket extends AbstractSocket {
  protected Player player = null;

  public BaseGameSocket(Socket socket) {
    super(socket);
  }

  public void setPlayer(Player p) {
    this.player = p;
  }
  public Player getPlayer() { return player; }
  public String getPlayerString() {
    return "Player: " + this.player.getName() + " Color: " + this.player.getColor().toString();
  }

  @Override
  public void setup() {
    Logger.info(Logger.logTime() + this.getUUID() + " joined!");
  }

  public int getPlayerId() {
    return player.getId();
  }

  public String getPlayerName() {
    return player.getName();
  }

  private boolean isJSONValid(String json) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(json);
      return true;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  @Override
  public void listen() {
    Object o;
    Object res;
    JSONCommandWrapper<?> reqJSON = null;
    while (true) {
      try {
        o = readInput();
        if(!(o instanceof String)) continue;
        if(!isJSONValid((String) o)) continue; // Skip if invalid json was sent
        reqJSON = new JSONCommandWrapper<>((String) o);
        res = this.socketHandler.processInput(reqJSON, this.getUUID());
        if(res.equals(-1)) break;
      } catch(SocketTimeoutException ignored) {
      } catch(EOFException e) { // Socket closing
        quit(this.getUUID());
        break;
      } catch (IOException | ClassNotFoundException e) {
        Logger.error(e.getMessage());
        break;
      }
      if(reqJSON != null) Logger.info("Incoming command: " + reqJSON.toString());
    }
    quit(this.getUUID()); // just in case
  }

  protected void quit(UUID client) {
    BaseGameServer.removeClient(client);
    try {
      BaseGameServer.messageAll("Client " + client + " has left.");
    } catch (IOException ioException) {
      Logger.error(ioException.getMessage());
    }
  }
}
