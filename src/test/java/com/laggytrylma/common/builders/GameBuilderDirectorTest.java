package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Player;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class GameBuilderDirectorTest {
  @Test
  public void testGameBuilderDirectorClassicTrylmaNoPlayers() {
    GameBuilderDirector test = new GameBuilderDirector(new ClassicTrylmaBuilder());
    assertNotNull(test.build());
  }

  @Test
  public void testGameBuilderDirectorClassicTrylmaWithPlayers() {
    GameBuilderDirector test = new GameBuilderDirector(new ClassicTrylmaBuilder());
    ArrayList<Player> players = new ArrayList<>();
    for(int i = 0; i < 6; i ++) {
      players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
    }
    test.setPlayers(players.toArray(new Player[0]));
    assertNotNull(test.build());
  }

  @Test
  public void testGameBuilderDirectorNullBuilderFail() {
    GameBuilderDirector test = new GameBuilderDirector(null);
    assertThrows(NullPointerException.class, test::build);
  }
}
