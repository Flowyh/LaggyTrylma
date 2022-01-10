package com.laggytrylma.backend.sockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.backend.server.BaseGameServerCommandsReceiver;
import com.laggytrylma.backend.server.commands.MessageAll;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

public class BaseGameSocket extends AbstractSocket {
  protected Player player = null;
  public BaseGameServer serv;

  public BaseGameSocket(Socket socket) {
    super(socket);
  }

  public void bindServer(BaseGameServer serv) {
    this.serv = serv;
    ((BaseGameSocketHandler) this.socketHandler).bindServer(serv);
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


  @Override
  public void close() {
    Logger.debug("My close");
    try{
      super.close();
    } catch (IOException e) {
      Logger.error(e.getMessage());
    }

    serv.removeClient(getUUID());
    serv.cmdExecutor.executeCommand(new MessageAll(new BaseGameServerCommandsReceiver(serv.getClients(), "Client " + getUUID() + " has left.")));
  };
}
