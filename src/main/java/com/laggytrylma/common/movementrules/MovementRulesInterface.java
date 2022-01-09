package com.laggytrylma.common.movementrules;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

import java.util.Set;

public interface MovementRulesInterface {
    Set<Square> getAllowedMoves(Piece piece);
}
