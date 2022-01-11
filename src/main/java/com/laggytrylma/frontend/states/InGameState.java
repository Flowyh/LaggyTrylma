package com.laggytrylma.frontend.states;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InGameState extends AbstractState{

    public InGameState(Context ctx) {
        super(ctx);
    }

    @Override
    public void disconnect() {
        leave();

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
    public void leave(){
        Map<String, String> args = new HashMap<>();
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(LobbyCommands.LEAVE, args);
        ctx.client.sendMessage(msg);

        ctx.state = new ConnectedState(ctx);
        getPageManager().pop();
    }
}
