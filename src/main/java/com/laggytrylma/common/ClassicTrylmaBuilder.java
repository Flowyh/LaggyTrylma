package com.laggytrylma.common;

import com.laggytrylma.utils.Logger;

import java.awt.*;

public class ClassicTrylmaBuilder extends AbstractGameBuilder{
    private Game game = new Game();
    private Player[] players;

    // diagonal coordinate system (u,v) ranging from 0 to 16 (inclusive), refer to drawings
    Square[][] squares = new Square[17][17];

    private float[] convertCoordinatesToXY(int u, int v){
        return new float[] {v - u / 2f + 2, u};
    }

    private boolean onBoard(int u, int v){
        boolean insideDownPointingTriangle = (u >= 4) && (v <= 12) && (u-v <= 4);
        boolean insideUpPointingTriangle = (u <= 12) && (v >= 4) && (u-v >= -4);
//        boolean insideUpPointingTriangle = false;

        return insideDownPointingTriangle || insideUpPointingTriangle;
    }

    /**
     * If cooridnates point to a Square inside the star, return it, otherwise return NULL;
     */
    private Square getSquareConstrained(int u, int v){
        if(onBoard(u, v))
            return squares[u][v];
        else
            return null;
    }

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

    @Override
    public void makeConnections() {
        // add neighbours
        for(int u=0; u<17; u++){
            for(int v=0;v<17;v++){
                addConnection(u, v);
            }
        }
    }

    @Override
    public void connectPlayers(Player[] players) {
        if(players == null || players.length != 6){
            Logger.error("No proper players provided, making something up");
            this.players = new Player[6];
            this.players[0] = new Player(getNewId(), "1", new Color(249, 65, 68));
            this.players[1] = new Player(getNewId(), "2", new Color(248, 150, 30));
            this.players[2] = new Player(getNewId(), "3", new Color(249, 199, 79));
            this.players[3] = new Player(getNewId(), "4", new Color(144, 190, 109));
            this.players[4] = new Player(getNewId(), "5", new Color(67, 170, 139));
            this.players[5] = new Player(getNewId(), "6", new Color(87, 117, 144));
        } else{
            this.players = players;
        }
        for(Player player : this.players){
            game.addPlayer(player);
        }
    }

    @Override
    public void setSquareOwnership() {
        for(int u=0; u<17; u++){
            for(int v=0;v<17;v++){
                Square square = squares[u][v];
                if(square == null)
                    continue;
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
        }
    }

    @Override
    public void addRules() {
        game.addRule(new NearMovement());
        game.addRule(new FarMovement());
    }

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

    @Override
    public Game getResult() {
        return game;
    }
}
