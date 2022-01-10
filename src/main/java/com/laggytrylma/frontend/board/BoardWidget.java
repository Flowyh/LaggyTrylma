package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.frontend.GameDisplayInterface;
import com.laggytrylma.frontend.LocalGameInput;
import com.laggytrylma.utils.Logger;


import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.min;

public class BoardWidget extends JPanel implements GameDisplayInterface {
    Game game;
    LocalGameInput control;
    DisplayMouseHandler mouseHandler = new DisplayMouseHandler(this);
    BoardWidgetState state = new IdleBoardWidgetState(this);
    Player me = null;
    AffineTransform af = new AffineTransform();

    public List<SquareDisplayWrapper> elements = new LinkedList<>();


    public BoardWidget(){
        super();
        addMouseListener(mouseHandler);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);

        if(game == null)
            return;

        computeAffineTransform();
        g2d.transform(af);
        state.draw(g2d);
    }

    public void attachControl(LocalGameInput control){
        this.control = control;
    }

    public void setState(BoardWidgetState state) {
        this.state = state;
    }

    public void clickedOn(SquareDisplayWrapper wrapper){
        state.clickedOn(wrapper);
    }

    public void movePiece(Piece piece, Square square) {
        control.localMove(piece, square);
    }

    @Override
    public void updateGame() {
        repaint();
    }

    @Override
    public void startGame(Game game) {
        Logger.debug("Widget starting game");
        elements.clear();
        this.game = game;
        for(Square square : game.getSquares()){
            elements.add(new SquareDisplayWrapper(square));
        }
        repaint();
    }

    @Override
    public void setWhoAmI(Player player) {
        me = player;
    }

    protected void computeAffineTransform() {
        int height = getHeight();
        int width = getWidth();

        int shorterEdge = min(height, width);
        AffineTransform af = new AffineTransform();
        float scale = (float)shorterEdge;

        float tx = (width - shorterEdge) / 2f;
        float ty = (height - shorterEdge) / 2f;
        af.translate(tx, ty);
        af.scale(scale, scale);
        this.af = af;
    }

    protected AffineTransform getAffineTransform(){
        return af;
    }
}
