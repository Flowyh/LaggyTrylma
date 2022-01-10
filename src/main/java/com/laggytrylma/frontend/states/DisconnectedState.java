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

        String[] parts = address.split(":");
        String host;
        int port;
        if(parts.length == 1){
            host = parts[0];
            port = 21375;
        } else if (parts.length == 2){
            host = parts[0];
            try{
                port = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                Logger.error("Incorrect port");
                return;
            }
        } else {
            Logger.error("Incorrect address format");
            return;
        }


        try{
            Socket socket = new Socket(host, port);
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

        getPageManager().push("LOBBY");

        ctx.state = new ConnectedState(ctx);
    }
}
