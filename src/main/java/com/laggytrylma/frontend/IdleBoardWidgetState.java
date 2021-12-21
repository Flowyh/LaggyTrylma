package com.laggytrylma.frontend;

import java.awt.*;

public class IdleBoardWidgetState extends BoardWidgetState {

    public IdleBoardWidgetState(BoardWidget boardWidget) {
        super(boardWidget);
    }

    @Override
    void clickedOn(SquareDisplayWrapper wrapper) {
        if(wrapper != null && wrapper.occupied()){
            BoardWidgetStateSelected state = new BoardWidgetStateSelected(board);
            state.setSelected(wrapper);
            board.setState(state);
        }

    }

    @Override
    void draw(Graphics2D g2d) {
        for(SquareDisplayWrapper element : board.elements){
            element.draw(g2d);
        }
    }
}
