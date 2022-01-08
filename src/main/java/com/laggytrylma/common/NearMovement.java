package com.laggytrylma.common;

import java.util.HashSet;
import java.util.Set;

public class NearMovement implements MovementRulesInterface{
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
}
