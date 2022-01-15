package com.laggytrylma.backend.ctx;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractServer {
  private int port;
  private String name;
  protected ThreadPoolExecutor threadPool;
  protected ServerSocket serverSocket;
  private AbstractSocketBuilder socketBuilder;
  protected boolean running = true;

  protected AbstractServer(int port, String name) {
    this.port = port;
    this.name = name;
  }

  public int getPort() {
    return port;
  }

  protected void setSocketBuilder(AbstractSocketBuilder socketBuilder) {
    this.socketBuilder = socketBuilder;
  }

  public void startServer(int threads, ArrayList<String> args) {
    try {
      this.serverSocket = new ServerSocket(port);
      Logger.info("Running server: " + name + " on port " + this.port + ".");
      threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
      setup();
    } catch(IllegalArgumentException | IOException e) {
      Logger.error(e.getMessage());
    }
  }

  public AbstractSocket createNewSocket(Socket socket) throws IOException {
    return socketBuilder.setSocket(socket).setupSocket().build();
  }

  protected abstract void setup();
  public abstract void listen();
  protected void close() {
    try {
      this.serverSocket.close();
    } catch (NullPointerException | IOException e) {
      Logger.error(e.getMessage());
    }
  }


  protected String whoAreYou() { return this.name; }
  protected boolean isClosed() { return this.serverSocket.isClosed(); }
  protected void flipRunFlag() { running = !running; }
}
