package com.laggytrylma.backend.ctx;

import com.laggytrylma.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractServer {
  private int port;
  private String name;
  protected ThreadPoolExecutor threadPool;
  protected ServerSocket serverSocket;
  protected static Map<UUID, AbstractSocket> clients;
  private AbstractSocketBuilder socketBuilder;
  protected boolean running = true;

  protected AbstractServer(int port, String name) {
    this.port = port;
    this.name = name;
    clients = new HashMap<>();
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

  public static void removeClient(UUID uuid) {
    clients.remove(uuid);
  }

  public static void messageAll(Object msg) throws IOException {
    for (UUID key : clients.keySet()) {
      clients.get(key).writeOutput(msg);
    }
  }

  protected abstract void setup();
  public abstract void listen();
  protected void close() {
    try {
      this.serverSocket.close();
    } catch (IOException ioException) {
      Logger.error(ioException.getMessage());
    }
  }


  protected String whoAreYou() { return this.name; }
  protected boolean isClosed() { return this.serverSocket.isClosed(); }
  protected void flipRunFlag() { running = !running; }
}
