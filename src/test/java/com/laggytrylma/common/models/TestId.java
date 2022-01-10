package com.laggytrylma.common.models;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestId {
    @Test
    public void testIdRetrieval(){
        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game game = director.build();

        for(Piece piece : game.getPieces()){
            Piece pieceById = game.getPieceById(piece.getId());
            assertEquals(piece, pieceById);
        }

        for(Square square : game.getSquares()){
            Square squareById = game.getSquareById(square.getId());
            assertEquals(square, squareById);
        }

        for(Player player : game.getPlayers()){
            Player playerById = game.getPlayerById(player.getId());
            assertEquals(player, playerById);
        }
    }
}
