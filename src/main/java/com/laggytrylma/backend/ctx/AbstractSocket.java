package com.laggytrylma.backend.ctx;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public abstract class AbstractSocket implements Runnable {
  private UUID uuid;
  private ObjectInputStream input;
  private ObjectOutputStream output;
  private Socket socket;
  public AbstractCommandHandler socketHandler;

  protected AbstractSocket(Socket socket) {
    this.socket = socket;
    this.uuid = UUID.randomUUID();
  }

  public UUID getUUID() {
    return this.uuid;
  }

  public void setSocketHandler(AbstractCommandHandler handler) {
    this.socketHandler = handler;
  }

  public void setInput(ObjectInputStream input) {
    this.input = input;
  }

  public void setOutput(ObjectOutputStream output) {
    this.output = output;
  }

  public Object readInput() throws IOException, ClassNotFoundException {
    return input.readObject();
  }

  public void writeOutput(Object o) throws IOException {
    output.writeObject(o);
  }

  public abstract void setup() throws IOException;
  public abstract void listen();

  public void close() throws IOException {
    this.socket.close();
  }

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
