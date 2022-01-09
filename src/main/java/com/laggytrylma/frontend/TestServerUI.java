package com.laggytrylma.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.backend.sockets.basegame.BaseGameSocket;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.Player;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class TestServerUI {
  private static final JFrame f = new JFrame();
  protected Game game;
  protected Player player;
  private final String serverAddress;
  private final int port;
  private boolean reading = true;
  private Socket server;
  protected ObjectOutputStream output;
  protected ObjectInputStream input;
  private static final BoardWidget display = new BoardWidget();
  private static final ServerMessageHandler servMsgHandler = new ServerMessageHandler();
  private final int connection_retries = 5;

  public TestServerUI(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
    servMsgHandler.bindUI(this);

    f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

    f.setSize(500,500);//500 width and 500 height
    f.setVisible(true);//making the frame visible
    f.add(display);
    f.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (JOptionPane.showConfirmDialog(f,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
          try {
            if(output == null) throw new IOException("Output is closed");
            Map<String, String> args = new HashMap<>();
            JSONCommandWrapper<GameCommands> cmd = new JSONCommandWrapper<>(GameCommands.LEAVE, args);
            output.writeObject(cmd.serialize());
            quit();
          } catch (IOException e) {
            Logger.error(e.getMessage());
            quit();
          }
        }
      }
    });
  }

  public void setGame(Game g) {
    this.game = g;
  }
  public void setPlayer(Player p) {
    this.player = p;
  }

  public void attachGameToDisplay() {
    display.attachGame(this.game);
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

  private void run() {
    for(int i = 0; i < connection_retries; i++) {
      Logger.debug("Connecting to server [try: " + i + "] . . . ");
      try {
        this.server = new Socket(this.serverAddress, this.port);
        output = new ObjectOutputStream(this.server.getOutputStream());
        input = new ObjectInputStream(this.server.getInputStream());
        if (this.server != null && output != null) break;
      } catch(IOException e) {
        Logger.error(e.getMessage());
      }
    }
    if(this.server == null || output == null) {
      Logger.error("Couldn't connect to the game server");
      return;
    }
    Logger.info("Connected to server!");
    Object result;
    JSONCommandWrapper<?> json;
    try {
      Object o;
      while (this.reading) {
        if (!this.server.isClosed()) {
          try {
            o = input.readObject();
            Logger.debug("Server msg: " + o.toString());
            if(!(o instanceof String) || !(isJSONValid((String) o))) continue;
            json = new JSONCommandWrapper<>((String) o);
            result = servMsgHandler.processInput(json, null);
            Logger.debug("Result: " + result.toString());
          } catch (SocketException ignored) {
          } catch (IOException e) {
            Logger.error(e.getMessage());
          }
          //Map<String, String> args = new HashMap<>();
          //args.put("piece", piece_id);
          //args.put("destination", square_id);
          //JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.MOVE, args);
          //try {
          //  output.write?(msg.serialize());
          //} catch(IOException e) {
          //  Logger.error(e.getMessage());
          //}
        }
      }
    } catch (ClassNotFoundException e) {
      Logger.error(e.getMessage());
    } finally {
      this.quit();
    }
  }

  private void quit() {
    this.reading = false;
    try {
      if(server == null || output == null) throw new IOException("Server or output is closed");
      this.server.close();
      output.close();
    } catch (IOException e) {
      Logger.error(e.getMessage());
    }
    f.setVisible(false);
    f.dispose();
    System.exit(1);
  }


  public static void main(String[] args) throws IOException {
    Logger.setDepth(4);
    Logger.setDoLogTime(false);
    var client = new TestServerUI("127.0.0.1", 21375);
    client.run();
    client.quit();
  }
}
