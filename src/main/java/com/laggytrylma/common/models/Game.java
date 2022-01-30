package com.laggytrylma.common.models;

import com.laggytrylma.common.rules.MovementRulesInterface;
import com.laggytrylma.common.rules.RuleInterface;
import com.laggytrylma.common.rules.WinRulesInterface;
import com.laggytrylma.utils.Logger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Main Trylma Game Model.
 */
public class Game {
    /**
     * List of Game's pieces.
     */
    private final List<Piece> pieces = new LinkedList<>();
    /**
     * List of Game's squares.
     */
    private final List<Square> squares = new LinkedList<>();
    /**
     * List of Game's Players.
     */
    private final List<Player> players = new LinkedList<>();

    /**
     * List of Game's win conditions.
     */
    private final List<Move> movesHistory = new LinkedList<>();
    /**
     * List of Game's rules.
     */
    private final List<MovementRulesInterface> movementRules = new LinkedList<>();
    /**
     * List of Game's win conditions.
     */
    private final List<WinRulesInterface> winRules = new LinkedList<>();
    /**
     * Current player that has to perform a move.
     */
    private Player currentPlayer;

    /**
     * Set current player.
     * @param player new current player.
     */
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    /**
     * Get current player.
     * @return Player current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Add a Square to square list.
     * @param square new Square object.
     */
    public void addSquare(Square square) {
        squares.add(square);
    }

    /**
     * Get list of Game's squares.
     * @return List of Square objects.
     */
    public List<Square> getSquares(){
        return squares;
    }

    /**
     * Add new Rule to the Game.
     * @param rule new rule
     */
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

    /**
     * Get allowed moves of current piece.
     * Iterates over current rules and determines the list of visitable squares.
     * @param piece Piece object that we will be checking.
     * @return Set of visitable squares.
     */
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

    /**
     * Get Game's winner.
     * @return Player Game's winner (if there's any, otherwise null).
     */
    public Player getWinner(){
        for(WinRulesInterface rule : winRules){
            Player winner = rule.getWinner(this);
            if(winner != null){
                return winner;
            }
        }
        return null;
    }

    /**
     * Move Piece to given Square.
     * @param piece Piece object
     * @param finalPosition Square object to move the piece to
     * @return True if move was successful, False if otherwise.
     */
    public boolean move(Piece piece, Square finalPosition) {
        if(!getAllowedMoves(piece).contains(finalPosition))
            return false;

        finalPosition.setPiece(piece);
        Square startingSquare = piece.getSquare();
        startingSquare.setPiece(null);
        piece.setSquare(finalPosition);
        movesHistory.add(new Move(piece, startingSquare, finalPosition));

        return true;
    }

    /**
     * Get Square from squares list by it's id.
     * @param id Square id
     * @return Square object.
     */
    public Square getSquareById(int id){
        for(Square square : squares){
            if(square.getId() == id)
                return square;
        }
        return null;
    }

    /**
     * Get Piece from pieces list by it's id.
     * @param id Piece id
     * @return Piece object.
     */
    public Piece getPieceById(int id){
        for(Piece piece : pieces){
            if(piece.getId() == id)
                return piece;
        }
        return null;
    }

    /**
     * Get Player from players list by it's id.
     * @param id Player id
     * @return Player object.
     */
    public Player getPlayerById(int id){
        for(Player player : players){
            if(player.getId() == id)
                return player;
        }
        return null;
    }

    /**
     * Get all Game's rules.
     * @return List of Game's rules.
     */
    public List<RuleInterface> getRules() {
        List<RuleInterface> output = new LinkedList<>();
        output.addAll(movementRules);
        output.addAll(winRules);
        return output;
    }

    /**
     * Get all Game's pieces.
     * @return List of Game's pieces.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * Get all Game's players.
     * @return List of Game's players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Add new player to the Game.
     * @param player new Player object.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Add new piece to the Game.
     * @param piece new Piece object.
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }
}
