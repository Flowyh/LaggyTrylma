package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClassicTrylmaBuilderTest {
  @Test
  public void testClassicTrylmaBuilder2Players() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ArrayList<Player> players = new ArrayList<>();
    for(int i = 0; i < 2; i ++) {
      players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
    }
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(players.toArray(new Player[0]));
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game g = test.getResult();
    assertNotNull(g);
    assertEquals(g.getPlayers().size(), 2);
    assertEquals(g.getRules().size(), 4);
    assertEquals(g.getPieces().size(), 20);
    assertEquals(g.getSquares().size(), 121);
    assertNull(g.getWinner());
  }

  @Test
  public void testClassicTrylmaBuilder3Players() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ArrayList<Player> players = new ArrayList<>();
    for(int i = 0; i < 3; i ++) {
      players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
    }
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(players.toArray(new Player[0]));
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game g = test.getResult();
    assertNotNull(g);
    assertEquals(g.getPlayers().size(), 3);
    assertEquals(g.getRules().size(), 4);
    assertEquals(g.getPieces().size(), 30);
    assertEquals(g.getSquares().size(), 121);
    assertNull(g.getWinner());
  }

  @Test
  public void testClassicTrylmaBuilder4Players() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ArrayList<Player> players = new ArrayList<>();
    for(int i = 0; i < 4; i ++) {
      players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
    }
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(players.toArray(new Player[0]));
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game g = test.getResult();
    assertNotNull(g);
    assertEquals(g.getPlayers().size(), 4);
    assertEquals(g.getRules().size(), 4);
    assertEquals(g.getPieces().size(), 40);
    assertEquals(g.getSquares().size(), 121);
    assertNull(g.getWinner());
  }

  @Test
  public void testClassicTrylmaBuilder6Players() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ArrayList<Player> players = new ArrayList<>();
    for(int i = 0; i < 6; i ++) {
      players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
    }
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(players.toArray(new Player[0]));
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game g = test.getResult();
    assertNotNull(g);
    assertEquals(g.getPlayers().size(), 6);
    assertEquals(g.getRules().size(), 4);
    assertEquals(g.getPieces().size(), 60);
    assertEquals(g.getSquares().size(), 121);
    assertNull(g.getWinner());
  }

  @Test
  public void testClassicTrylmaBuilderUndefinedPlayers() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ArrayList<Player> players = new ArrayList<>();
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(players.toArray(new Player[0]));
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game g = test.getResult();
    assertNotNull(g);
    assertEquals(g.getPlayers().size(), 0);
    assertEquals(g.getRules().size(), 4);
    assertEquals(g.getPieces().size(), 0);
    assertEquals(g.getSquares().size(), 121);
    assertNull(g.getWinner());
  }
}
