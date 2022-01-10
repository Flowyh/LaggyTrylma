package com.laggytrylma.frontend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;

public interface RemoteGameInput {
    void remoteMove(int pieceId, int destId);

    void startGame(Game game);
}
