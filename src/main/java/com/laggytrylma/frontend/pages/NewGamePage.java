package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.states.Context;

public class NewGamePage extends Page{
    public NewGamePage(Context ctx) {
        super(ctx);
        buildUI();
    }

    @Override
    void buildUI() {

    }

    @Override
    String getPageName() {
        return "NEW_GAME";
    }
}
