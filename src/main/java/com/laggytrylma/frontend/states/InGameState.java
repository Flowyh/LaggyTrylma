package com.laggytrylma.frontend.states;

public class InGameState extends ConnectedState{

    public InGameState(Context ctx) {
        super(ctx);
    }

    @Override
    public void disconnect(){
        leave();
        super.disconnect();
    }

    @Override
    public void leave(){

    }
}
