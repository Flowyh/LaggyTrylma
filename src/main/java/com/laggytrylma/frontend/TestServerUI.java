package com.laggytrylma.frontend;

import com.laggytrylma.common.Game;
import com.laggytrylma.utils.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TestServerUI {
  private static final JFrame f = new JFrame();
  private static Game game;
  private final String serverAddress;
  private final int port;
  private boolean reading = true;
  private Socket server;
  private ObjectOutputStream output;
  private static final BoardWidget display = new BoardWidget();

  public TestServerUI(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;

    f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

    f.setSize(500,500);//500 width and 500 height
    f.setVisible(true);//making the frame visible
    f.add(display);

  }

  private void run() throws IOException {
    try {
      this.server = new Socket(this.serverAddress, this.port);
      Logger.info("Connected to server!");
      this.output = new ObjectOutputStream(this.server.getOutputStream());
      ObjectInputStream input = new ObjectInputStream(this.server.getInputStream());

      Object o;
      while(this.reading) {
        if(!this.server.isClosed()) {
          o = input.readObject();
          if(o instanceof Game) {
            Logger.info("Game updated");
            game = (Game) o;
            display.attachGame(game);
          }
        }
      }
    } catch(SocketException ignored) {
    } catch(IOException | ClassNotFoundException e) {
      e.printStackTrace();
      Logger.error(e.getMessage());
    } finally {
      this.quit();
    }
  }

  private void quit() throws IOException {
    this.reading = false;
    this.server.close();
    f.setVisible(false);
    f.dispose();
  }

  public static void main(String[] args) throws IOException {
    Logger.setDepth(4);
    Logger.setDoLogTime(false);
    var client = new TestServerUI("127.0.0.1", 21375);
    client.run();
  }
}
