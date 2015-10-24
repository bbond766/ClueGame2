package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameSetupTests {

	@BeforeClass
	public static void setUp() {
		// TODO
	}
	
	@Test
	public void testLoadPeople() {
		// TODO
		/* Test that we have the right number of people
		 * Test for skipping lines, missing a line, etc.
		 */
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
