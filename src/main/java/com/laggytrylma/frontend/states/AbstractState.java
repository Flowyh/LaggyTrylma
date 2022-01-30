package com.laggytrylma.frontend.states;

import com.laggytrylma.frontend.pages.PageManager;
import com.laggytrylma.utils.Logger;

public abstract class AbstractState {
    protected Context ctx;

    public AbstractState(Context ctx){
        this.ctx = ctx;
    }
    public void connect(String address, String nick){
    };
    public void disconnect(){
    };
    public void join(int game_id){    };

    public void leave() {

    };

    protected PageManager getPageManager(){
        return ctx.getPageManager();
    }

    public void createLobby(int playerCount){};

    public void replay(String id){};
}
