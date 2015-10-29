package clueTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Solution;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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
			else if (targetCell == board.getCellAt(1, 9))
				loc_1_7++;
			else if (targetCell == board.getCellAt(2,  10))
				loc_2_10++;
			else {
				fail("Invalid target selected, cell " + targetCell + " is not a valid target");
			}
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
		testPlayer.setLastVisited("Kave Room");
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
				fail("Invalid target selected, cell " + targetCell + " is not a valid target");
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
		Board newBoard=new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");  //Create new Board but don't deal cards.
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
			//set up the board for a new random distribution of cards each iteration
			Board testBoard=new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
			
			//add only computer players: human interaction is not being tested here
			//and anyway, no one wants to interact 100 times just to test
			testBoard.addPlayer(new ComputerPlayer("Miss Scarlet","red",0,0));
			testBoard.addPlayer(new ComputerPlayer("Colonel Mustard","yellow",0,0));
			testBoard.addPlayer(new ComputerPlayer("Mr. Green","green",0,0));
			testBoard.addPlayer(new ComputerPlayer("Professor Plum","purple",0,0));
			testBoard.addPlayer(new ComputerPlayer("Mrs. White","white",0,0));
			
			//load the configs, except the players
			try{
				testBoard.loadBoardConfig();
				testBoard.loadCardConfig();
				testBoard.loadRoomConfig();
			}
			catch(BadConfigFormatException e){
				e.printStackTrace();
			}
			
			testBoard.setAnswer();//set the test answer
			
			testBoard.dealCards();//populate player hands
			
			assertTrue(testBoard.handleSuggestion(testSolution, "Miss Scarlet", new BoardCell(0,0))!=null);//test
		}
		
		//now to test human interaction
		HumanPlayer testhuman=new HumanPlayer("Mrs. Peacock","blue",0,0);
		testhuman.giveCard(new Card("Miss Scarlet",CardType.PERSON));
		testhuman.giveCard(new Card("Candlestick",CardType.WEAPON));
	}

	@Test
	public void makeSuggestionRandom() {
		// the first test will call the makeSuggestion function
		//100 times and keep track of two players and one weapon.  each time through
		//the loop it will verify that it hasn't called any cards that the player
		//holds. Each time through the loop it will also verify that the room 
		//choice is the room the player is in and no other. 
		//After the loops it will verify that it has called the players and 
		//the weapon an appropriate number of times.
		

		ComputerPlayer testPlayer =  new ComputerPlayer("Mr Green", "blue", 13,3);
		int MrsWhite = 0;
		int MrsPeacock = 0;
		int MissScarlet = 0;
		int leadPipe = 0;
		int revolver = 0;
		int candlestick = 0;
		Board testBoard = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		try {
			testBoard.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadCardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadPlayerConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		testBoard.setAnswer();
		Set<String> unseen = new HashSet<String>(Arrays.asList("Miss Scarlet", "Mrs. Peacock", "Mrs. White", "Lead Pipe", "Revolver", "Candlestick"));
		
		for (Card c : testBoard.getChoices())
			if (!unseen.contains(c.getName()))
				testBoard.addSeenCard(c);
		
		Solution testSolution = null;
		
		for (int i=0; i<100;++i){
			testSolution = testPlayer.makeSuggestion(testBoard);
			if(testSolution.person.equals("Mrs. Peacock"))
				MrsPeacock++;
			else if(testSolution.person.equals("Mrs. White"))
				MrsWhite++;
			else if(testSolution.person.equals("Miss Scarlet"))
				MissScarlet++;
			if(testSolution.weapon.equals("Lead Pipe")) 
				leadPipe++;
			if(testSolution.weapon.equals("Revolver"))
				revolver++;
			if(testSolution.weapon.equals("Candlestick"))
				candlestick++;
		}

		assertTrue(MrsWhite > 8);
		assertTrue(MrsPeacock > 8);
		assertTrue(leadPipe > 8);
		assertTrue(revolver> 8);
	}

	@Test
	public void makeSuggestionSolution(){
		//the second test will put three cards in the answer and deal the player
				//three cards, and put the rest of the cards in the cardSeen array.
				//then when the makeSuggestion function is called, it should pick the 
				//solution because that is the only choices left to make.
		
		
		ComputerPlayer testPlayer =  new ComputerPlayer("Mr Green", "blue", 5,6);
		Board testBoard = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		try {
			testBoard.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadCardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadPlayerConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			testBoard.loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		testBoard.setAnswer();
		testBoard.dealOnePlayer();//this function is in Board class for testing only
		testBoard.fillSeenCards();//this function is in Board class for testing only

		Solution testSolution = testPlayer.makeSuggestion(testBoard);
		assertEquals(testSolution.person, testBoard.getAnswer().person);
		assertEquals(testSolution.weapon, testBoard.getAnswer().weapon);
		assertEquals(testSolution.room, testBoard.getAnswer().room);
		


		// TODO
		/* Suggestion should not include any cards that are not
		 * in the player's hand or that have been seen.
		 * Suggestion should choose randomly from the unseen cards.
		 */

	}
}
