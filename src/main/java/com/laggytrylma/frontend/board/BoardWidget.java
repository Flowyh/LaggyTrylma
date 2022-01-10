package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.frontend.GameDisplayInterface;
import com.laggytrylma.frontend.LocalGameInput;
import com.laggytrylma.frontend.SquareDisplayWrapper;
import com.laggytrylma.utils.Logger;


import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

public class BoardWidget extends JPanel implements GameDisplayInterface {
    Game game;
    LocalGameInput control;
    DisplayMouseHandler mouseHandler = new DisplayMouseHandler(this);
    BoardWidgetState state = new IdleBoardWidgetState(this);

    public List<SquareDisplayWrapper> elements = new LinkedList<>();


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

    public void attachControl(LocalGameInput control){
        this.control = control;
    }

    public void setState(BoardWidgetState state) {
        this.state = state;
    }

    public void clickedOn(SquareDisplayWrapper wrapper){
        state.clickedOn(wrapper);
    }

    public void movePiece(Piece piece, Square square) {
        control.localMove(piece, square);
    }

    @Override
    public void updateGame() {
        repaint();
    }

    @Override
    public void startGame(Game game) {
        Logger.debug("Widget starting game");
        elements.clear();
        this.game = game;
        for(Square square : game.getSquares()){
            elements.add(new SquareDisplayWrapper(square));
        }
        repaint();
    }
}
