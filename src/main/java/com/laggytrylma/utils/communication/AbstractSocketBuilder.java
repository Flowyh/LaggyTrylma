package com.laggytrylma.utils.communication;


import java.io.IOException;
import java.net.Socket;

public abstract class AbstractSocketBuilder {
  public abstract AbstractSocketBuilder setSocket(Socket socket) throws IOException;
  public abstract AbstractSocketBuilder setupSocket() throws IOException;
  public abstract AbstractSocket build();
}
