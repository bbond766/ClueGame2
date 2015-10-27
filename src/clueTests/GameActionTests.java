package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Solution;
import clueGame.ComputerPlayer;
import clueGame.Card;
import clueGame.CardType;
import clueGame.BoardCell;
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
	public void selectRandomTargetLocation() {	
		// Test no rooms in targets, choose target randomly
		ComputerPlayer testPlayer = new ComputerPlayer("Mrs. Peacock", "blue", 6, 10);
		board.calcTargets(0,  10, 2);
		int loc_0_8 = 0;
		int loc_1_7 = 0;
		int loc_2_10 = 0;
		// Test 100 times
		for (int i=0; i<100; i++) {
			BoardCell targetCell = testPlayer.pickLocation(board.getTargets());
			if (targetCell == board.getCellAt(0, 8))
				loc_0_8++;
			else if (targetCell == board.getCellAt(1, 7))
				loc_1_7++;
			else if (targetCell == board.getCellAt(2,  10))
				loc_2_10++;
			else
				fail("Invalid target selected");
		}
		// Make sure we selected 100 times
		assertEquals(100, loc_0_8 + loc_1_7 + loc_2_10);
		// Make sure each target was selected more than once
		assertTrue(loc_0_8 > 10);
		assertTrue(loc_1_7 > 10);
		assertTrue(loc_2_10 > 10);
	}
	
	@Test
	public void selectRandomTargetLocationWithRoom() {
		/* Test that the computer randomly picks from all locations
		 * when targets includes a room that was just visited.
		 */
		ComputerPlayer testPlayer = new ComputerPlayer("Mrs. Peacock", "blue", 8, 3);
		board.calcTargets(8, 3, 1);
		int loc_7_3 = 0;
		int loc_8_2 = 0;
		int loc_8_4 = 0;
		int loc_9_3 = 0;
		// Test 100 times
		for (int i=0; i<100; i++) {
			BoardCell targetCell = testPlayer.pickLocation(board.getTargets());
			if (targetCell == board.getCellAt(7, 3))
				loc_7_3++;
			else if (targetCell == board.getCellAt(8, 2))
				loc_8_2++;
			else if (targetCell == board.getCellAt(8, 4))
				loc_8_4++;
			else if (targetCell == board.getCellAt(9, 3))
				loc_9_3++;
			else
				fail("Invalid target selected");
		}
		// Make sure we selected 100 times
		assertEquals(100, loc_7_3 + loc_8_2 + loc_8_4 + loc_9_3);
		// Make sure each target was selected more than once
		assertTrue(loc_7_3 > 8);
		assertTrue(loc_8_2 > 8);
		assertTrue(loc_8_4 > 8);
		assertTrue(loc_9_3 > 8);
	}
	
	@Test 
	public void selectRoom() {
		/* Test that the computer will always pick a room if it is
		 * in the targets set and wasn't just visited.
		 */
		ComputerPlayer testPlayer = new ComputerPlayer("Mrs. Peacock", "blue", 8, 3);
		board.calcTargets(8, 3, 1);
		// Test 100 times
		for (int i=0; i<100; i++) {
			BoardCell targetCell = testPlayer.pickLocation(board.getTargets());
			assertEquals(targetCell, board.getCellAt(9, 3));
		}
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


		Board newBoard=new Board();  //Create new Board but don't deal cards.
		newBoard.setAnswer();    // Debugging method sets answer to Miss Scarlet, Canary Room, Candlestick
		
		ComputerPlayer testAI=new ComputerPlayer("Miss Scarlet","red",0,0); //Invalid Location, but irrelevant to this test.
		
		//Sanity Check
		assertTrue(newBoard.checkAccusation(new Solution("Canary Room", "Miss Scarlet", "Candlestick")));
		
		//Generate an incorrect solution
		Solution testSolution=new Solution("Ballroom","Colonel Mustard","Knife");
		
		
		//Give the test player Colonel Mustard.
		Card testPerson=new Card("Colonel Mustard",CardType.PERSON);
		testAI.giveCard(testPerson);
		
		//Test the disproveSuggestion method
		assertTrue(testAI.disproveSuggestion(testSolution)==testPerson);
		
		//Give the test player the Ballroom
		Card testroom=new Card("Ballroom",CardType.ROOM);
		testAI.giveCard(testroom);
		
		//Test the disproveSuggestion method when the test player has two cards to disprove the suggestion with
		assertTrue(testAI.disproveSuggestion(testSolution)!=null); //The disproveSuggestion method only returns null if it can't find a card to disprove the suggestion with
		
		
		//Now we test players in order
		//Because the solution is known to not be testSolution, this should return a different card each time, but never null 
		for(int i=0;i<100;++i){
			System.out.println(i);
			Board testBoard=new Board();
			testBoard.initialize();//start again with a clean slate
			testBoard.setAnswer();//set the test answer
			testBoard.dealCards();//populate player hands
			assertTrue(testBoard.handleSuggestion(testSolution, "Miss Scarlet", new BoardCell(0,0))!=null);//test
		}

	}

	@Test
	public void makeSuggestion() {
		ComputerPlayer testPlayer =  new ComputerPlayer("Miss Scarlet", "blue", 13,3);
		
		
		
		// TODO
		/* Suggestion should not include any cards that are not
		 * in the player's hand or that have been seen.
		 * Suggestion should choose randomly from the unseen cards.
		 */

	}
}
