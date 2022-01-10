package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Square;
import com.laggytrylma.frontend.SquareDisplayWrapper;

import java.awt.*;
import java.util.Set;

public class BoardWidgetStateSelected extends BoardWidgetState {
    SquareDisplayWrapper selected;
    Set<Square> visitable;
    public BoardWidgetStateSelected(BoardWidget board){
        super(board);
    }

    public void setSelected(SquareDisplayWrapper wrapper){
        selected = wrapper;
        visitable = board.game.getAllowedMoves(wrapper.getSquare().getPiece()); // LAW OF DEMETER!!!!
    }

    @Override
    void clickedOn(SquareDisplayWrapper wrapper) {
        if(wrapper == null)
            return;
        if(visitable.contains(wrapper.getSquare())){
            board.movePiece(selected.getSquare().getPiece(), wrapper.getSquare());
        }
        board.setState(new IdleBoardWidgetState(board));
    }

    @Override
    void draw(Graphics2D g2d) {
        for(SquareDisplayWrapper element : board.elements){
            element.draw(g2d);
            if(element == selected || visitable.contains(element.getSquare())){
                element.drawHighlighted(g2d);
            }
        }
    }

}
