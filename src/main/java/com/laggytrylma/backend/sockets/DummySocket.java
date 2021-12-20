package com.laggytrylma.backend.sockets;

import com.laggytrylma.backend.ctx.AbstractSocket;
import com.laggytrylma.utils.Logger;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class DummySocket extends AbstractSocket {

  public DummySocket(Socket socket) {
    super(socket);
  }

  @Override
  public void setup() {
    Logger.info(Logger.logTime() + this.getUUID() + " joined!");
  }

  @Override
  public void listen() {
    while (true) {
      Object o = null;
      try {
        o = readInput();
        Object res = this.socketHandler.processInput(o, this.getUUID());
        if(res.equals(-1)) break;
      } catch(SocketTimeoutException ignored) {
      } catch(EOFException e) {
        break;
      } catch (IOException | ClassNotFoundException e) {
        Logger.error(e.getMessage());
        break;
      }
      Logger.info("Incoming message: " + o);
    }
  }
}
