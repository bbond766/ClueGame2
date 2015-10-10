package game;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board board;
	
	@Before
	public void init() {
		board = new Board();
	}
	
	@Test
	public void topLeftCorner() {
		Assert.assertTrue(board.getAdjList(new BoardCell(0, 0)) != null);
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
	
	@Test
	public void testAdjacency00()
	{
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency01()
	{
		BoardCell cell = board.getCell(0,1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency10()
	{
		BoardCell cell = board.getCell(0,1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency11()
	{
		BoardCell cell = board.getCell(0,1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testTargets00_3()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}

	@Test
	public void testTargets33_2()
	{
		BoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
	}
}
