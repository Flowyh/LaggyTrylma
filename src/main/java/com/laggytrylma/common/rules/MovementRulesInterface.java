package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.Set;

/**
 * Game's movement rule interface.
 */
public interface MovementRulesInterface extends RuleInterface{
    /**
     * Get's piece's visitable squares.
     * @param piece Piece to be checked
     * @return Set of visitable squares.
     */
    Set<Square> getAllowedMoves(Piece piece);

    /**
     * Removes banned moves from given piece's visitable squares.
     * @param piece Piece to be checked
     * @param targets Set of visitable squares (pre-filtered).
     * @return Set of filtered visitable squares.
     */
    Set<Square> filterBannedMoves(Piece piece, Set<Square> targets);
}
