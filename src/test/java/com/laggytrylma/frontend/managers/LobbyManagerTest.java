package com.laggytrylma.frontend.managers;

import com.laggytrylma.frontend.CommandMatcher;
import com.laggytrylma.frontend.communication.ClientSocket;
import com.laggytrylma.utils.communication.commands.models.ClientCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LobbyManagerTest {
    LobbyManager lm;
    LobbyDisplayInterface display;
    ClientSocket client;

    @Before
    public void setup(){
        lm = new LobbyManager();
        client = mock(ClientSocket.class);
        display = mock(LobbyDisplayInterface.class);
        lm.attachClientSocket(client);
        lm.attachLobbyDisplay(display);
    }

    @Test
    public void instantiationTest(){
        assertNotNull(lm);
    }

    @Test
    public void lobbiesListTest(){
        List<LobbyDescriptor> lobbies = new LinkedList<>();
        lm.lobbiesList(lobbies);
        verify(display).updateListAllLobbies(lobbies);
    }

    @Test
    public void requestUpdate(){
        Map<String, String> args = new HashMap<>();
        JSONCommandWrapper<?> expectedMessage = new JSONCommandWrapper<>(LobbyCommands.LIST_ALL, args);

        lm.requestUpdate();

        verify(client).sendMessage(argThat(new CommandMatcher(expectedMessage)));
    }
}
