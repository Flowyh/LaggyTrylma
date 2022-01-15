package com.laggytrylma.frontend.managers;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.frontend.CommandMatcher;
import com.laggytrylma.frontend.communication.ClientSocket;
import com.laggytrylma.utils.communication.commands.models.GameCommands;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BoardManagerTest {
    GameManager gm;
    ClientSocket client;
    GameDisplayInterface display;
    Game game;

    @Before
    public void setup(){
        gm = new GameManager();
        client = mock(ClientSocket.class);
        display = mock(GameDisplayInterface.class);
        game = mock(Game.class);
        gm.attachClientSocket(client);
        gm.attachGameDisplay(display);
        gm.startGame(game);
    }


    @Test
    public void instantiationAndGameStart(){
        assertNotNull(gm);
        verify(display).startGame(game);
        verify(display).updateGame();
    }

    @Test
    public void localMoveTest(){
        Piece piece = mock(Piece.class);
        when(piece.getId()).thenReturn(0);

        Square square = mock(Square.class);
        when(square.getId()).thenReturn(1);

        when(game.move(piece, square)).thenReturn(true);

        gm.localMove(piece, square);

        verify(game).move(piece, square);


        Map<String, String> args = new HashMap<>();
        args.put("piece", Integer.toString(piece.getId()));
        args.put("destination", Integer.toString(square.getId()));
        JSONCommandWrapper<?> expectedCommand = new JSONCommandWrapper<>(GameCommands.MOVE, args);
        verify(client).sendMessage(argThat(new CommandMatcher(expectedCommand)));
    }

    @Test
    public void incorrectLocalMoveTest(){
        Piece piece = mock(Piece.class);
        when(piece.getId()).thenReturn(0);

        Square square = mock(Square.class);
        when(square.getId()).thenReturn(1);

        when(game.move(piece, square)).thenReturn(false);

        gm.localMove(piece, square);

        verify(game).move(piece, square);
        verify(client, never()).sendMessage(any());
    }

    @Test
    public void remoteMoveTest(){
        Piece piece = mock(Piece.class);
        Square square = mock(Square.class);
        when(game.getPieceById(0)).thenReturn(piece);
        when(game.getSquareById(1)).thenReturn(square);
        Mockito.reset(display);

        gm.remoteMove(0, 1);
        verify(game).move(piece, square);
        verify(display).updateGame();
    }

    @Test
    public void assignPlayerTest(){
        Player player = mock(Player.class);
        when(game.getPlayerById(0)).thenReturn(player);

        gm.assignPlayer(0);

        verify(display).setWhoAmI(player);
    }

    @Test
    public void setCurrentPlayerTest(){
        Player player = mock(Player.class);
        when(game.getPlayerById(0)).thenReturn(player);

        Mockito.reset(display);

        gm.setCurrentPlayer(0);

        verify(game).setCurrentPlayer(player);
        verify(display).updateGame();
    }

    @Test
    public void removeGameTest(){
        gm.removeGame();
        verify(display).removeGame();
    }

    @Test
    public void winTest(){
        Player player = mock(Player.class);
        when(game.getPlayerById(0)).thenReturn(player);
        gm.win(0);
        verify(display).win(player);
    }
}
