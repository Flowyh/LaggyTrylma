package com.laggytrylma.common;

import java.util.Set;

public interface MovementRulesInterface {
    Set<Square> getAllowedMoves(Piece piece);
}
