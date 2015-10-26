package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class GameSetupTests {
	
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Loads the config files, calculates adjacencies, selects the answer, and deals the cards
		board = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		board.initialize();
	}
	
	@Test
	public void testLoadPeople() {
		// TODO
		/* Test that we have the right number of people
		 * Test for skipping lines, missing a line, etc.
		 * Right name, color, starting position
		 */
		ArrayList<Player> testList = board.getPlayers();
		assertEquals(6, testList.size());
		assertTrue(testList.get(0).getName() == "Miss Scarlet");
		assertTrue(testList.get(1).getName() == "Mrs. Peacock");
		assertTrue(testList.get(2).getName() == "Professor Plum");
		assertTrue(testList.get(3).getName() == "Colonel Mustard");
		assertTrue(testList.get(4).getName() == "Mrs. White");
		assertTrue(testList.get(5).getName() == "Mr. Green");
	}
	
	@Test
	public void testLoadCards() {
		// TODO
		/* Test if cards are loaded correctly
		 * Every card should have a name and a CardType
		 */
	}
	
	@Test
	public void testDealCards() {
		// TODO
		/* All cards should be dealt
		 * All players should have roughly the same number of cards
		 * The same card should not be given to >1 player
		 */
	}

}
