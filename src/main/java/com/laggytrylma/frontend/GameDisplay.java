package com.laggytrylma.frontend;

import com.laggytrylma.common.Game;
import com.laggytrylma.common.Square;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

public class GameDisplay extends JPanel {
    Game game;
    DisplayMouseHandler mouseHandler = new DisplayMouseHandler(this);
    public List<SquareDisplayWrapper> elements = new LinkedList<>();

    public GameDisplay(){
        addMouseListener(mouseHandler);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);
        if(game == null)
            return;
        for(SquareDisplayWrapper element : elements){
            element.draw(g2d);
        }
    }

    public void attachGame(Game game){
        this.game = game;
        for(Square square : game.getSquares()){
            elements.add(new SquareDisplayWrapper(square));
        }
    }
}
