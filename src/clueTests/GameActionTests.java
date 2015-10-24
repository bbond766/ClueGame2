package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {

	@BeforeClass
	public static void setUp() {
		// TODO
	}
	
	@Test
	public void checkAccusation() {
		// TODO
		/* Check that good and bad accusations are handled correctly
		 * An accusation is different from a suggestion - in this case
		 * the game says yes or no to the player's accusation.
		 */
	}
	
	@Test
	public void selectTargetLocation() {
		// TODO
		/* Should only choose a valid target.
		 * If no rooms in list, choose a target randomly.
		 * If the list includes a room, it should be chosen
		 * unless player was just in that room. In that case,
		 * the room has the same chance of being chosen as
		 * any other square.
		 * 
		 * So, we need to test for random behavior
		 * -Run the method multiple times and ensure that all
		 *  desired behaviors are seen.
		 *  
		 * Also need to test that a room is always chosen
		 * -Run the method multiple times, ensure that only
		 *  that behavior is seen.
		 */
	}
	
	@Test
	public void disproveSuggestion() {
		// TODO
		/* If a player has a card that's suggested, that card
		 * is shown (i.e., returned).
		 * If the player has multiple cards that match, the
		 * card to be returned is selected randomly.
		 * Once a player has shown a card, no other players
		 * are queried.
		 * If none of the other players has any relevant cards,
		 * the error value null is returned.
		 * 
		 * If a player has a matching card, return it.
		 * -Create a player with known cards, call the disprove
		 *  method, ensure desired card is returned.
		 *  
		 * If the player has multiple cards that match, return
		 * should be random.
		 * -Set up a player, use a loop, ensure that all
		 *  matching cards are returned some number of times.
		 *   
		 * Player class searches its list of cards.
		 * Deciding which player to query can be handled by the
		 * board.
		 * 
		 * How do we know which player is the accusing player?
		 * Test ideas in powerpoint
		 */
	}
	
	@Test
	public void makeSuggestion() {
		// TODO
		/* Suggestion should not include any cards that are not
		 * in the player's hand or that have been seen.
		 * Suggestion should choose randomly from the unseen cards.
		 */
	}
	
	@Test
	public void makeAccusation() {
		// TODO
		/* All 3 cards need to be correct
		 * How do we store solution?
		 * Do we pass in cards, strings, solution struct?
		 */
	}

}
