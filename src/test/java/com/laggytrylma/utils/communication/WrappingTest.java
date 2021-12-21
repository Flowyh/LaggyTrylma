package com.laggytrylma.utils.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.laggytrylma.utils.communication.commands.models.Game;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class WrappingTest {
  @Test
  public void testSerialize() throws JsonProcessingException {
    HashMap<String, String> test = new HashMap<>();
    test.put("HELLO", "TESt");
    test.put("HELLO2", "TESt");
    test.put("HELLO3", "TESt");
    test.put("HELLO4", "TESt");
    JSONCommandWrapper<Game> cmdWrap = new JSONCommandWrapper<>(Game.UPDATE, test);
    String serialized = cmdWrap.serialize();
    assertEquals(
            "{\"model\":\"game\",\"command\":\"update\",\"args\":{\"HELLO2\":\"TESt\",\"HELLO\":\"TESt\",\"HELLO3\":\"TESt\",\"HELLO4\":\"TESt\"}}",
            serialized
    );
  }

  @Test
  public void testDeserialized() throws JsonProcessingException {
    HashMap<String, String> test = new HashMap<>();
    test.put("HELLO", "TESt");
    test.put("HELLO2", "TESt");
    test.put("HELLO3", "TESt");
    test.put("HELLO4", "TESt");
    JSONCommandWrapper<Game> cmdWrap = new JSONCommandWrapper<>(Game.UPDATE, test);
    String serialized = cmdWrap.serialize();
    JSONCommandWrapper<?> deserialized = new JSONCommandWrapper<>(serialized);
    assertEquals(
            "Model: game Command: update {HELLO2=TESt, HELLO=TESt, HELLO3=TESt, HELLO4=TESt}",
            deserialized.toString()
    );
  }
}
