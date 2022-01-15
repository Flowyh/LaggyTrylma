package com.laggytrylma.utils.communication;


import java.io.IOException;
import java.net.Socket;

/**
 * GoF's builder pattern for creating Socket handling classes.
 */
public interface AbstractSocketBuilder {
  AbstractSocketBuilder setSocket(Socket socket) throws IOException;
  AbstractSocketBuilder setupSocket() throws IOException;
  AbstractSocket build();
}
