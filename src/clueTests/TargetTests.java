package clueTests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TargetTests {
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
	public void testTargetsOneStep() {
		board.calcTargets(5, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(6, 20)));	
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 21)));	
	}
	
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 7)));
		assertTrue(targets.contains(board.getCellAt(1, 8)));
		assertTrue(targets.contains(board.getCellAt(0, 9)));
	}
	
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(1, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(2, 7)));
		assertTrue(targets.contains(board.getCellAt(3, 8)));
		assertTrue(targets.contains(board.getCellAt(2, 9)));
		assertTrue(targets.contains(board.getCellAt(1, 10)));
		assertTrue(targets.contains(board.getCellAt(0, 9)));
		assertTrue(targets.contains(board.getCellAt(1, 8)));
		assertTrue(targets.contains(board.getCellAt(0, 7)));
	}
	
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(7, 6, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		assertTrue(targets.contains(board.getCellAt(8, 5)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(8, 3)));
		assertTrue(targets.contains(board.getCellAt(6, 5)));
		assertTrue(targets.contains(board.getCellAt(9, 8)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));
		assertTrue(targets.contains(board.getCellAt(7, 2)));
		assertTrue(targets.contains(board.getCellAt(8, 7)));
		assertTrue(targets.contains(board.getCellAt(10, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		assertTrue(targets.contains(board.getCellAt(7, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(7, 8)));
	}	
	
	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(16, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(board.getCellAt(17, 3).isDoorway());
		assertTrue(board.getCellAt(17, 4).isDoorway());
		assertTrue(board.getCellAt(17, 5).isDoorway());

		board.calcTargets(16, 10, 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 9)));
		assertTrue(targets.contains(board.getCellAt(17, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(14, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 11)));
		assertTrue(targets.contains(board.getCellAt(16, 12)));
		assertTrue(board.getCellAt(16, 12).isDoorway());
	}
	
	@Test
	public void testRoomExit()
	{
		board.calcTargets(16, 22, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 22)));

		board.calcTargets(4, 6, 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 9)));
		assertTrue(targets.contains(board.getCellAt(5, 8)));
		assertTrue(targets.contains(board.getCellAt(3, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(2, 7)));
	}

}
