package com.laggytrylma.common.models;

public class GameReplay extends Game{
    private int move_number;

    public GameReplay(Game game){
        this.pieces = game.getPieces();
        this.squares = game.getSquares();
        this.players = game.getPlayers();
        this.movesHistory = game.getMovesHistory();

        move_number = movesHistory.size();
        while(move_number > 0){
            previousMove();
        }
    }

    private void forcedMove(Piece piece, Square target){
        target.setPiece(piece);
        Square startingSquare = piece.getSquare();
        startingSquare.setPiece(null);
        piece.setSquare(target);
    }

    private void forcedMove(Move move){
        forcedMove(move.piece, move.to);
    }

    private void undoMove(Move move){
        forcedMove(move.piece, move.from);
    }

    public void nextMove(){
        if(move_number < movesHistory.size()){
            Move move = movesHistory.get(move_number);
            forcedMove(move);
            move_number++;
        }
    }

    public void previousMove(){
        if(move_number > 0){
            move_number--;
            Move move = movesHistory.get(move_number);
            undoMove(move);
        }
    }
}
