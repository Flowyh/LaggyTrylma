package com.laggytrylma.frontend;

import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;

import java.util.List;

public interface LobbyDisplayInterface {
    void updateListAllLobbies(List<LobbyDescriptor> lobbies);
}
