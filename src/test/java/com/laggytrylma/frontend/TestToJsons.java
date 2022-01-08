package com.laggytrylma.frontend;

import com.laggytrylma.common.ClassicTrylmaBuilder;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.GameBuilderDirector;
import org.junit.Test;

public class TestToJsons {
  @Test
  public void testGameToJSON() {
    GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
    Game trylma = director.build();
    System.out.println(trylma.toJSON());
  }
}
