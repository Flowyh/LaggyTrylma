package com.laggytrylma.backend.sockets;

import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * GoF's builder pattern for creating new BaseGameSocket instances.
 */
public class BaseGameSocketBuilder implements AbstractSocketBuilder {
  /**
   * New BaseGameSocket instance holder.
   */
  BaseGameSocket socket;

  /**
   * Create new BaseGameSocket instance, bind socket connection and set Input/OutputStreams.
   * @param socket client's connection socket
   * @return AbstractSocketBuilder for next methods
   * @throws IOException socket exceptions
   */
  @Override
  public AbstractSocketBuilder setSocket(Socket socket) throws IOException {
    this.socket = new BaseGameSocket(socket);
    this.socket.setInput(new ObjectInputStream(socket.getInputStream()));
    this.socket.setOutput(new ObjectOutputStream(socket.getOutputStream()));
    return this;
  }

  /**
   * Setup socket handler and call socket's setup method.
   * @return AbstractSocketBuilder for next methods
   */
  @Override
  public AbstractSocketBuilder setupSocket() {
    this.socket.setSocketHandler(new BaseGameSocketHandler());
    this.socket.setup();
    return this;
  }

  /**
   * Return created BaseGameSocket instance.
   * @return AbstractSocket new instance.
   */
  @Override
  public AbstractSocket build() {
    return socket;
  }
}
