package com.laggytrylma.frontend.board;

import com.laggytrylma.common.models.Game;
import com.laggytrylma.common.models.Piece;
import com.laggytrylma.common.models.Player;
import com.laggytrylma.common.models.Square;
import com.laggytrylma.frontend.managers.LocalGameInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.mockito.Mockito.*;

public class BoardStateTest {
    SquareDisplayWrapper myWrapper, someonesWrapper, emptyWrapper;
    Player me, someone;
    Square mySquare, someonesSquare, emptySquare;
    BoardWidget boardWidget;
    Game mockedGame;
    Piece pi1, pi2;

    @Before
    public void setUp() {
        boardWidget = new BoardWidget();

        me = new Player(0, "name", Color.BLACK);
        someone = new Player(1, "name", Color.BLACK);

        boardWidget.setWhoAmI(me);

        mySquare = new Square(2, 0f, 0f);
        someonesSquare = new Square(3, 0f, 0f);
        emptySquare = new Square(4, 0f, 0f);

        pi1 = new Piece(4, me, mySquare);
        pi2 = new Piece(5, someone, someonesSquare);

        mySquare.setPiece(pi1);
        someonesSquare.setPiece(pi2);

        myWrapper = new SquareDisplayWrapper(mySquare);
        someonesWrapper = new SquareDisplayWrapper(someonesSquare);
        emptyWrapper = new SquareDisplayWrapper(emptySquare);

        mockedGame = mock(Game.class);
        when(mockedGame.getSquares()).thenReturn(new LinkedList<>(Arrays.asList(mySquare, someonesSquare, emptySquare)));
        boardWidget.startGame(mockedGame);
    }

    @Test
    public void testInitialState() {
        BoardWidget boardWidget = new BoardWidget();
        Assert.assertNotNull(boardWidget);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testClickedOnEmptySpace() {
        when(mockedGame.getCurrentPlayer()).thenReturn(me);
        boardWidget.clickedOn(null);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testClickedNotMyTurn() {
        when(mockedGame.getCurrentPlayer()).thenReturn(someone);
        boardWidget.clickedOn(myWrapper);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testClickedNotMyPiece() {
        when(mockedGame.getCurrentPlayer()).thenReturn(me);
        boardWidget.clickedOn(someonesWrapper);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testClickedOnEmptySquare() {
        when(mockedGame.getCurrentPlayer()).thenReturn(me);
        boardWidget.clickedOn(emptyWrapper);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testClickedOnMyPiece() {
        when(mockedGame.getCurrentPlayer()).thenReturn(me);
        boardWidget.clickedOn(myWrapper);
        Assert.assertTrue(boardWidget.state instanceof BoardWidgetStateSelected);
    }

    @Test
    public void testUnselect() {
        BoardWidgetStateSelected newState = new BoardWidgetStateSelected(boardWidget);
        newState.setSelected(myWrapper);
        boardWidget.state = newState;

        boardWidget.clickedOn(emptyWrapper);
        Assert.assertTrue(boardWidget.state instanceof IdleBoardWidgetState);
    }

    @Test
    public void testMovement(){
        when(mockedGame.getCurrentPlayer()).thenReturn(me);

        LocalGameInput lgi = mock(LocalGameInput.class);
        boardWidget.attachControl(lgi);

        Set<Square> allowedMoves = new HashSet<>();
        allowedMoves.add(emptySquare);
        when(mockedGame.getAllowedMoves(pi1)).thenReturn(allowedMoves);

        boardWidget.clickedOn(myWrapper);
        boardWidget.clickedOn(emptyWrapper);
        verify(lgi).localMove(pi1, emptySquare);
    }
}
