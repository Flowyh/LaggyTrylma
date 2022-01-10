package com.laggytrylma.common.models;

import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.movementrules.MovementRulesInterface;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
    public Game(){

    }

    List<Piece> pieces = new LinkedList<>();
    List<Square> squares = new LinkedList<>();
    List<Player> players = new LinkedList<>();
    List<MovementRulesInterface> movementRules = new LinkedList<>();
    int currentPlayer = -1;

    public void setCurrentPlayer(int id) {
        currentPlayer = id;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public List<Square> getSquares(){
        return squares;
    }

    public void addRule(MovementRulesInterface movementRule){
        movementRules.add(movementRule);
    }

    public Set<Square> getAllowedMoves(Piece piece){
        Set<Square> visitable = new HashSet<>();
        for(MovementRulesInterface rule: movementRules){
            visitable.addAll(rule.getAllowedMoves(piece));
        }
        return visitable;
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

    public List<MovementRulesInterface> getRules() {
        return movementRules;
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
