package com.laggytrylma.common.models;

import com.laggytrylma.common.rules.MovementRulesInterface;
import com.laggytrylma.common.rules.RuleInterface;
import com.laggytrylma.common.rules.WinRulesInterface;
import com.laggytrylma.utils.Logger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
    private final List<Piece> pieces = new LinkedList<>();
    private final List<Square> squares = new LinkedList<>();
    private final List<Player> players = new LinkedList<>();
    private final List<MovementRulesInterface> movementRules = new LinkedList<>();
    private final List<WinRulesInterface> winRules = new LinkedList<>();
    private Player currentPlayer;

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public List<Square> getSquares(){
        return squares;
    }

    public void addRule(RuleInterface rule)
    {
        if(rule instanceof MovementRulesInterface) {
            movementRules.add((MovementRulesInterface) rule);
        } else if(rule instanceof WinRulesInterface){
            winRules.add((WinRulesInterface) rule);
        } else{
            Logger.error("Unknown rule type");
        }
    }

    public Set<Square> getAllowedMoves(Piece piece){
        Set<Square> visitable = new HashSet<>();
        for(MovementRulesInterface rule: movementRules){
            visitable.addAll(rule.getAllowedMoves(piece));
        }
        for(MovementRulesInterface rule: movementRules){
            rule.filterBannedMoves(piece, visitable);
        }
        return visitable;
    }

    public Player getWinner(){
        for(WinRulesInterface rule : winRules){
            Player winner = rule.getWinner(this);
            if(winner != null){
                return winner;
            }
        }
        return null;
    }

    public boolean move(Piece piece, Square finalPosition) {
        if(!getAllowedMoves(piece).contains(finalPosition))
            return false;

        finalPosition.setPiece(piece);
        Square startingSquare = piece.getSquare();
        startingSquare.setPiece(null);
        piece.setSquare(finalPosition);

        return true;
    }

    public Square getSquareById(int id){
        for(Square square : squares){
            if(square.getId() == id)
                return square;
        }
        return null;
    }

    public Piece getPieceById(int id){
        for(Piece piece : pieces){
            if(piece.getId() == id)
                return piece;
        }
        return null;
    }

    public Player getPlayerById(int id){
        for(Player player : players){
            if(player.getId() == id)
                return player;
        }
        return null;
    }

    public List<RuleInterface> getRules() {
        List<RuleInterface> output = new LinkedList<>();
        output.addAll(movementRules);
        output.addAll(winRules);
        return output;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
}
