package com.laggytrylma.frontend.states;

import com.laggytrylma.utils.Logger;

public class DisconnectedState extends AbstractState{
    public DisconnectedState(Context ctx){
        super(ctx);
    }

    @Override
    public void connect(String address){
        Logger.debug("Connecting to: " + address);
        getPageManager().push("GAME");
    }
}
