package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Solution;

public class GameActionTests {
	
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Load the config files, calculates adjacencies, selects the answer, and deals the cards
		board = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		board.initialize();
	}
	
	@Test
	public void makeAccusation() {
		/* Test whether the board appropriately handles correct
		 * and incorrect accusations
		 */
		
		// Create new board but do not randomly select answer
		Board newBoard = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		try {
			newBoard.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			newBoard.loadCardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			newBoard.loadPlayerConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			newBoard.loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		newBoard.setAnswer();    // Debugging method sets answer to Miss Scarlet, Canary Room, Candlestick
		newBoard.dealCards();
		
		// Test correct solution
		assertTrue(newBoard.checkAccusation(new Solution("Canary Room", "Miss Scarlet", "Candlestick")));
		// Test incorrect room
		assertFalse(newBoard.checkAccusation(new Solution("Rumpus Room", "Miss Scarlet", "Candlestick")));
		// Test incorrect person
		assertFalse(newBoard.checkAccusation(new Solution("Canary Room", "Mr. Green", "Candlestick")));
		// Test incorrect weapon
		assertFalse(newBoard.checkAccusation(new Solution("Canary Room", "Miss Scarlet", "Lead Pipe")));		
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

}
