package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Move to nearest empty Square rule.
 */
public class NearMovement implements MovementRulesInterface {
    /**
     * Check if Piece's near neighbours are not occupied.
     * @param piece Piece to be checked
     * @return Set of visitable squares.
     */
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        Set<Square> visitable = new HashSet<>();
        Square startingSquare = piece.getSquare();
        for(Connection connection : startingSquare.getConnections()){
            Square near = connection.near;
            if(near != null && !near.occupied()){
                visitable.add(near);
            }
        }

        return visitable;
    }

    /**
     * No filtering
     * @param piece Piece to be checked
     * @param targets Set of visitable squares (pre-filtered).
     * @return same Set of visitable squares (pre-filtered).
     */
    @Override
    public Set<Square> filterBannedMoves(Piece piece, Set<Square> targets) {
        return targets;
    }
}
