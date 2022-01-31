package com.laggytrylma.frontend.states;

public class ReplayState extends AbstractState {
    public ReplayState(Context ctx) {
        super(ctx);
    }

    @Override
    public void leave(){
        ctx.getGameManager().removeGame();
        ctx.getGameManager().updateDisplays();

        ctx.state = new ConnectedState(ctx);
        getPageManager().pop();
    }
}
