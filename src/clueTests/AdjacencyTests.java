package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjacencyTests {
	private static Board board;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt");
		board.initialize();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void walkwaysOnlyTest() {
		LinkedList<BoardCell> testList = board.getAdjList(13, 14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(12, 14)));
		assertTrue(testList.contains(board.getCellAt(14, 14)));
		assertTrue(testList.contains(board.getCellAt(13, 13)));
		assertTrue(testList.contains(board.getCellAt(13, 15)));
	}

	@Test
	public void boardEdgeTest() {
		LinkedList<BoardCell> testList = board.getAdjList(7, 0);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));

		testList = board.getAdjList(0, 8);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertTrue(testList.contains(board.getCellAt(1, 8)));
		assertTrue(testList.contains(board.getCellAt(0, 9)));

		testList = board.getAdjList(5, 22);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 22)));
		assertTrue(testList.contains(board.getCellAt(5, 21)));
		assertTrue(testList.contains(board.getCellAt(6, 22)));

		testList = board.getAdjList(21, 10);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(21, 9)));
		assertTrue(testList.contains(board.getCellAt(20, 10)));
	}

	@Test
	public void roomEdgeTest() {
		LinkedList<BoardCell> testList = board.getAdjList(0, 17);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertTrue(testList.contains(board.getCellAt(1, 17)));

		testList = board.getAdjList(14, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 4)));
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
	}

	@Test
	public void adjDoorwayTest() {
		LinkedList<BoardCell> testList = board.getAdjList(15, 22);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 22)));
		assertTrue(testList.contains(board.getCellAt(15, 21)));
		assertTrue(testList.contains(board.getCellAt(16, 22)));
		assertTrue(board.getCellAt(16, 22).isDoorway());
		
		testList = board.getAdjList(2, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 16)));
		assertTrue(testList.contains(board.getCellAt(2, 14)));
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		assertTrue(testList.contains(board.getCellAt(3, 15)));
		assertTrue(board.getCellAt(2, 14).isDoorway());

		testList = board.getAdjList(6, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		assertTrue(board.getCellAt(5, 5).isDoorway());

		testList = board.getAdjList(16, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(board.getCellAt(17, 5).isDoorway());
	}

	@Test
	public void doorwayTest() {
		LinkedList<BoardCell> testList = board.getAdjList(16, 22);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 22)));
		assertTrue(board.getCellAt(16, 22).isDoorway());
		
		testList = board.getAdjList(2, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 15)));
		assertTrue(board.getCellAt(2, 14).isDoorway());
	}
}
