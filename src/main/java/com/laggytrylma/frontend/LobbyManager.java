package com.laggytrylma.frontend;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LobbyManager {
    private List<LobbyDisplayInterface> lobbyDisplays = new LinkedList<>();
    private ClientSocket clientSocket;

    public void attachClientSocket(ClientSocket client){
        this.clientSocket = client;
    };

    public void attachLobbyDisplay(LobbyDisplayInterface lobbyDisplay){
        lobbyDisplays.add(lobbyDisplay);
    }

    public void lobbiesList(List<LobbyDescriptor> lobbies){
        for(LobbyDisplayInterface lobbyDisplay : lobbyDisplays){
            lobbyDisplay.updateListAllLobbies(lobbies);
        }
    }

    public void requestUpdate() {
        Map<String, String> args = new HashMap<>();
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(LobbyCommands.LIST_ALL, args);
        clientSocket.sendMessage(msg);
    }
}
