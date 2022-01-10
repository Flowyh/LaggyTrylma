package com.laggytrylma.frontend.states;

import com.laggytrylma.utils.Logger;

import java.io.IOException;

public class ConnectedState extends AbstractState {
    public ConnectedState(Context ctx) {
        super(ctx);
    }

    @Override
    public void disconnect() {
        try{
            ctx.client.close();
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return;
        }

        ctx.getPageManager().set("MENU");
        ctx.client = null;
        ctx.state = new DisconnectedState(ctx);
    }

    @Override
    public void join(int game_id){
        ctx.state = new InGameState(ctx);
        getPageManager().push("GAME");
    }
}
