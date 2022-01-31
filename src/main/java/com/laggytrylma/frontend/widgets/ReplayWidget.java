package com.laggytrylma.frontend.widgets;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.GameReplay;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.frontend.managers.GameDisplayInterface;
import com.laggytrylma.frontend.managers.GameManager;
import com.laggytrylma.utils.Logger;

import javax.swing.*;
import java.awt.*;

public class ReplayWidget extends JPanel implements GameDisplayInterface {
    GameReplay game;
    GameManager gm;
    Player me = null;

    public ReplayWidget(){
        super();
        buildUI();
    }

    private void buildUI(){
        setVisible(false);
        JButton button_left = new JButton("previous");
        button_left.addActionListener((e) -> {
            game.previousMove();
            gm.updateDisplays();
        });
        add(button_left);

        JButton button_right = new JButton("next");
        button_right.addActionListener((e) -> {
            game.nextMove();
            gm.updateDisplays();
        });
        add(button_right);
    }

    @Override
    public void updateGame() {
        repaint();
    }

    @Override
    public void startGame(Game game) {
        Logger.debug("Starting replay widget");
        setVisible(false);
        if(!(game instanceof GameReplay))
            return;
        setVisible(true);
        this.game = (GameReplay) game;
        updateGame();
        repaint();
    }

    @Override
    public void setWhoAmI(Player player) {

    }

    @Override
    public void removeGame() {
        setVisible(false);
        repaint();
    }

    @Override
    public void win(Player winner) {

    }

    public void attachControl(GameManager gameManager) {
        gm = gameManager;
    }
}
