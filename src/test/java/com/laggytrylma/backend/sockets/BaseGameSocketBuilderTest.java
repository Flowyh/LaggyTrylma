package com.laggytrylma.backend.sockets;

import com.laggytrylma.utils.communication.AbstractSocket;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BaseGameSocketBuilderTest {
  @Test
  public void testBaseGameSocketBuilder() throws NoSuchFieldException {
    BaseGameSocketBuilder test = new BaseGameSocketBuilder();
    Field baseGameSocket = BaseGameSocketBuilder.class.getDeclaredField("socket");
    baseGameSocket.setAccessible(true);
    Socket mockSocket = mock(Socket.class);
    try {
      test = (BaseGameSocketBuilder) test.setSocket(mockSocket);
    } catch(IOException | NullPointerException ignore) {}
    AbstractSocket res = test.setupSocket().build();
    assertNotNull(test.socket);
    assertNotNull(res);
  }
}
