package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Cell;

public class BoardTest {


	private Board board;
	private Cell cell;
	
	@Before
	public void setUp() throws Exception {
	
		board = new Board();
			
	}

	@Test
	public void testFirstPlayerWins() {
	
        // | X | X | X |
        // | O | O |   |
        // |   |   |   |

		cell = board.getCell(0);			
	    cell.value = "X";	
		cell.active = false;	

		cell = board.getCell(3);			
	    cell.value = "O";	
		cell.active = false;
		
		cell = board.getCell(1);			
	    cell.value = "X";	
		cell.active = false;
		
		cell = board.getCell(4);			
	    cell.value = "O";	
		cell.active = false;
		
		cell = board.getCell(2);			
	    cell.value = "X";	
		cell.active = false;
		
	    assertNotNull(board.getCellsIfWinner("X"));
	    assertNull(board.getCellsIfWinner("O"));	
	}
	
	@Test
	public void testFirstPlayerLoses() {
	
        // | X | X |   |
        // | O | O | O |
        // | X |   |   |
		
		cell = board.getCell(0);			
	    cell.value = "X";	
		cell.active = false;	

		cell = board.getCell(3);			
	    cell.value = "O";	
		cell.active = false;
		
		cell = board.getCell(1);			
	    cell.value = "X";	
		cell.active = false;
		
		cell = board.getCell(4);			
	    cell.value = "O";	
		cell.active = false;
		
		cell = board.getCell(6);			
	    cell.value = "X";	
		cell.active = false;
	
		cell = board.getCell(5);			
	    cell.value = "O";	
		cell.active = false;
		
	    assertNull(board.getCellsIfWinner("X"));
	    assertNotNull(board.getCellsIfWinner("O"));
	}
	
	@Test
	public void testDraw() {
		
		for (int i = 0; i < 6; i++) {
			cell = board.getCell(i);
			cell.active = false;
			if (i%2 == 0)  
			{
				cell.value = "X";	
			}
			else
			{
				cell.value = "O";
			}
		}
		
		cell = board.getCell(7);
		cell.active = false;
		cell.value = "X";
		
		cell = board.getCell(6);
		cell.active = false;
		cell.value = "O";

		cell = board.getCell(8);
		cell.active = false;
		cell.value = "X";		
		
//		| X | O | X |
//		| O | X | O |	
//		| O | X | X |	
		
		assertTrue(board.checkDraw());		
	}
	
}
