package com.laggytrylma.frontend;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

public interface LocalGameInput {
    boolean localMove(Piece piece, Square destination);
}
