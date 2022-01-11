package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.HashSet;
import java.util.Set;

public class NoLevingOfTargetBase implements MovementRulesInterface{
    @Override
    public Set<Square> getAllowedMoves(Piece piece) {
        return new HashSet<>();
    }

    @Override
    public Set<Square> filterBannedMoves(Piece piece, Set<Square> targets) {
        if(piece.getSquare().getTarget() == piece.getOwner()){
            targets.removeIf(target -> target.getTarget() != piece.getOwner());
        }

        return targets;
    }
}
