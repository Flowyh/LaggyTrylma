package com.laggytrylma.common.movementrules;

import com.laggytrylma.common.models.Connection;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.HashSet;
import java.util.Set;

public class FarMovement implements MovementRulesInterface {
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        Square startingSquare = piece.getSquare();
        return recursivePossibilities(startingSquare, new HashSet<>());
    }

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
