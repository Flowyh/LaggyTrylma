package com.laggytrylma.utils.communication;

import com.laggytrylma.utils.communication.commands.AbstractCommandHandler;
import com.laggytrylma.utils.communication.commands.ModelCommandsEnumBuilder;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commands.models.IModelCommands;
import com.laggytrylma.utils.communication.commandwrappers.BaseCommandWrapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommandsModelsTest {
  @Test
  public void testAbstractCommandHandler() {
    AbstractCommandHandler test = mock(AbstractCommandHandler.class);
    doCallRealMethod().when(test).processInput(any(), any());
    when(test.handleCommand(any(), any(), any())).thenReturn(1);
    UUID random = UUID.randomUUID();
    Object o = "1";
    assertEquals(0, test.processInput(o, random));
    o = new BaseCommandWrapper<>("1");
    assertEquals(0, test.processInput(o, random));
    Map<String, String> args = new HashMap<>();
    args.put("test", "test");
    o = new BaseCommandWrapper<>(GameCommands.START, args);
    assertEquals(1, test.processInput(o, random));
  }

  @Test
  public void testEnumBuilder() {
    IModelCommands test = ModelCommandsEnumBuilder.buildEnum("???", "1");
    assertNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("game", "1");
    assertNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("game", "start");
    assertNotNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("lobby", "1");
    assertNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("lobby", "create");
    assertNotNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("client", "1");
    assertNull(test);
    test = ModelCommandsEnumBuilder.buildEnum("client", "create");
    assertNotNull(test);
  }

  @Test
  public void testCommandsStrings() {
    IModelCommands test = ModelCommandsEnumBuilder.buildEnum("game", "start");
    assertNotNull(test);
    assertEquals(test.model(), "game");
    assertEquals(test.command(), "start");
    test = ModelCommandsEnumBuilder.buildEnum("lobby", "create");
    assertNotNull(test);
    assertEquals(test.model(), "lobby");
    assertEquals(test.command(), "create");
    test = ModelCommandsEnumBuilder.buildEnum("client", "create");
    assertNotNull(test);
    assertEquals(test.model(), "client");
    assertEquals(test.command(), "create");
  }
}
