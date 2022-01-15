package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

/**
 * Game win condition rule interface.
 */
public interface WinRulesInterface extends RuleInterface{
    /**
     * Get game's winner.
     * @param game Game object
     * @return Player winner
     */
    Player getWinner(Game game);
}
