package com.laggytrylma.backend.sockets;

import com.laggytrylma.backend.server.BaseGameLobbyManager;
import com.laggytrylma.backend.server.BaseGameServer;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.helpers.AbstractSystemOutCatch;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.AbstractSocket;
import org.junit.Test;

import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BaseGameSocketTest extends AbstractSystemOutCatch {
//  @Test
//  public void testBindServer() {
//    BaseGameServer serv = mock(BaseGameServer.class);
//    BaseGameSocketHandler handler = mock(BaseGameSocketHandler.class);
//    BaseGameSocket test = new BaseGameSocket(new Socket());
//    test.setSocketHandler(handler);
//    test.bindServer(serv);
//    assertNotNull(test.serv);
//  }

  @Test
  public void testPlayer() {
    BaseGameSocket test = new BaseGameSocket(new Socket());
    Player player = new Player(1, "1", new Color(0, 0, 0));
    test.setPlayer(player);
    assertEquals(test.getPlayer().toString(), "Player: 1 Color: java.awt.Color[r=0,g=0,b=0] Id: 1");
    assertEquals(test.getPlayerString(), "Player: 1 Color: java.awt.Color[r=0,g=0,b=0]");
    assertEquals(test.getPlayerId(), 1);
    assertEquals(test.getPlayerName(), "1");
  }

  @Test
  public void testJsonValid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    BaseGameSocket test = new BaseGameSocket(new Socket());
    Method isJsonValid = BaseGameSocket.class.getDeclaredMethod("isJSONValid", String.class);
    isJsonValid.setAccessible(true);
    assertEquals(isJsonValid.invoke(test, "{}"), true);
    assertEquals(isJsonValid.invoke(test, "123123{\123}"), false);
  }

  @Test
  public void testSetup() throws NoSuchFieldException, IllegalAccessException {
    BaseGameSocket test = new BaseGameSocket(new Socket());
    Field uuid = AbstractSocket.class.getDeclaredField("uuid");
    uuid.setAccessible(true);
    UUID testUUID = UUID.randomUUID();
    uuid.set(test, testUUID);
    test.setup();
    LocalDateTime current = LocalDateTime.now();
    String logTime = "[DATE: " + current.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TIME: " + current.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
    assertEquals(logTime + "[[32mINFO[0m] " + logTime + testUUID.toString() + " joined!"+ System.lineSeparator(), outContent.toString());
  }

//  @Test
//  public void testListenErrorCaught() throws NoSuchFieldException, IllegalAccessException, IOException, ClassNotFoundException {
//    Logger.setDoLogTime(false);
//    // Set mocks
//    BaseGameServer serv = mock(BaseGameServer.class);
//    BaseGameLobbyManager manager = mock(BaseGameLobbyManager.class);
//    Field lM = BaseGameServer.class.getDeclaredField("lobbyManager");
//    lM.setAccessible(true);
//    lM.set(serv, manager);
//    BaseGameSocketHandler handler = mock(BaseGameSocketHandler.class);
//    BaseGameSocket test = new BaseGameSocket(new Socket());
//    ObjectInputStream mockOIS = mock(ObjectInputStream.class);
//    // Set BaseGameServerSocket
//    test.setInput(mockOIS);
//    test.setSocketHandler(handler);
//    test.bindServer(serv);
//    // Set Throwers
//    doThrow(IOException.class).when(mockOIS).readObject();
//    test.listen();
//    doThrow(EOFException.class).when(mockOIS).readObject();
//    test.listen();
//    // Verification
//    verify(mockOIS, times(2)).readObject();
//    assertEquals("[[31mERROR[0m] null" + System.lineSeparator(), outContent.toString());
//    Logger.setDoLogTime(true);
//  }
//
//  @Test
//  public void testListenSuccess() throws NoSuchFieldException, IllegalAccessException, IOException, ClassNotFoundException {
//    Logger.setDoLogTime(false);
//    String testJSON = "{\"model\":\"game\",\"command\":\"move\",\"args\":{}}";
//    // Set mocks
//    BaseGameSocket test = new BaseGameSocket(new Socket());
//    BaseGameServer serv = mock(BaseGameServer.class);
//    BaseGameLobbyManager manager = mock(BaseGameLobbyManager.class);
//    BaseGameSocketHandler handler = mock(BaseGameSocketHandler.class);
//    ObjectInputStream mockOIS = mock(ObjectInputStream.class);
//    Field lM = BaseGameServer.class.getDeclaredField("lobbyManager");
//    lM.setAccessible(true);
//    lM.set(serv, manager);
//    // Set BaseGameServerSocket
//    test.setInput(mockOIS);
//    test.setSocketHandler(handler);
//    test.bindServer(serv);
//    doReturn(testJSON).when(mockOIS).readObject();
//    doReturn(-1).when(handler).processInput(any(), any());
//    test.listen();
//    verify(handler).processInput(any(), any());
//  }
//
//  @Test
//  public void testClose() throws NoSuchFieldException, IllegalAccessException {
//    BaseGameServer serv = mock(BaseGameServer.class);
//    BaseGameLobbyManager manager = mock(BaseGameLobbyManager.class);
//    Field lM = BaseGameServer.class.getDeclaredField("lobbyManager");
//    lM.setAccessible(true);
//    lM.set(serv, manager);
//    BaseGameSocketHandler handler = mock(BaseGameSocketHandler.class);
//    BaseGameSocket test = new BaseGameSocket(new Socket());
//    test.setSocketHandler(handler);
//    test.bindServer(serv);
//    test.close();
//    verify(manager).removeClient(test.getUUID());
//    verify(serv).removeClient(test.getUUID());
//  }
}
