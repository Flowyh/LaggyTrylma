package com.laggytrylma.frontend.states;

import com.laggytrylma.frontend.pages.PageManager;
import com.laggytrylma.utils.Logger;

public abstract class AbstractState {
    protected Context ctx;

    public AbstractState(Context ctx){
        this.ctx = ctx;
    }
    public void connect(String address){
        Logger.error("This state can't connect");
    };
    public void disconnect(){
        Logger.error("This state can't disconnect");
    };
    public void join(int game_id){
        Logger.error("This state can't join");
    };

    public void leave() {
        Logger.error("This state can't leave");
    };

    protected PageManager getPageManager(){
        return ctx.getPageManager();
    }

}
