package com.laggytrylma.frontend;

import com.laggytrylma.frontend.pages.PageManager;
import com.laggytrylma.frontend.states.Context;

import javax.swing.*;

public class TestUI extends JFrame{

    public TestUI(){
        super("Trylma");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        Context ctx = new Context();
        PageManager pm = new PageManager(this, ctx);
        ctx.setPageManager(pm);
        pm.push("MENU");
//        pm.push("GAME");
//        pm.pop();
//        pm.push("GAME");


        setSize(400, 400);
    }

    public static void main(String[] args) {
        // antialiasing of fonts
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            TestUI ui = new TestUI();
        });

        while(true);
    }
}