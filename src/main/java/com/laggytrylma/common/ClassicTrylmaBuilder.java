package com.laggytrylma.common;

public class ClassicTrylmaBuilder implements AbstractGameBuilder{
    private Game game = new Game();

    // diagonal coordinate system (u,v) ranging from 0 to 16 (inclusive), refer to drawings
    Square[][] squares = new Square[17][17];

    private boolean onBoard(int u, int v){
        boolean insideDownPointingTriangle = (u >= 4) && (v <= 12) && (u-v <= 4);
        boolean insideUpPointingTriangle = (u <= 12) && (v >= 4) && (u-v >= -4);
//        boolean insideUpPointingTriangle = false;

        return insideDownPointingTriangle || insideUpPointingTriangle;
    }

    /**
     * If cooridnates point to a Square inside the star, return it, otherwise return NULL;
     * @param u
     * @param v
     * @return
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
                    squares[u][v] = new Square(0, 0);
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
    public void connectPlayers() {

    }

    @Override
    public void setSquareOwnership() {

    }

    @Override
    public void addRules() {

    }

    @Override
    public Game getResult() {
        return game;
    }
}
