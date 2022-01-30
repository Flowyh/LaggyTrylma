package com.laggytrylma.common.models;

public class Move {
    Piece piece;
    Square from;
    Square to;
    Move(Piece piece, Square from, Square to){
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
}
