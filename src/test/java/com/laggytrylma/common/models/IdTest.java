package com.laggytrylma.common.models;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IdTest {
    @Test
    public void testIdRetrieval(){
        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < 6; i ++) {
            players.add(new Player(i, Integer.toString(i), new Color(i, i, i)));
        }
        director.setPlayers(players.toArray(new Player[0]));
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
