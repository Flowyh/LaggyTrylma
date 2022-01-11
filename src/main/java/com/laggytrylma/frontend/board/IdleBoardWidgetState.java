package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Player;
import com.laggytrylma.frontend.board.SquareDisplayWrapper;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class IdleBoardWidgetState extends BoardWidgetState {

    public IdleBoardWidgetState(BoardWidget boardWidget) {
        super(boardWidget);
    }

    @Override
    void clickedOn(SquareDisplayWrapper wrapper) {
        boolean myTurn = board.me == board.game.getCurrentPlayer();
        if(wrapper != null && wrapper.occupied() && myTurn){
            Player owner = wrapper.getSquare().getPiece().getOwner();
            if(owner == board.me){
                BoardWidgetStateSelected state = new BoardWidgetStateSelected(board);
                state.setSelected(wrapper);
                board.setState(state);
            }
        }
    }

    @Override
    void draw(Graphics2D g2d) {
        for(SquareDisplayWrapper element : board.elements){
            element.draw(g2d);
        }
    }
}
