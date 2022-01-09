package com.laggytrylma.frontend.board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayMouseHandler extends MouseAdapter {
    protected final BoardWidget display;

    DisplayMouseHandler(BoardWidget display){
        super();
        this.display = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(SquareDisplayWrapper element : display.elements){
            if(element.contains(e.getPoint())){
                display.clickedOn(element);
                display.repaint();
                return;
            }
        }
        display.clickedOn(null);
        display.repaint();

    }

}

