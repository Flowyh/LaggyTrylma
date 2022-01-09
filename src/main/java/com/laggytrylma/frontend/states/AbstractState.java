package com.laggytrylma.frontend.states;

import com.laggytrylma.frontend.pages.PageManager;

public abstract class AbstractState {
    private Context ctx;

    public AbstractState(Context ctx){
        this.ctx = ctx;
    }
    public void connect(String addrress){};
    public void disconnect(){};
    public void join(int game_id){};

    protected PageManager getPageManager(){
        return ctx.getPageManager();
    }

}
