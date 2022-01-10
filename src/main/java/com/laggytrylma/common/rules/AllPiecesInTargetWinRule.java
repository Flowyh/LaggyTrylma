package com.laggytrylma.common.rules;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AllPiecesInTargetWinRule implements WinRulesInterface{
    @Override
    public Player getWinner(Game game) {
        List<Player> potentialWinners = new LinkedList<>(game.getPlayers());
        for(Piece piece : game.getPieces()){
            if(piece.getSquare().getTarget() != piece.getOwner())
                potentialWinners.remove(piece.getOwner());
        }

        if(!potentialWinners.isEmpty()){
            return potentialWinners.get(0);
        } else{
            return null;
        }
    }
}
