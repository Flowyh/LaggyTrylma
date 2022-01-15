package com.laggytrylma.utils.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandSerializer;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class JSONCommandWrappingTest {
  @Test
  public void testSerialize() {
    HashMap<String, String> test = new HashMap<>();
    test.put("HELLO", "TESt");
    test.put("HELLO2", "TESt");
    test.put("HELLO3", "TESt");
    test.put("HELLO4", "TESt");
    JSONCommandWrapper<GameCommands> cmdWrap = new JSONCommandWrapper<>(GameCommands.MOVE, test);
    String serialized = cmdWrap.serialize();
    assertEquals(
            "{\"model\":\"game\",\"command\":\"move\",\"args\":{\"HELLO2\":\"TESt\",\"HELLO\":\"TESt\",\"HELLO3\":\"TESt\",\"HELLO4\":\"TESt\"}}",
            serialized
    );
  }

  @Test
  public void testDeserialized()  {
    HashMap<String, String> test = new HashMap<>();
    test.put("HELLO", "TESt");
    test.put("HELLO2", "TESt");
    test.put("HELLO3", "TESt");
    test.put("HELLO4", "TESt");
    JSONCommandWrapper<GameCommands> cmdWrap = new JSONCommandWrapper<>(GameCommands.MOVE, test);
    String serialized = cmdWrap.serialize();
    JSONCommandWrapper<?> deserialized = new JSONCommandWrapper<>(serialized);
    assertEquals(
            "Model: game Command: move {HELLO2=TESt, HELLO=TESt, HELLO3=TESt, HELLO4=TESt}",
            deserialized.toString()
    );
  }

  @Test
  public void testSerializedConstructorFail() {
    JSONCommandWrapper<?> test = new JSONCommandWrapper<>("asd{asda{!123");
    assertNull(test.getCommand());
    assertNull(test.getArgs());
  }

  @Test
  public void testEmptySerialize() {
    BaseCommandWrapper<?> test = mock(BaseCommandWrapper.class);
    doCallRealMethod().when(test).serialize();
    String res = test.serialize();
    assertNull(res);
    verify(test).serialize();
  }

  @Test
  public void testSerializeFail() {
    Map<String, String > args = new HashMap<>();
    IModelCommands mockCmd = new IModelCommands() {
      @Override
      public String command() {
        return (String) new Object();
      }

      @Override
      public String model() {
        return (String) new Object();
      }
    };
    JSONCommandWrapper<?> test = new JSONCommandWrapper<>(mockCmd, args);
    String res = test.serialize();
    assertNull(res);
  }
}
