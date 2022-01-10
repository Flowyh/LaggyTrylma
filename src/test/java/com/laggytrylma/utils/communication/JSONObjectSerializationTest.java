package com.laggytrylma.utils.communication;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.movementrules.FarMovement;
import com.laggytrylma.common.movementrules.MovementRulesInterface;
import com.laggytrylma.utils.communication.serializers.AbstractObjectSerializer;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class JSONObjectSerializationTest {

  @Test
  public void testObjectJSONSerializerInstance() throws NoSuchFieldException, IllegalAccessException {
    ObjectJSONSerializer test = ObjectJSONSerializer.getInstance();
    assertNotNull(test);
    // Singleton double checked-locking
    Field instance = ObjectJSONSerializer.class.getDeclaredField("instance");
    instance.setAccessible(true);
    instance.set(null, null);
    test = ObjectJSONSerializer.getInstance();
    assertNotNull(test);
  }

  @Test
  public void testObjectDeserializationFail() {
    String test = "1asda{asda{asd{23";
    assertNull(ObjectJSONSerializer.deserialize(test, Integer.class));
  }

  @Test
  public void testGameToJSON() {
    GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
    Game trylma = director.build();
    String serialized = ObjectJSONSerializer.serialize(trylma);
    Game trylma_deserialized = (Game)ObjectJSONSerializer.deserialize(serialized, Game.class);

    assertEquals(trylma.getSquares().size(), trylma_deserialized.getSquares().size());
    assertEquals(trylma.getRules().size(), trylma_deserialized.getRules().size());
    assertEquals(trylma.getPieces().size(), trylma_deserialized.getPieces().size());
    assertEquals(trylma.getPlayers().size(), trylma_deserialized.getPlayers().size());

    for(int i=0; i<trylma.getRules().size();i++){
      MovementRulesInterface r1 = trylma.getRules().get(i);
      MovementRulesInterface r2 = trylma_deserialized.getRules().get(i);
      assertEquals(r1.getClass(), r2.getClass());
    }

    for(Square square : trylma.getSquares()){
      Square square_des = trylma_deserialized.getSquareById(square.getId());
      if(square.occupied())
        assertEquals(square.getPiece().getId(), square_des.getPiece().getId());
      else
        assertNull(square_des.getPiece());

      for(int i = 0; i < square.getConnections().size(); i++){
        Connection c1 = square.getConnections().get(i);
        Connection c2 = square_des.getConnections().get(i);
        if(c1.near != null)
          assertEquals(c1.near.getId(), c2.near.getId());
        else
          assertNull(c2.near);

        if(c1.far != null)
          assertEquals(c1.far.getId(), c2.far.getId());
        else
          assertNull(c2.far);
      }

      assertEquals(square.getX(), square_des.getX(), 0.01);
      assertEquals(square.getY(), square_des.getY(), 0.01);

      if(square.getSpawn() != null){
        assertEquals(square.getSpawn().getId(), square_des.getSpawn().getId());
      }
      else{
        assertNull(square_des.getSpawn());
      }

      if(square.getTarget() != null){
        assertEquals(square.getTarget().getId(), square_des.getTarget().getId());
      }
      else{
        assertNull(square_des.getTarget());
      }
    }
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
  public void testRuleToJSON(){
    MovementRulesInterface rule = new FarMovement();
    String serialized = ObjectJSONSerializer.serialize(rule);
    assertEquals("{\"name\":\"com.laggytrylma.common.movementrules.FarMovement\"}", serialized);
    assertTrue(ObjectJSONSerializer.deserialize(serialized, MovementRulesInterface.class) instanceof MovementRulesInterface);
  }

  @Test
  public void testRuleToJSONFail(){
    String ruleSerializationFail = "{\"name\":\"com.bitcoin.miner\"}";
    assertNull(ObjectJSONSerializer.deserialize(ruleSerializationFail, MovementRulesInterface.class));
  }

  @Test
  public void testAbstractObjectSerializerStatics() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method instance = AbstractObjectSerializer.class.getDeclaredMethod("serialize", Object.class);
    instance.setAccessible(true);
    assertNull(instance.invoke(null, "123"));
    instance = AbstractObjectSerializer.class.getDeclaredMethod("deserialize", String.class, Class.class);
    instance.setAccessible(true);
    assertNull(instance.invoke(null, "123", String.class));
  }
}
