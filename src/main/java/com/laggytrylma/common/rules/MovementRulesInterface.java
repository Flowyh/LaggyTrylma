package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.Set;

public interface MovementRulesInterface extends RuleInterface{
    Set<Square> getAllowedMoves(Piece piece);
    Set<Square> filterBannedMoves(Piece piece, Set<Square> targets);
}
