package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.states.Context;

import javax.swing.*;

public abstract class Page extends JPanel {
    public Page(Context ctx) {
        this.ctx = ctx;
    }

    protected Context ctx;

    abstract void buildUI();
    abstract String getPageName();
    void onClose() {};
    void onOpen() {};
}
