package com.laggytrylma.frontend.states;

import com.laggytrylma.frontend.pages.PageManager;

public class Context {
    AbstractState state = new DisconnectedState(this);
    private PageManager pm;

    public void connect(String address){
        state.connect(address);
    }

    public void disconnect(){
        state.disconnect();
    }

    public void join(int game_id){
        state.join(game_id);
    }

    public PageManager getPageManager() {
        return pm;
    }

    public void setPageManager(PageManager pm){
        this.pm = pm;
    }
}
