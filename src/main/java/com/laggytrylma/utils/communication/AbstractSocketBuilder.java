package com.laggytrylma.utils.communication;


import java.io.IOException;
import java.net.Socket;

public interface AbstractSocketBuilder {
  AbstractSocketBuilder setSocket(Socket socket) throws IOException;
  AbstractSocketBuilder setupSocket() throws IOException;
  AbstractSocket build();
}
