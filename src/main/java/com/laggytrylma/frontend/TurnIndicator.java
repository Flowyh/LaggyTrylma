package com.laggytrylma.frontend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

import javax.swing.*;

public class TurnIndicator extends JLabel implements GameDisplayInterface{
    Game game;
    Player me = null;

    public TurnIndicator(){
        super();
    }

    @Override
    public void updateGame() {
        setText("Some random text");
        repaint();
    }

    @Override
    public void startGame(Game game) {
        this.game = game;
    }

    @Override
    public void setWhoAmI(Player player) {
        me = player;
    }
}
