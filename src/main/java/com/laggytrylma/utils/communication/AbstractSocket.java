package com.laggytrylma.utils.communication;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * Abstract class for holding base client connection socket handling logic.
 */
public abstract class AbstractSocket implements Runnable {
  /**
   * AbstractSocket's uuid.
   */
  private final UUID uuid;
  /**
   * AbstractSocket's ObjectInput (stream).
   */
  private ObjectInput input;
  /**
   * AbstractSocket's ObjectOutput (stream).
   */
  private ObjectOutput output;
  /**
   * AbstractSocket's Socket object.
   */
  protected final Socket socket;
  /**
   * AbstractSocket's socket handler object.
   */
  public AbstractCommandHandler socketHandler;

  /**
   * Class constructor
   * @param socket Socket object to be bound to this instance.
   */
  protected AbstractSocket(Socket socket) {
    this.socket = socket;
    this.uuid = UUID.randomUUID();
  }

  /**
   * Get AbstractSocket's UUID.
   * @return UUID of this instance.
   */
  public UUID getUUID() {
    return this.uuid;
  }

  /**
   * Set AbstractSocket's AbstractCommandHandler.
   * @param handler AbstractCommandHandler object
   */
  public void setSocketHandler(AbstractCommandHandler handler) {
    this.socketHandler = handler;
  }

  /**
   * Set AbstractSocket's ObjectInput (stream).
   * @param input ObjectInput (stream) object.
   */
  public void setInput(ObjectInput input) {
    this.input = input;
  }

  /**
   * Set AbstractSocket's ObjectOutput (stream).
   * @param output ObjectInput (stream) object.
   */
  public void setOutput(ObjectOutput output) {
    this.output = output;
  }

  /**
   * Read Input from ObjectInput (stream).
   * @return Object read from ObjectInput
   * @throws IOException Input couldn't be read
   * @throws ClassNotFoundException Unidentified class send over ObjectInput (stream).
   */
  public Object readInput() throws IOException, ClassNotFoundException {
    return input.readObject();
  }

  /**
   * Write object to ObjectOutput (stream).
   * @param o Object to be written to ObjectOutput
   * @throws IOException Output couldn't be written.
   */
  public void writeOutput(Object o) throws IOException {
    output.writeObject(o);
  }

  /**
   * Setup this instance (abstract).
   * @throws IOException any socket exception
   */
  public abstract void setup() throws IOException;

  /**
   * Listening loop (abstract).
   */
  public abstract void listen();

  /**
   * Close client connection socket.
   * @throws IOException socket error
   */
  public void close() throws IOException {
    this.socket.close();
  }

  /**
   * Run socket thread.
   */
  @Override
  public void run() {
    listen();
    try {
     this.close();
     Logger.info("Goodbye! " + this.getUUID());
    } catch (IOException ioException) {
      Logger.error(ioException.getMessage());
    }
  }
}
