package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.states.Context;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class NewGamePage extends Page{
    public NewGamePage(Context ctx) {
        super(ctx);
        buildUI();
    }

    @Override
    void buildUI() {
        setLayout(new MigLayout("fillx"));

        add(new JPanel(), "grow, wrap, height 50%"); //filler

        add(new JLabel("Number of players"), "wrap, height ::50, width 50%, center, sg label");

        SpinnerModel model = new SpinnerListModel(new String[] {"2","3","4","6"});
        JSpinner spinner = new JSpinner(model);
        add(spinner, "wrap, center, sg label");

        JButton createButton = new JButton("Create");
        add(createButton, "wrap, center, sg label");
        createButton.addActionListener((e) ->{
            ctx.getPageManager().pop();
            int playerCount = Integer.parseInt((String) spinner.getValue());
            ctx.createLobby(playerCount);
        });

        add(new JPanel(), "grow, wrap, height 50%"); //filler

        JButton leave = new JButton("Leave");
        add(leave);
        leave.addActionListener((e)->ctx.getPageManager().pop());
    }

    @Override
    String getPageName() {
        return "NEW_GAME";
    }
}
