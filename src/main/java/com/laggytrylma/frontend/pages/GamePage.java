package com.laggytrylma.frontend.pages;

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
        this.add(title, "center, gapbottom 30, wrap, height 10%");

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
