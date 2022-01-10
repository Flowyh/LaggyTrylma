package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.TurnIndicator;
import com.laggytrylma.frontend.board.BoardWidget;
import com.laggytrylma.frontend.states.Context;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class GamePage extends Page{
    public GamePage(Context ctx){
        super(ctx);
        buildUI();
    }

    @Override
    void buildUI() {
        this.setLayout(new MigLayout("fill"));

        JLabel title = new JLabel("GamePanel");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 30));
        this.add(title, "center, wrap, height 10%");

//        TurnIndicator turnIndicator = new TurnIndicator();
//        this.add(turnIndicator, "wrap");

        BoardWidget display = new BoardWidget();
        this.add(display, "grow, height 90%");
        ctx.getGameManager().attachGameDisplay(display);
        display.attachControl(ctx.getGameManager());
    }

    @Override
    String getPageName() {
        return "GAME";
    }
}
