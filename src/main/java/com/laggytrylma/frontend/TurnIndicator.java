package com.laggytrylma.frontend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

import javax.swing.*;
import java.awt.*;

public class TurnIndicator extends JLabel implements GameDisplayInterface{
    Game game;
    Player me = null;

    public TurnIndicator(){
        super();
        setText(" ");
    }

    @Override
    public void updateGame() {
        Player activePlayer = game.getCurrentPlayer();
        if(activePlayer != null){
            if(activePlayer == me){
                setText("Your turn!");
            } else{
                setText(activePlayer.getName() + "'s turn");
            }
            setForeground(activePlayer.getColor());
        } else{
            setText(" ");
            setForeground(Color.BLACK);
        }

        repaint();
    }

    @Override
    public void startGame(Game game) {
        this.game = game;
        updateGame();
    }

    @Override
    public void setWhoAmI(Player player) {
        me = player;
        updateGame();
    }
}
