package com.laggytrylma.frontend;

import com.laggytrylma.common.Connection;
import com.laggytrylma.common.Square;

import java.awt.*;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class SquareDisplayWrapper {
    final private Square square;
    final private Shape body;
    private Color color = new Color(127, 120, 0);

    final float radius = 12;
    final float scale = 30;

    public SquareDisplayWrapper(Square square){
        this.square = square;
        body = new Ellipse2D.Float(scale*square.getX(),scale*square.getY(), 2*radius, 2*radius);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fill(body);
    }

    public boolean containts(Point2D point){
        return body.contains(point);
    }

    public void select(){
        color = new Color(203, 28, 14);
    }
}
