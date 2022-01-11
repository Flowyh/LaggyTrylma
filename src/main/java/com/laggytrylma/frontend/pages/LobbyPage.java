package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.LobbyDisplayInterface;
import com.laggytrylma.frontend.states.Context;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.LobbyDescriptor;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;


public class LobbyPage extends Page implements LobbyDisplayInterface {
    private JPanel gamesList;
    private Timer timer;


    public LobbyPage(Context ctx) {
        super(ctx);
        buildUI();
        ctx.getLobbyManager().attachLobbyDisplay(this);
    }

    @Override
    public void onOpen(){
        requestUpdate();
        timer = new Timer(3000, (e)->requestUpdate());
        timer.start();
    }

    @Override
    public void onClose(){
        timer.stop();

    }

    @Override
    void buildUI() {
        this.setLayout(new MigLayout("fillx"));

        JLabel title = new JLabel("LaggyTrylma - Lobby");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 30));
        this.add(title, "center, gapbottom 30, wrap");

        JButton newGameButton = new JButton("New game");
        newGameButton.setFont(new Font("Open Sans Light", Font.PLAIN, 15));
        newGameButton.addActionListener((e) -> ctx.getPageManager().push("NEW_GAME"));
        this.add(newGameButton, "sg button, center, wrap");

        gamesList = new JPanel();
        this.add(gamesList, "grow, wrap, height 90%");
        gamesList.setLayout(new MigLayout("fillx", "[l][c][r]", ""));
        updateGames(new LinkedList<>());

        JButton disconnectButton = new JButton("Disconnect");
        add(disconnectButton, "");
        disconnectButton.addActionListener((e) -> ctx.disconnect());
    }

    @Override
    String getPageName() {
        return "LOBBY";
    }

    private void updateGames(List<LobbyDescriptor> lobbies){
        gamesList.removeAll();
        for(LobbyDescriptor lobby : lobbies){
            JLabel ownerLabel = new JLabel("Host: " + lobby.getOwner());
            ownerLabel.setBackground(getBackground().darker());
            gamesList.add(ownerLabel, "height 50::");

            JLabel usersLabel = new JLabel("Players: " + lobby.getPlayersCount());
            usersLabel.setBackground(getBackground().darker());
            gamesList.add(usersLabel, "");

            JButton joinButton = new JButton("Join");
            gamesList.add(joinButton, "wrap");
            joinButton.addActionListener((e) -> ctx.join(lobby.getId()));
        }
        updateUI();
        repaint();
    }

    private void requestUpdate(){
        ctx.getLobbyManager().requestUpdate();
        Logger.info("Request update");
    }

    @Override
    public void updateListAllLobbies(List<LobbyDescriptor> lobbies) {
        updateGames(lobbies);
    }
}
