package experiment;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class IntBoardTest {
	private IntBoard board;
	
	@Before
	public void init() {
		board = new IntBoard();
	}
	
	@Test
	public void topLeftCorner() {
		assertTrue(board.getAdjList(new BoardCell(0, 0)) != null);
	}
	
	@Test public void bottomRightCorner() {
		assertTrue(board.getAdjList(new BoardCell(3, 3)) != null);
	}

	@Test public void rightEdge() {
		assertTrue(board.getAdjList(new BoardCell(1, 3)) != null);
	}

	@Test public void leftEdge() {
		assertTrue(board.getAdjList(new BoardCell(3, 0)) != null);
	}

	@Test public void secondColumn() {
		assertTrue(board.getAdjList(new BoardCell(1, 1)) != null);
	}

	@Test public void secondFromLastColumn() {
		assertTrue(board.getAdjList(new BoardCell(2, 2)) != null);
	}

}
