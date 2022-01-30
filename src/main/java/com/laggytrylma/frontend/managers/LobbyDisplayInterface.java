package com.laggytrylma.frontend.managers;

import com.laggytrylma.utils.communication.ArchiveGameDescriptor;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;

import java.util.List;

public interface LobbyDisplayInterface {
    void updateListAllLobbies(List<LobbyDescriptor> lobbies);
    void updateArchive(List<ArchiveGameDescriptor> archivedGames);
}
