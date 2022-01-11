package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClassicTrylmaBuilderTest {
  @Test
  public void testClassicTrylmaBuilderNoPlayers() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    ClassicTrylmaBuilder test = new ClassicTrylmaBuilder();
    Method makeInstance = ClassicTrylmaBuilder.class.getDeclaredMethod("makeInstance");
    makeInstance.setAccessible(true);
    makeInstance.invoke(test);
    test.instantiateBoard();
    test.makeConnections();
    test.connectPlayers(null);
    test.setSquareOwnership();
    test.createPieces();
    test.addRules();
    Game result = test.getResult();
    assertNotNull(result);
    assertEquals(result.getRules().size(), 4);
    assertEquals(result.getPieces().size(), 60);
    assertEquals(result.getSquares().size(), 121);
    assertEquals(result.getPlayers().size(), 6);
  }

  @Test
  public void testClassicTrylmaBuilderWithPlayers() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
    assertNotNull(test.getResult());
  }
}