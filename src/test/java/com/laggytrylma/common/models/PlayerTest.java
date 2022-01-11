package com.laggytrylma.common.models;

import org.junit.Test;
import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PlayerTest {
  @Test(expected = NullPointerException.class)
  public void testPlayerInstantiation() throws NullPointerException {
    Player test = new Player(1, "1", new Color(0, 0, 0));
    assertEquals(test.toString(), "Player: 1 Color: java.awt.Color[r=0,g=0,b=0] Id: 1");
    test = new Player();
    Object itThrowsAnyway = test.toString();
  }

  @Test
  public void testPlayerGetters() {
    Player test = new Player(1, "1", new Color(0, 0, 0));
    assertEquals(test.getColor().toString(), "java.awt.Color[r=0,g=0,b=0]");
    assertEquals(test.getId(), 1);
    assertEquals(test.getName(), "1");
  }

  @Test
  public void testPlayerSetters() {
    Player test = new Player(1, "1", new Color(0, 0, 0));
    test.setName("test");
    test.setColor(new Color(1, 1, 1));
    assertEquals(test.getColor().toString(), "java.awt.Color[r=1,g=1,b=1]");
    assertEquals(test.getName(), "test");
  }
}
