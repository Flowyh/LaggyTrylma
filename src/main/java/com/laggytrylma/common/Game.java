package com.laggytrylma.common;

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

}
