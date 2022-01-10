package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.states.Context;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LobbyPage extends Page{
    private JPanel gamesList;


    public LobbyPage(Context ctx) {
        super(ctx);
        buildUI();
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
        updateGames();

        JButton disconnectButton = new JButton("Disconnect");
        add(disconnectButton, "");
        disconnectButton.addActionListener((e) -> ctx.disconnect());
    }

    @Override
    String getPageName() {
        return "LOBBY";
    }

    private void updateGames(){
        gamesList.removeAll();
        for(int i =0 ; i<5; i++){
            JLabel ownerLabel = new JLabel("Owner");
            ownerLabel.setBackground(getBackground().darker());
            gamesList.add(ownerLabel, "height 50::");

            JLabel usersLabel = new JLabel("Users");
            usersLabel.setBackground(getBackground().darker());
            gamesList.add(usersLabel, "");

            JButton joinButton = new JButton("Join");
            gamesList.add(joinButton, "wrap");
            joinButton.addActionListener((e) -> ctx.join(0));
        }
    }
}
