package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Can't leave target base after entering rule.
 */
public class NoLeavingOfTargetBase implements MovementRulesInterface{
    /**
     * No new allowed moves.
     * @param piece Piece to be checked
     * @return new Set of Squares.
     */
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        return new HashSet<>();
    }

    /**
     * Filter moves outside of target base (if piece is inside it).
     * @param piece Piece to be checked
     * @param targets Set of visitable squares (pre-filtered).
     * @return Set of visitable squares
     */
    @Override
    public Set<Square> filterBannedMoves(Piece piece, Set<Square> targets) {
        if(piece.getSquare().getTarget() == piece.getOwner()){
            targets.removeIf(target -> target.getTarget() != piece.getOwner());
        }

        return targets;
    }
}
