package com.laggytrylma.utils.communication;

import com.laggytrylma.common.ClassicTrylmaBuilder;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.GameBuilderDirector;
import com.laggytrylma.common.Player;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class JSONObjectSerializationTest {
  @Test
  public void testGameToJSON() {
    GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
    Game trylma = director.build();
    String serialized = ObjectJSONSerializer.serialize(trylma);
    System.out.println(serialized);
  }

  @Test
  public void testGameDeserialization() {
    GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
    Game trylma = director.build();
    String serialized = ObjectJSONSerializer.serialize(trylma);
    Game trylma2 = (Game)ObjectJSONSerializer.deserialize(serialized, Game.class);
  }

  @Test
  public void testPlayerToJSON() {
    Player test = new Player(1, "1", new Color(0, 126, 0));
    String serialized = ObjectJSONSerializer.serialize(test);
    assertEquals("{\"id\":1,\"name\":\"1\",\"color\":{\"argb\":\"ff007e00\"}}", serialized);
    System.out.println(ObjectJSONSerializer.deserialize(serialized, Player.class));
  }

  @Test
  public void testColorToJSON() {
    Color c = new Color(249, 65, 68);
    String serialized = ObjectJSONSerializer.serialize(c);
    assertEquals("{\"argb\":\"fff94144\"}", serialized);
    assertEquals(c, ObjectJSONSerializer.deserialize(serialized, Color.class));
  }

  @Test
  public void testSquareSerialization(){

  }
}
