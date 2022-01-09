package com.laggytrylma.common;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestId {
    @Test
    public void testIdRetrieval(){
        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game game = director.build();

        for(Piece piece : game.pieces){
            Piece pieceById = game.getPieceById(piece.getId());
            assertEquals(piece, pieceById);
        }

        for(Square square : game.squares){
            Square squareById = game.getSquareById(square.getId());
            assertEquals(square, squareById);
        }

        for(Player player : game.players){
            Player playerById = game.getPlayerById(player.getId());
            assertEquals(player, playerById);
        }
    }
}
