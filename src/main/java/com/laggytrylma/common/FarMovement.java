package com.laggytrylma.common;

import java.util.HashSet;
import java.util.Set;

public class FarMovement implements MovementRulesInterface{
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        Set<Square> visitable = new HashSet<>();
        Square startingSquare = piece.getSquare();
        for(Connection connection : startingSquare.getConnections()){
            Square near = connection.near;
            Square far = connection.far;
            if(near != null && near.occpuied() && far != null && !far.occpuied()){
                visitable.add(far);
            }
        }

        return visitable;
    }
}
