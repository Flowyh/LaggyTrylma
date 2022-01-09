package com.laggytrylma.backend.ctx;

import com.laggytrylma.helpers.AbstractSystemOutCatch;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestAbstractSocket extends AbstractSystemOutCatch {

  @Test
  public void testSocketRun() throws IOException {
    Socket mockSocket = mock(Socket.class);
    // Spy class, so we can actually call constructor of Abstract class
    AbstractSocket testSocket = spy(createSocket(mockSocket));
    AbstractCommandHandler socketHandler = mock(AbstractCommandHandler.class);
    testSocket.setSocketHandler(socketHandler);

    doNothing().when(testSocket).setup();
    doNothing().when(testSocket).listen();
    assertNotNull(testSocket.getUUID());

    doCallRealMethod().when(testSocket).run();
    testSocket.run();
    testSocket.setup();
    verify(testSocket).setup();
    verify(testSocket).listen();

    testSocket.close();
  }

  @Test(expected = IOException.class)
  public void testSocketIOException() throws IOException {
    Socket mockSocket = mock(Socket.class);
    // Spy class, so we can actually call constructor of Abstract class
    AbstractSocket testSocket = spy(createSocket(mockSocket));
    AbstractCommandHandler socketHandler = mock(AbstractCommandHandler.class);
    testSocket.setSocketHandler(socketHandler);

    doThrow(IOException.class).when(testSocket).close();
    testSocket.close();
  }

  public AbstractSocket createSocket(Socket socket) {
    return new AbstractSocket(socket) {
      @Override
      public void setup() {
      }

      @Override
      public void listen() {
      }
    };
  }
}
