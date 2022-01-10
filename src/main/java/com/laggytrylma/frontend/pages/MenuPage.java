package com.laggytrylma.frontend.pages;

import javax.swing.*;

import com.laggytrylma.frontend.states.Context;
import net.miginfocom.swing.MigLayout;

import java.awt.*;

public class MenuPage extends Page{
    public MenuPage(Context ctx){
        super(ctx);
        buildUI();
    }

    @Override
    void buildUI() {
        this.setLayout(new MigLayout("fillx"));

        this.add(new JPanel(), "grow, wrap, height 50%"); //filler

        JLabel title = new JLabel("LaggyTrylma");
        title.setFont(new Font("Open Sans Light", Font.BOLD, 30));
        this.add(title, "center, gapbottom 30, wrap");

        JTextField address = new JTextField("127.0.0.1:21376");
        address.setFont(new Font("Open Sans Light", Font.PLAIN, 15));
        this.add(address, "wrap, height ::50, width 50%, center, sg button");

        JButton connect = new JButton("Connect");
        connect.setFont(new Font("Open Sans Light", Font.PLAIN, 15));
        connect.addActionListener( (e) -> {
                String addr = address.getText();
                ctx.connect(addr);
        });

        this.add(connect, "sg button, center, wrap");

        this.add(new JPanel(), "grow, height 50%"); //filler
    }

    @Override
    String getPageName() {
        return "MENU";
    }
}
