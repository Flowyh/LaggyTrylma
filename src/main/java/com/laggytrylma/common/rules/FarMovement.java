package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Jump over piece rule.
 */
public class FarMovement implements MovementRulesInterface {
    /**
     * Recursively check for available jumps over current board pieces.
     * @param piece Piece to be checked
     * @return Set of visitable Squares
     */
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        Square startingSquare = piece.getSquare();
        return recursivePossibilities(startingSquare, new HashSet<>());
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

    /**
     * Recursively check whether we can jump over the near neighbours from list of connections.
     * If near neighbour is occupied and far neighbour isn't, we can jump.
     * @param startingSquare Square to check it's connections
     * @param visited Set of visitable Squares (passed to every recursion step)
     * @return Set of visitable Squares
     */
    private Set<Square> recursivePossibilities(Square startingSquare, Set<Square> visited){
        for(Connection connection : startingSquare.getConnections()){
            Square near = connection.near;
            Square far = connection.far;
            if(near != null && near.occupied() && far != null && !far.occupied() && !visited.contains(far)){
                visited.add(far);
                recursivePossibilities(far, visited);
            }
        }

        return visited;
    }

}
