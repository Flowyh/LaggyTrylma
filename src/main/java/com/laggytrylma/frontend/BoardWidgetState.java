package com.laggytrylma.frontend;

import java.awt.*;

public abstract class BoardWidgetState {
    protected BoardWidget board;
    BoardWidgetState(BoardWidget board){
        this.board = board;
    }
    abstract void clickedOn(SquareDisplayWrapper wrapper);
    abstract void draw(Graphics2D g2d);
}
