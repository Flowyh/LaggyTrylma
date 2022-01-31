package com.laggytrylma.frontend.managers;

import com.laggytrylma.common.models.*;
import com.laggytrylma.frontend.communication.ClientSocket;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameManager implements LocalGameInput, RemoteGameInput {
    Game game;
    ClientSocket clientSocket;
    List<GameDisplayInterface> gameDisplays = new LinkedList<>();
    Player me = null;

    public void attachClientSocket(ClientSocket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void attachGameDisplay(GameDisplayInterface gameDisplay){
        gameDisplays.add(gameDisplay);
    }

    @Override
    public boolean localMove(Piece piece, Square destination) {
        boolean correct = game.move(piece, destination);
        if(!correct)
            return false;

        updateDisplays();


        Map<String, String> args = new HashMap<>();
        args.put("piece", Integer.toString(piece.getId()));
        args.put("destination", Integer.toString(destination.getId()));
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.MOVE, args);
        boolean success = clientSocket.sendMessage(msg);
        return success;
    }

    @Override
    public void remoteMove(int pieceId, int destId)    {
        Piece piece = game.getPieceById(pieceId);
        Square dest = game.getSquareById(destId);

        game.move(piece, dest);
        updateDisplays();
    }

    @Override
    public void startGame(Game game) {
        this.game = game;
        Logger.debug("Loading a new game");
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.startGame(game);
        }
        updateDisplays();
    }

    @Override
    public void assignPlayer(int playerId) {
        me = game.getPlayerById(playerId);
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.setWhoAmI(me);
        }
    }

    @Override
    public void setCurrentPlayer(int nextPlayer) {
        Player player = game.getPlayerById(nextPlayer);
        game.setCurrentPlayer(player);

        updateDisplays();
    }

    public void updateDisplays(){
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.updateGame();
        }
    }

    public void removeGame() {
        game=null;
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.removeGame();
        }
    }

    public void win(int playerId) {
        Player winner = game.getPlayerById(playerId);
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.win(winner);
        }
    }

    public void replay(Game game){
        startGame(new GameReplay(game));
    }
}
