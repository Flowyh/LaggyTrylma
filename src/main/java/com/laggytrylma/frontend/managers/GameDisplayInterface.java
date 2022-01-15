package com.laggytrylma.frontend.managers;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

public interface GameDisplayInterface {
    void updateGame();
    void startGame(Game game);
    void setWhoAmI(Player player);
    void removeGame();

    void win(Player winner);
}
