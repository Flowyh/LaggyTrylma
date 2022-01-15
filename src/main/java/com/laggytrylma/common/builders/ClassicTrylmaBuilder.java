package com.laggytrylma.common.builders;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.common.rules.AllPiecesInTargetWinRule;
import com.laggytrylma.common.rules.FarMovement;
import com.laggytrylma.common.rules.NearMovement;
import com.laggytrylma.common.rules.NoLeavingOfTargetBase;
import com.laggytrylma.utils.Logger;

/**
 * Classic Trylma variant builder.
 */
public class ClassicTrylmaBuilder extends AbstractGameBuilder{
    /**
     * New Game instance.
     */
    private Game game = null;
    /**
     * Array of Game's player objects.
     */
    private Player[] players;

    /**
     * 2D Array of Squares objects.
     * Diagonal coordinate system (u,v) ranging from 0 to 16 (inclusive), refer to drawings
     */
    Square[][] squares = new Square[17][17];

    /**
     * Converts Square's game coordinates to UI's (x, y) coordinates.
     * @param u Square's game u coordinate
     * @param v Square's game v coordinate
     * @return UI's (x, y) coordinates
     */
    private float[] convertCoordinatesToXY(int u, int v){
        float scale = 1/17f;
        return new float[] {scale * (v - u / 2f + 4), scale*u};
    }

    /**
     * Check whether Square is on "board" (outside pointing triangles).
     * @param u Square's game u coordinate
     * @param v Square's game v coordinate
     * @return True if Square is on board, False if otherwise.
     */
    private boolean onBoard(int u, int v){
        boolean insideDownPointingTriangle = (u >= 4) && (v <= 12) && (u-v <= 4);
        boolean insideUpPointingTriangle = (u <= 12) && (v >= 4) && (u-v >= -4);

        return insideDownPointingTriangle || insideUpPointingTriangle;
    }

    /**
     * If coordinates point to a Square inside the star.
     * @param u Square's game u coordinate
     * @param v Square's game v coordinate
     * @return Square object if it is inside the star, null if otherwise.
     */
    private Square getSquareConstrained(int u, int v){
        if(onBoard(u, v))
            return squares[u][v];
        else
            return null;
    }

    /**
     * Add connection to given square on (u, v) coordinates.
     * @param u Square's u coordinate
     * @param v Square's v coordinate
     */
    private void addConnection(int u, int v){
        Square referenceSquare = getSquareConstrained(u, v);
        if(referenceSquare == null) {
            return;
        }

        // How to increment u and v to get to the next neighbour.
        // In order: down left, right, down right
        final int[][] neighbourVectors = {{1, 0}, {0, 1}, {1, 1}};
        for(int[] direction :  neighbourVectors){
            // inverting adds: up right, left, up left
            for(int invert : new int[] {-1, 1}){
                int delta_u = direction[0] * invert;
                int delta_v = direction[1] * invert;

                Square near =  getSquareConstrained(u+delta_u, v+delta_v);
                Square far = getSquareConstrained(u + 2*delta_u, v + 2*delta_v);
                referenceSquare.addConnection(near, far);
            }
        }
    }

    /**
     * Instantiate a new Game object.
     */
    @Override
    void makeInstance() {
        game = new Game();
    }

    /**
     * Instantiate squares and add them to Game's square list.
     */
    @Override
    public void instantiateBoard() {
        // instantiate squares which are inside the start
        for(int u=0; u<17; u++){
            for(int v=0;v<17;v++){
                if(onBoard(u, v)){
                    float[] xy = convertCoordinatesToXY(u, v);
                    squares[u][v] = new Square(getNewId(), xy[0], xy[1]);
                    game.addSquare(squares[u][v]);
                }
            }
        }
    }

    /**
     * Add connections to every square.
     */
    @Override
    public void makeConnections() {
        // add neighbours
        for(int u=0; u<17; u++){
            for(int v=0;v<17;v++){
                addConnection(u, v);
            }
        }
    }

    /**
     * Connect player to the Game
     * @param players Array of Game's Player objects
     */
    @Override
    public void connectPlayers(Player[] players) {
        if(players.length != 6 && players.length != 4 && players.length != 3 && players.length != 2){
            Logger.error("No proper players provided");
        }
        this.players = players;

        for(Player player : this.players){
            game.addPlayer(player);
        }
    }

    /**
     * Set Square's spawn/target Player objects.
     */
    @Override
    public void setSquareOwnership() {
        for(int u=0; u<17; u++){
            for(int v=0;v<17;v++){
                Square square = squares[u][v];
                if(square == null)
                    continue;
                switch(players.length){
                    case 2 -> {
                        if(u < 4){
                            square.setSpawnAndTarget(players[0], players[1]);
                        }
                        else if(u > 12){
                            square.setSpawnAndTarget(players[1], players[0]);
                        }
                        else {
                            square.setSpawnAndTarget(null, null);
                        }
                    }
                    case 3 -> {
                        if(u < 4){
                            square.setSpawnAndTarget(players[0], players[2]);
                        }
                        else if(v > 12){
                            square.setSpawnAndTarget(players[1], players[0]);
                        }
                        else if(u - v > 4){
                            square.setSpawnAndTarget(players[2], players[1]);
                        }
                        else{
                            square.setSpawnAndTarget(null, null);
                        }
                    }
                    case 4 -> {
                        if(u < 4){
                            square.setSpawnAndTarget(players[0], players[2]);
                        }
                        else if(u - v < -4){
                            square.setSpawnAndTarget(players[1], players[3]);
                        }
                        else if(u > 12){
                            square.setSpawnAndTarget(players[2], players[0]);
                        }
                        else if(u - v > 4){
                            square.setSpawnAndTarget(players[3], players[1]);
                        }
                        else{
                            square.setSpawnAndTarget(null, null);
                        }
                    }
                    case 6 ->{
                        if(u < 4){
                            square.setSpawnAndTarget(players[0], players[3]);
                        }
                        else if(u - v < -4){
                            square.setSpawnAndTarget(players[1], players[4]);
                        }
                        else if(v > 12){
                            square.setSpawnAndTarget(players[2], players[5]);
                        }
                        else if(u > 12){
                            square.setSpawnAndTarget(players[3], players[0]);
                        }
                        else if(u - v > 4){
                            square.setSpawnAndTarget(players[4], players[1]);
                        }
                        else if(v < 4){
                            square.setSpawnAndTarget(players[5], players[2]);
                        }
                        else{
                            square.setSpawnAndTarget(null, null);
                        }
                    }
                    default -> square.setSpawnAndTarget(null, null);
                }
            }
        }
    }

    /**
     * Add classic Trylma rules to the Game instance.
     */
    @Override
    public void addRules() {
        game.addRule(new NearMovement());
        game.addRule(new FarMovement());
        game.addRule(new AllPiecesInTargetWinRule());
        game.addRule(new NoLeavingOfTargetBase());
    }

    /**
     * Create Pieces object to the Game.
     */
    @Override
    public void createPieces() {
        for(int u=0; u<17; u++) {
            for (int v = 0; v < 17; v++) {
                Square square = squares[u][v];
                if(square == null)
                    continue;
                Player owner = square.getSpawn();
                if(owner == null)
                    continue;
                Piece piece = new Piece(getNewId(), owner, square);
                square.setPiece(piece);
                game.addPiece(piece);
            }
        }
    }

    /**
     * @return new Game instance
     */
    @Override
    public Game getResult() {
        return game;
    }
}
