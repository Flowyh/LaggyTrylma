package com.laggytrylma.frontend.states;

import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.ClientCommands;
import com.laggytrylma.utils.communication.commands.models.LobbyCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> args = new HashMap<>();
        args.put("id", Integer.toString(game_id));
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(LobbyCommands.JOIN, args);
        ctx.client.sendMessage(msg);

        // TODO: add confirmation
        ctx.state = new InGameState(ctx);
        getPageManager().push("GAME");
    }

    @Override
    public void createLobby(int playerCount){
        Map<String, String> args = new HashMap<>();
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(LobbyCommands.CREATE, args);
        ctx.client.sendMessage(msg);

        args = new HashMap<>();
        args.put("player_limit", Integer.toString(playerCount));
        msg = new JSONCommandWrapper<>(LobbyCommands.PLAYER_LIMIT, args);
        ctx.client.sendMessage(msg);

        // TODO: add confirmation
        ctx.state = new InGameState(ctx);
        ctx.getPageManager().push("GAME");
    }

    @Override
    public void replay(int id){

    }
}
