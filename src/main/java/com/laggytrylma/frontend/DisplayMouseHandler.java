package com.laggytrylma.frontend;

import com.laggytrylma.common.Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayMouseHandler extends MouseAdapter {
    protected final GameDisplay display;

    DisplayMouseHandler(GameDisplay display){
        super();
        this.display = display;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(SquareDisplayWrapper element : display.elements){
            if(element.containts(e.getPoint())){
                element.select();
                display.repaint();
                break;
            }
        }
    }

}

