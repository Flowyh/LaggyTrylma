package com.laggytrylma.frontend;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameManager implements LocalGameInput, RemoteGameInput {
    Game game;
    ClientSocket clientSocket;
    List<GameDisplayInterface> gameDisplays = new LinkedList<>();

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

        // JSONCommandWrapper<GameCommands> request = new JSONCommandWrapper<>(GameCommands.UPDATE, )

        Map<String, String> args = new HashMap<>();
        args.put("piece", Integer.toString(piece.getId()));
        args.put("destination", Integer.toString(destination.getId()));
        JSONCommandWrapper<?> msg = new JSONCommandWrapper<>(GameCommands.MOVE, args);
        try {
            String serialized = msg.serialize();
            System.out.println(serialized);
            clientSocket.writeOutput(serialized);
        } catch(IOException e) {
          Logger.error(e.getMessage());
        }

        return true;
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
        Logger.debug("Starting game");
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.startGame(game);
        }
        updateDisplays();
    }

    private void updateDisplays(){
        for(GameDisplayInterface gameDisplay : gameDisplays){
            gameDisplay.updateGame();
        }
    }
}
