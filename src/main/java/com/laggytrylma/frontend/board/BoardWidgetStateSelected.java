package com.laggytrylma.frontend.board;

import com.laggytrylma.common.Square;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

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
            board.game.move(selected.getSquare().getPiece(), wrapper.getSquare());
//            JSONCommandWrapper<GameCommands> request = new JSONCommandWrapper<>(GameCommands.UPDATE, )
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
