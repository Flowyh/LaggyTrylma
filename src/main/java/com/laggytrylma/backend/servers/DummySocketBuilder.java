package com.laggytrylma.backend.servers;

import com.laggytrylma.backend.ctx.AbstractSocket;
import com.laggytrylma.backend.ctx.AbstractSocketBuilder;
import com.laggytrylma.backend.sockets.DummySocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DummySocketBuilder extends AbstractSocketBuilder {
  DummySocket socket;

  @Override
  public AbstractSocketBuilder setSocket(Socket socket) throws IOException {
    this.socket = new DummySocket(socket);
    this.socket.setInput(new ObjectInputStream(socket.getInputStream()));
    this.socket.setOutput(new ObjectOutputStream(socket.getOutputStream()));
    return this;
  }

  @Override
  public AbstractSocketBuilder setupSocket() {
    this.socket.setSocketHandler(new DummySocketHandler());
    this.socket.setup();
    return this;
  }

  @Override
  public AbstractSocket build() {
    return socket;
  }
}
