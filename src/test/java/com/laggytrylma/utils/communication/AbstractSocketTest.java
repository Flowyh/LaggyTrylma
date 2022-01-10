package com.laggytrylma.utils.communication;

import com.laggytrylma.helpers.AbstractSystemOutCatch;
import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AbstractSocketTest extends AbstractSystemOutCatch {
  @Test
  public void testCreation() {
    Socket mockSocket = mock(Socket.class);
    AbstractSocket test = mock(AbstractSocket.class, Mockito.withSettings().useConstructor(mockSocket).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    assertNotNull(test);
    assertNotNull(test.getUUID());
  }

  @Test
  public void testSetters() {
    AbstractSocket test = mock(AbstractSocket.class);
    doCallRealMethod().when(test).setSocketHandler(any());
    doCallRealMethod().when(test).setInput(any());
    doCallRealMethod().when(test).setOutput(any());
    AbstractCommandHandler mockHandler = mock(AbstractCommandHandler.class);
    ObjectOutputStream mockOOS = mock(ObjectOutputStream.class);
    ObjectInputStream mockOIS = mock(ObjectInputStream.class);
    test.setSocketHandler(mockHandler);
    test.setOutput(mockOOS);
    test.setInput(mockOIS);
    verify(test).setSocketHandler(any());
    verify(test).setOutput(any());
    verify(test).setInput(any());
    assertNotNull(test.socketHandler);
  }

  @Test
  public void testSocketClose() throws IOException {
    Socket mockSocket = mock(Socket.class);
    AbstractSocket test = mock(AbstractSocket.class, Mockito.withSettings().useConstructor(mockSocket).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    test.close();
    verify(mockSocket).close();
  }

  @Test
  public void testSetup() throws IOException {
    AbstractSocket test = mock(AbstractSocket.class);
    test.setup();
    verify(test).setup();
  }

  @Test
  public void testRun() throws IOException {
    Socket mockSocket = mock(Socket.class);
    AbstractSocket test = mock(AbstractSocket.class, Mockito.withSettings().useConstructor(mockSocket).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    doNothing().when(test).listen();
    test.run();
    verify(test).listen();
    verify(test).close();
  }

  @Test
  public void testRunCloseFail() throws IOException {
    Socket mockSocket = mock(Socket.class);
    AbstractSocket test = mock(AbstractSocket.class, Mockito.withSettings().useConstructor(mockSocket).defaultAnswer(Mockito.CALLS_REAL_METHODS));
    doNothing().when(test).listen();
    doThrow(IOException.class).when(test).close();
    test.run();
    verify(test).listen();
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
