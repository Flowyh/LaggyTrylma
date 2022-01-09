package com.laggytrylma.frontend.board;

import com.laggytrylma.common.Game;
import com.laggytrylma.common.Square;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

public class BoardWidget extends JPanel {
    Game game;
    DisplayMouseHandler mouseHandler = new DisplayMouseHandler(this);
    public List<SquareDisplayWrapper> elements = new LinkedList<>();
    BoardWidgetState state = new IdleBoardWidgetState(this);

    public BoardWidget(){
        addMouseListener(mouseHandler);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);

        if(game == null)
            return;

        state.draw(g2d);
    }

    public void attachGame(Game game){
        this.game = game;
        for(Square square : game.getSquares()){
            elements.add(new SquareDisplayWrapper(square));
        }
    }

    public void setState(BoardWidgetState state) {
        this.state = state;
    }

    public void clickedOn(SquareDisplayWrapper wrapper){
        state.clickedOn(wrapper);
    }
}
