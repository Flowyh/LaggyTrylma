package com.laggytrylma.frontend.communication;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.frontend.managers.GameManager;
import com.laggytrylma.frontend.managers.LobbyManager;
import com.laggytrylma.frontend.states.Context;
import com.laggytrylma.utils.communication.commands.models.*;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import com.laggytrylma.utils.communication.serializers.JSON.ObjectJSONSerializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageHandlerTest {
    MessageHandler mh;
    Context ctx;
    GameManager gm;
    LobbyManager lm;
    HashMap<String, String> args = new HashMap<>();

    private void sendCommand(IModelCommands command, Map<String, String> args) {
        JSONCommandWrapper<?> wrapper = new JSONCommandWrapper<>(command, args);
        UUID uuid = UUID.randomUUID();
        mh.processInput(wrapper, uuid);
    }

    @Before
    public void setup() {
        args.clear();
        ctx = mock(Context.class);
        gm = mock(GameManager.class);
        lm = mock(LobbyManager.class);
        mh = new MessageHandler(ctx, gm, lm);
    }

    @Test
    public void instantiationTest() {
        assertNotNull(mh);
    }

    @Test
    public void gameStartTest() {
        args.put("game", ObjectJSONSerializer.serialize(new Game()));
        sendCommand(GameCommands.START, args);
        verify(gm).startGame(any());
    }

    @Test
    public void gameNextTest() {
        args.put("player", "0");
        sendCommand(GameCommands.NEXT, args);
        verify(gm).setCurrentPlayer(0);
    }

    @Test
    public void gameMoveTest() {
        args.put("piece", "0");
        args.put("destination", "1");
        sendCommand(GameCommands.MOVE, args);
        verify(gm).remoteMove(0, 1);
    }

    @Test
    public void gamePlayerInfoTest() {
        args.put("player", "0");
        sendCommand(GameCommands.PLAYER_INFO, args);
        verify(gm).assignPlayer(0);
    }

    @Test
    public void gameInfoTest() {
        sendCommand(GameCommands.GAME_INFO, args);
    }


    @Test
    public void gameWinTest() {
        args.put("player", "0");
        sendCommand(GameCommands.WIN, args);
        verify(gm).win(0);
    }

    @Test
    public void clientTest(){
        sendCommand(ClientCommands.NICKNAME, args);
    }

    @Test
    public void lobbyListTest(){
        args.put("0", "{\"owner\":\"jacek\", \"players\":5}");
        sendCommand(LobbyCommands.LIST_ALL, args);
        verify(lm).lobbiesList(argThat(new LobbiesListMatcher()));
    }

    @Test
    public void lobbyDelete(){
        sendCommand(LobbyCommands.DELETE, args);
        verify(ctx).leave();
    }

    static class LobbiesListMatcher implements ArgumentMatcher<List<LobbyDescriptor>>{

        @Override
        public boolean matches(List<LobbyDescriptor> lobbyDescriptors) {
            if(lobbyDescriptors.size() != 1)
                return false;

            LobbyDescriptor lb = lobbyDescriptors.get(0);
            if(!lb.getOwner().equals("jacek"))
                return false;
            if(lb.getPlayersCount() != 5)
                return false;
            if(lb.getId() != 0)
                return false;
            return true;
        }
    }
}
