package com.laggytrylma.backend.ctx;

import com.laggytrylma.helpers.AbstractSystemOutCatch;
import com.laggytrylma.utils.communication.AbstractSocketBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AbstractServerTest extends AbstractSystemOutCatch {

  @Test
  public void testStartServer() {
    // Spy class, so we can actually call constructor of Abstract class
    AbstractServer testServer = spy(createServer(21370, "TEST"));
    doNothing().when(testServer).listen();
    when(testServer.whoAreYou()).thenReturn("TEST");

    doCallRealMethod().when(testServer).startServer(100, null);
    doCallRealMethod().when(testServer).close();
    testServer.startServer(100, null);
    testServer.listen();
    verify(testServer).listen();

    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";

    assertFalse(testServer.isClosed());
    assertEquals("TEST", testServer.whoAreYou());
    assertEquals(logTime + "[[32mINFO[0m] Running server: TEST on port " + testServer.getPort() + "." + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testStartServerFail() {
    // Spy class, so we can actually call constructor of Abstract class
    AbstractServer testServer = spy(createServer(-1, "TEST"));
    doNothing().when(testServer).listen();
    when(testServer.whoAreYou()).thenReturn("TEST");

    doCallRealMethod().when(testServer).startServer(100, null);
    testServer.startServer(100, null);

    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    testServer.close();
    assertEquals(logTime
                    + "[[31mERROR\u001B[0m] Port value out of range: -1"
                    + System.lineSeparator()
                    + logTime
                    + "[\u001B[31mERROR\u001B[0m] Cannot invoke \"java.net.ServerSocket.close()\" because \"<local2>.serverSocket\" is null"
                    + System.lineSeparator()
            , outContent.toString());
  }

  @Test
  public void testIllegalThreadPoolSize() {
    AbstractServer testServer = spy(createServer(21371, "TEST"));
    doThrow(IllegalArgumentException.class).when(testServer).startServer(-1, null);
    assertThrows(IllegalArgumentException.class, () -> testServer.startServer(-1, null));
  }

  @Test
  public void testClose() {
    AbstractServer testServer = spy(createServer(21372, "TEST"));
    doCallRealMethod().when(testServer).startServer(100, null);
    testServer.startServer(100, null);
    testServer.close();
    verify(testServer).close();
  }

  @Test
  public void testCloseException() {
    AbstractServer testServer = spy(createServer(21372, "TEST"));
    doCallRealMethod().when(testServer).close();
    doCallRealMethod().when(testServer).startServer(100, null);
    testServer.startServer(100, null);
    testServer.close();
    assertNotNull(outContent.toString());
  }

  @Test
  public void testFlipRunFlag() {
    AbstractServer test = createServer(21376, "TEST");
    boolean before = test.running;
    test.flipRunFlag();
    boolean after = test.running;
    assertEquals(!before, after);
  }

  @Test
  public void testServerSocketBuilder() throws IOException {
    AbstractServer test = createServer(21376, "TEST");
    AbstractSocketBuilder mockBuilder = mock(AbstractSocketBuilder.class);
    doReturn(mockBuilder).when(mockBuilder).setSocket(any());
    doReturn(mockBuilder).when(mockBuilder).setupSocket();
    doReturn(null).when(mockBuilder).build();
    test.setSocketBuilder(mockBuilder);
    assertNull(test.createNewSocket(mock(Socket.class)));
  }

  public AbstractServer createServer(int port, String name) {
    return new AbstractServer(port, name) {
      @Override
      protected void setup() {}
      @Override
      public void listen() { }
    };
  }
}
