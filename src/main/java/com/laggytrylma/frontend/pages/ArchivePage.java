package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.managers.LobbyDisplayInterface;
import com.laggytrylma.frontend.states.Context;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.ArchiveGameDescriptor;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ArchivePage extends Page implements LobbyDisplayInterface{
    private JPanel gamesList;


    public ArchivePage(Context ctx) {
        super(ctx);
        buildUI();
        ctx.getLobbyManager().attachLobbyDisplay(this);
    }

    @Override
    public void onOpen(){
        Map<String, String> args = new HashMap<>();
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(LobbyCommands.LIST_ARCHIVE, args);
        ctx.client.sendMessage(msg);
    }

    @Override
    void buildUI() {
        this.setLayout(new MigLayout("fillx"));

        JLabel title = new JLabel("Games Archive");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 30));
        this.add(title, "center, gapbottom 30, wrap");

        gamesList = new JPanel();
        this.add(gamesList, "grow, wrap, height 90%");
        gamesList.setLayout(new MigLayout("fillx", "[l][c][r]", ""));
        updateArchive(new LinkedList<>());

        JButton backButton = new JButton("Back");
        add(backButton, "");
        backButton.addActionListener((e) -> ctx.getPageManager().pop());
    }

    @Override
    String getPageName() {
        return "ARCHIVE";
    }


    @Override
    public void updateListAllLobbies(List<LobbyDescriptor> lobbies) {}

    @Override
    public void updateArchive(List<ArchiveGameDescriptor> archivedGames) {
        gamesList.removeAll();
        for(ArchiveGameDescriptor game : archivedGames){
            JLabel dateLabel = new JLabel("Date: " + game.getDate());
            dateLabel.setBackground(getBackground().darker());
            gamesList.add(dateLabel, "height 50::");

            JLabel usersLabel = new JLabel("");
            usersLabel.setBackground(getBackground().darker());
            gamesList.add(usersLabel, "");

            JButton joinButton = new JButton("Replay");
            gamesList.add(joinButton, "wrap");
            joinButton.addActionListener((e) -> ctx.replay(game.getId()));
        }
        updateUI();
        repaint();
    }
}
