package com.laggytrylma.frontend.board;

import com.laggytrylma.frontend.SquareDisplayWrapper;

import java.awt.*;

public class IdleBoardWidgetState extends BoardWidgetState {

    public IdleBoardWidgetState(BoardWidget boardWidget) {
        super(boardWidget);
    }

    @Override
    void clickedOn(SquareDisplayWrapper wrapper) {
        boolean myTurn = board.me == board.game.getCurrentPlayer();
        if(wrapper != null && wrapper.occupied() && myTurn){
            if(wrapper.getSquare().getPiece().getOwner() == board.me){
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
