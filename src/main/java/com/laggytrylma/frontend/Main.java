package com.laggytrylma.frontend;

import com.laggytrylma.frontend.pages.PageManager;
import com.laggytrylma.frontend.states.Context;
import com.laggytrylma.utils.Logger;

import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        super("Trylma");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Logger.setMessageBoxRoot(this);

        Context ctx = new Context();
        PageManager pm = new PageManager(this, ctx);
        ctx.setPageManager(pm);
        pm.push("MENU");

        setSize(400, 400);
    }

    public static void main(String[] args) {
        // antialiasing of fonts
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        Logger.setDepth(4);

        Main ui = new Main();
    }
}