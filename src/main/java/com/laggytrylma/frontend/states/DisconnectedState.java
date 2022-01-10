package com.laggytrylma.frontend.states;

import com.laggytrylma.frontend.ClientSocket;

import com.laggytrylma.frontend.MessageHandler;
import com.laggytrylma.utils.Logger;

import java.io.IOException;
import java.net.Socket;

public class DisconnectedState extends AbstractState{
    public DisconnectedState(Context ctx){
        super(ctx);
    }

    @Override
    public void connect(String address){
        Logger.debug("Connecting to: " + address);

        try{
            Socket socket = new Socket("127.0.0.1", 21375);
            ctx.client = new ClientSocket(socket);



        } catch (IOException e) {
            Logger.error("Connection failed");
            return;
        }

        MessageHandler messageHandler = new MessageHandler(ctx.getGameManager());
        ctx.client.setSocketHandler(messageHandler);
        ctx.getGameManager().attachClientSocket(ctx.client);
        Thread thread = new Thread(ctx.client, "ClientSocket");
        thread.start();

        getPageManager().push("GAME");
    }
}
