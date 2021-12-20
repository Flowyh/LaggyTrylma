package com.laggytrylma.backend;

import com.laggytrylma.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class TestClient {
  private final String serverAddress;
  private final int port;
  private boolean reading = true;
  private Socket server;
  private ObjectOutputStream output;
  JFrame frame = new JFrame("Chatter");
  JTextField textField = new JTextField(50);
  JTextArea messageArea = new JTextArea(16, 50);

  public TestClient(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;

    this.textField.setEditable(true);
    this.messageArea.setEditable(false);
    this.frame.getContentPane().add(textField, BorderLayout.SOUTH);
    this.frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
    this.frame.pack();

    // Send on enter then clear to prepare for next message
    this.textField.addActionListener(e -> {
      try {
        this.output.writeObject(textField.getText());
      } catch (IOException ioException) {
        Logger.error(ioException.getMessage());
      }
      this.textField.setText("");
    });

    this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
          try {
            output.writeObject("!quit");
            quit();
          } catch (IOException ioException) {
            ioException.printStackTrace();
          }
        }
      }
    });
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
          if(o != null) {
            Logger.info("Sending message: " + o);
            this.messageArea.append(o + "\n");
          }
        }
      }
    } catch(SocketException ignored) {
    } catch(IOException | ClassNotFoundException e) {
      e.printStackTrace();
      Logger.error(e.getMessage());
    } finally {
      quit();
    }
  }

  private void quit() throws IOException {
    this.reading = false;
    this.server.close();
    this.frame.setVisible(false);
    this.frame.dispose();
  }

  public static void main(String[] args) throws Exception {
    Logger.setDepth(4);
    Logger.setDoLogTime(false);
    var client = new TestClient("127.0.0.1", 21375);
    client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    client.frame.setVisible(true);
    client.run();
  }
}
