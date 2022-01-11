package com.laggytrylma.utils.communication;

import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LobbyDescriptorTest {
  @Test
  public void testLobbyDescriptor() {
    LobbyDescriptor test = new LobbyDescriptor(1, "1", 2);
    assertEquals(test.getId(), 1);
    assertEquals(test.getOwner(), "1");
    assertEquals(test.getPlayersCount(), 2);
  }
}
