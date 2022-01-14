package com.laggytrylma.frontend.states;
import com.laggytrylma.frontend.communication.ClientSocket;
import com.laggytrylma.frontend.managers.GameManager;
import com.laggytrylma.frontend.managers.LobbyManager;
import com.laggytrylma.frontend.pages.PageManager;

public class Context {
    AbstractState state = new DisconnectedState(this);
    GameManager gm = new GameManager();
    LobbyManager lm = new LobbyManager();
    private PageManager pm;
    public ClientSocket client;

    public void connect(String address, String nick){
        state.connect(address, nick);
    }

    public void disconnect(){
        state.disconnect();
    }

    public void join(int game_id){
        state.join(game_id);
    }

    public void leave() {state.leave();};

    public PageManager getPageManager() {
        return pm;
    }

    public GameManager getGameManager(){return gm;}

    public LobbyManager getLobbyManager(){return lm;};

    public void setPageManager(PageManager pm){
        this.pm = pm;
    }


    public void createLobby(int playerCount) {
        state.createLobby(playerCount);
    }
}
