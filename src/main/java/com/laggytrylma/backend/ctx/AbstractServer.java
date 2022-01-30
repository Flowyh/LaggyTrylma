package com.laggytrylma.backend.ctx;

import com.laggytrylma.backend.GameRepoWrap;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Abstract class holding base server logic.
 */
public abstract class AbstractServer {
  /**
   * Server's port.
   */
  private final int port;
  /**
   * Server's name.
   */
  private final String name;
  /**
   * Server's socket thread pool.
   */
  protected ThreadPoolExecutor threadPool;
  /**
   * Main server's socket, which accepts client connections.
   */
  protected ServerSocket serverSocket;
  /**
   * Builder for creating new client connection socket classes.
   */
  private AbstractSocketBuilder socketBuilder;
  /**
   * Is the server running?
   */
  protected boolean running = true;

  protected GameRepoWrap repoWrapper;

  /**
   * AbstractServer class constructor
   * @param port server's port
   * @param name server's name
   */
  protected AbstractServer(int port, String name) {
    this.port = port;
    this.name = name;
  }

  /**
   * Server's port getter.
   * @return int server's port
   */
  public int getPort() {
    return port;
  }

  /**
   * Server's socket builder setter.
   * @param socketBuilder new socket builder
   */
  protected void setSocketBuilder(AbstractSocketBuilder socketBuilder) {
    this.socketBuilder = socketBuilder;
  }

  /**
   * Start server instance.
   * @param threads number of allowed client connections
   * @param args server arguments (currently unused).
   */
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

  /**
   * Create new socket instance.
   * Basically it should be done in a separate AbstractSocketBuilder's director class,
   * but it's literally just one line of code, so I've put it here.
   * @param socket client connection socket
   * @return AbstractSocket
   * @throws IOException socket errors
   */
  public AbstractSocket createNewSocket(Socket socket) throws IOException {
    return socketBuilder.setSocket(socket).setupSocket().build();
  }

  protected abstract void setup();
  public abstract void listen();

  /**
   * Close server instance.
   */
  protected void close() {
    try {
      this.serverSocket.close();
    } catch (NullPointerException | IOException e) {
      Logger.error(e.getMessage());
    }
  }

  /**
   * Server's name getter.
   * @return String server's name
   */
  protected String whoAreYou() { return this.name; }

  /**
   * Check whether server is closed.
   * @return boolean server close state.
   */
  protected boolean isClosed() { return this.serverSocket.isClosed(); }

  /**
   * Negate server's running flag.
   */
  protected void flipRunFlag() { running = !running; }

  public void bindRepoWrapper(GameRepoWrap repoWrap) {
    this.repoWrapper = repoWrap;
  }
}
