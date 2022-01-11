package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.awt.*;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class SquareDisplayWrapper {
    final private Square square;
    final private Shape body;
    private Color empty_color = new Color(83, 83, 83);

    final float radius = 0.025f;

    public SquareDisplayWrapper(Square square){
        this.square = square;
        body = new Ellipse2D.Float(square.getX(),square.getY(), 2*radius, 2*radius);
    }

    private Color getColor(){
        Piece piece = square.getPiece();
        if(piece == null){
            return empty_color;

        } else{
            return piece.getColor();
        }
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(getColor());
        g2d.fill(body);
    }

    public void drawHighlighted(Graphics2D g2d){
        g2d.setColor(getColor().brighter());
        g2d.setStroke(new BasicStroke(0.007f));
        g2d.draw(body);
    }

    public boolean contains(Point2D point){
        return body.contains(point);
    }

    public Square getSquare(){
        return square;
    }

    public boolean occupied(){
        return square.occupied();
    }

}
