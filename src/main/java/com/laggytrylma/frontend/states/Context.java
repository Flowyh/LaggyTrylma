package com.laggytrylma.frontend.states;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.frontend.ClientSocket;
import com.laggytrylma.frontend.GameManager;
import com.laggytrylma.frontend.pages.PageManager;

public class Context {
    AbstractState state = new DisconnectedState(this);
    GameManager gm = new GameManager();

    private PageManager pm;
    public ClientSocket client;

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

    public GameManager getGameManager(){return gm;}

    public void setPageManager(PageManager pm){
        this.pm = pm;
    }

}
