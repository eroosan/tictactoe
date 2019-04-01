package es.codeurjc.ais.tictactoe;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.*;
import es.codeurjc.ais.tictactoe.TicTacToeGame.CellMarkedValue;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

public class TicTacToeGameTest {

	TicTacToeGame game;
	Connection connection1;
	Connection connection2;
	Player player1;
	Player player2;	
	ArgumentCaptor<CellMarkedValue> argument;
	ArgumentCaptor<WinnerValue> argument2;
	List<Player> players;	


	@Before
	public void setUp() throws Exception {
		
		game = new TicTacToeGame();
		connection1 = mock(Connection.class);
		connection2 = mock(Connection.class);	
		
		player1 = mock(Player.class);
		when(player1.getId()).thenReturn(1);
		when(player1.getLabel()).thenReturn("X");		

	    player2 = mock(Player.class);
		when(player2.getId()).thenReturn(2);		
		when(player2.getLabel()).thenReturn("O");			

		argument = ArgumentCaptor.forClass(CellMarkedValue.class);
		argument2 = ArgumentCaptor.forClass(WinnerValue.class);		
		
		players = new CopyOnWriteArrayList<>();		
	}
	
	@Test
	public void testFirstPlayerWins() {
	
        // | X | O | X |
        // | O | X | O |
        // | X |   |   |
		
		int[] cellIdList = { 0 , 1 , 2 , 3 , 4 , 5 , 6 };
		Player player;
		
		game.addConnection(connection1);
		game.addConnection(connection2);
		
		game.addPlayer(player1);

		//metemos el player en una lista porque es lo que se tiene que pasar al sendEvent.	
		players.add(player1);
		verify(connection1).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2).sendEvent(EventType.JOIN_GAME,players);

		game.addPlayer(player2);

		players.add(player2);
		verify(connection1, times(2)).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2, times(2)).sendEvent(EventType.JOIN_GAME,players);
				
		for (int cellId: cellIdList) {
			
			if (cellId%2 == 0)
			{
				player = player1;
			}
			else
			{
				player = player2;
			}	
			verify(connection1).sendEvent(EventType.SET_TURN,player);	
			verify(connection2).sendEvent(EventType.SET_TURN,player);	
			
		    reset(connection1);
		    reset(connection2);	
			game.mark(cellId);
			verify(connection1).sendEvent(eq(EventType.MARK), argument.capture());
			verify(connection2).sendEvent(eq(EventType.MARK), argument.capture());
		}		

		verify(connection1).sendEvent(eq(EventType.GAME_OVER),argument2.capture());	
		verify(connection2).sendEvent(eq(EventType.GAME_OVER),argument2.capture());	
		
		WinnerValue winner = argument2.getValue();
		assertEquals(winner.player, player1);
	}
	
	@Test
	public void testFirstPlayerLoses() {
	
        // | O | X | O |
        // | X | O | X |
        // | O |   | X |
		
		int[] cellIdList = { 0 , 1 , 2 , 3 , 4 , 5 , 6 };
		Player player;
		
		game.addConnection(connection1);
		game.addConnection(connection2);
		
		game.addPlayer(player1);
			
		players.add(player1);
		verify(connection1).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2).sendEvent(EventType.JOIN_GAME,players);

		game.addPlayer(player2);

		players.add(player2);
		verify(connection1, times(2)).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2, times(2)).sendEvent(EventType.JOIN_GAME,players);
		
		verify(connection1).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2).sendEvent(EventType.SET_TURN,player1);	
		
		game.mark(8);
		verify(connection1).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2).sendEvent(eq(EventType.MARK),argument.capture());	

		for (int cellId: cellIdList) {
			
			if (cellId%2 == 0)
			{
				player = player2;
			}
			else
			{
				player = player1;
			}	
			verify(connection1).sendEvent(EventType.SET_TURN,player);	
			verify(connection2).sendEvent(EventType.SET_TURN,player);	
			
		    reset(connection1);
		    reset(connection2);	
			game.mark(cellId);
			verify(connection1).sendEvent(eq(EventType.MARK), argument.capture());
			verify(connection2).sendEvent(eq(EventType.MARK), argument.capture());
		}
		
		verify(connection1).sendEvent(eq(EventType.GAME_OVER),argument2.capture());	
		verify(connection2).sendEvent(eq(EventType.GAME_OVER),argument2.capture());		
	
		WinnerValue winner = argument2.getValue();		
		assertEquals(winner.player, player2);
	
	}
	
	@Test
	public void testDraw() {
	
//		| X | O | X |
//		| O | X | X |	
//		| O | X | O |	
		
		game.addConnection(connection1);
		game.addConnection(connection2);
		
		game.addPlayer(player1);
			
		players.add(player1);
		verify(connection1).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2).sendEvent(EventType.JOIN_GAME,players);

		game.addPlayer(player2);

		players.add(player2);
		verify(connection1, times(2)).sendEvent(EventType.JOIN_GAME,players);
		verify(connection2, times(2)).sendEvent(EventType.JOIN_GAME,players);
		
		verify(connection1).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2).sendEvent(EventType.SET_TURN,player1);	
		
		game.mark(0);

		verify(connection1).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection1).sendEvent(EventType.SET_TURN,player2);	
		verify(connection2).sendEvent(EventType.SET_TURN,player2);	
		
		game.mark(1);
		verify(connection1, times(2)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(2)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN,player1);			
		
		game.mark(2);
		verify(connection1, times(3)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(3)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN,player2);	
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN,player2);	
		
		game.mark(3);
		verify(connection1, times(4)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(4)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN,player1);			
		
		game.mark(4);
		verify(connection1, times(5)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(5)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN,player2);	
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN,player2);	
		
		game.mark(8);
		verify(connection1, times(6)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(6)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(4)).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN,player1);	

		game.mark(5);
		verify(connection1, times(7)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(7)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(4)).sendEvent(EventType.SET_TURN,player2);	
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN,player2);	
		
		game.mark(6);
		verify(connection1, times(8)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(8)).sendEvent(eq(EventType.MARK),argument.capture());
		verify(connection1, times(5)).sendEvent(EventType.SET_TURN,player1);	
		verify(connection2, times(5)).sendEvent(EventType.SET_TURN,player1);	
		
		game.mark(7);
		verify(connection1, times(9)).sendEvent(eq(EventType.MARK),argument.capture());	
		verify(connection2, times(9)).sendEvent(eq(EventType.MARK),argument.capture());	
		
		verify(connection1).sendEvent(eq(EventType.GAME_OVER),argument2.capture());	
		verify(connection2).sendEvent(eq(EventType.GAME_OVER),argument2.capture());		
	
		WinnerValue winner = argument2.getValue();		
		assertNull(winner);
	
	}

}
