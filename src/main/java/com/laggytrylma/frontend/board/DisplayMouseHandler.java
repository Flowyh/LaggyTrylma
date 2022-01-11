package com.laggytrylma.frontend.board;

import com.laggytrylma.utils.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class DisplayMouseHandler extends MouseAdapter {
    protected final BoardWidget board;

    DisplayMouseHandler(BoardWidget board){
        super();
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!board.active()){
            return;
        }
        for(SquareDisplayWrapper element : board.elements){
            Point2D transformed = new Point2D.Float();
            try {
                board.getAffineTransform().inverseTransform(e.getPoint(), transformed);
            } catch (NoninvertibleTransformException ex) {
                Logger.error(ex.getMessage());
                return;
            }

            if(element.contains(transformed)){
                board.clickedOn(element);
                board.repaint();
                return;
            }
        }
        board.clickedOn(null);
        board.repaint();

    }

}

