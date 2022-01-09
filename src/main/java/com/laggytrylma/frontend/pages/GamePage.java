package com.laggytrylma.frontend.pages;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.frontend.board.BoardWidget;
import com.laggytrylma.frontend.states.Context;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class GamePage extends Page{
    Game game;
    public GamePage(Context ctx){
        super(ctx);

        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        game = director.build();

        buildUI();
    }

    @Override
    void buildUI() {
        this.setLayout(new MigLayout("fillx"));

        JLabel title = new JLabel("GamePanel");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 30));
        this.add(title, "center, gapbottom 30, wrap");

        BoardWidget display = new BoardWidget();
        display.attachGame(game);
        this.add(display, "center, height 500::, width 500::");
    }

    @Override
    String getPageName() {
        return "GAME";
    }
}
