package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.widgets.ReplayWidget;
import com.laggytrylma.frontend.widgets.TurnIndicator;
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

        JLabel title = new JLabel("LaggyTrylma");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 60));
        add(title, "center, wrap, span, height 10%");

        TurnIndicator turnIndicator = new TurnIndicator();
        turnIndicator.setFont(new Font("Open Sans", Font.BOLD, 30));
        add(turnIndicator, "growx");
        ctx.getGameManager().attachGameDisplay(turnIndicator);

        ReplayWidget replayPanel = new ReplayWidget();
        replayPanel.setFont(new Font("Open Sans", Font.BOLD, 30));
        add(replayPanel, "wrap, align right");
        ctx.getGameManager().attachGameDisplay(replayPanel);
        replayPanel.attachControl(ctx.getGameManager());

        BoardWidget display = new BoardWidget();
        add(display, "span, grow, height 90%, wrap");
        ctx.getGameManager().attachGameDisplay(display);
        display.attachControl(ctx.getGameManager());

        JButton leave = new JButton("Leave");
        leave.addActionListener((e) -> ctx.leave());
        add(leave);
    }

    @Override
    String getPageName() {
        return "GAME";
    }
}
