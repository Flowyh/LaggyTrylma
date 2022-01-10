package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Player;

public interface WinRulesInterface extends RuleInterface{
    Player getWinner(Game game);
}
