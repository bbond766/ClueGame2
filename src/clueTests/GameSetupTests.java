package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class GameSetupTests {
	
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Load the config files, calculates adjacencies, selects the answer, and deals the cards
		board = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		board.initialize();
	}
	
	@Test
	public void testLoadPeople() {
		/* Test that all players were loaded with correct name, color,
		 * and location. Also tests whether player is human or computer-
		 * controlled.
		 */
		
		ArrayList<Player> testList = board.getPlayers();
		assertEquals(6, testList.size());
		
		// Test the human player
		assertEquals(testList.get(0).getName(), "Miss Scarlet");
		assertTrue(testList.get(0).isHuman());
		assertEquals(testList.get(0).getColor().getRed(),255 );
		assertEquals(testList.get(0).getRow(), 6);
		assertEquals(testList.get(0).getColumn(), 0);
		
		// Test the first computer player
		assertEquals(testList.get(1).getName(), "Mrs. Peacock");
		assertFalse(testList.get(1).isHuman());
		assertEquals(testList.get(1).getColor().getBlue(), 255);
		assertEquals(testList.get(1).getRow(), 15);
		assertEquals(testList.get(1).getColumn(), 0);

		// Test the last computer player
		assertEquals(testList.get(5).getName(), "Mr. Green");
		assertFalse(testList.get(5).isHuman());
		assertEquals(testList.get(5).getColor().getGreen(), 255);
		assertEquals(testList.get(5).getRow(), 21);
		assertEquals(testList.get(5).getColumn(), 9);
	}
	
	@Test
	public void testLoadCards() {
		/* Test that the deck contains the correct number of cards,
		 * correct number of each type of card, and if names were
		 * loaded correctly.
		 */
		
		/* Create new board and load files, but do not select answer,
		 * shuffle, or deal */
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

		
		// Test that every card was loaded into the deck
		ArrayList<Card> testList = newBoard.getDeck();
		assertEquals(testList.size(), 20);
		
		// Test that each type of card has the correct number
		int numRooms = 0;
		int numPersons = 0;
		int numWeapons = 0;
		
		for (Card c : testList) {
			if (c.getType() == CardType.ROOM)
				numRooms++;
			else if (c.getType() == CardType.PERSON)
				numPersons++;
			else
				numWeapons++;
		}
		
		assertEquals(numRooms, 8);
		assertEquals(numPersons, 6);
		assertEquals(numWeapons, 6);
		
		// Test that the deck contains example cards
		// I don't know how to do this any less awkwardly
		boolean foundCanaryRoom = false;
		boolean foundMrsWhite = false;
		boolean foundCandlestick = false;
		for (Card c: testList) {
			if (c.getName().equals("Canary Room"))
				foundCanaryRoom = true;
			else if (c.getName().equals("Mrs. White"))
				foundMrsWhite = true;
			else if (c.getName().equals("Candlestick"))
				foundCandlestick = true;
		}
		assertTrue(foundCanaryRoom);
		assertTrue(foundMrsWhite);
		assertTrue(foundCandlestick);
	}
	
	@Test
	public void testDealCards() {
		/* Test that all cards are dealt, all players have
		 * roughly the same number of cards, and no two
		 * players have the same card
		 */
		// Test all cards have been dealt from the deck
		
		ArrayList<Player> testPlayers = board.getPlayers();
		int totalCards = 0;
		for (Player p : testPlayers)
			totalCards += p.getHandSize();
		assertEquals(totalCards, 17);    // 3 cards are stored as the answer, so 17 cards are dealt
		
		// Test all players have similar hand sizes
		for (Player p : testPlayers)
			for (Player o : testPlayers)
				if (Math.abs(p.getHandSize() - o.getHandSize()) > 1)
					fail();    // Fails if any player has >=2 more cards than any other player
		
		// Test no two players share a card
		for (Player p : testPlayers)
			for (Player o : testPlayers)
				if (p != o)
					assertTrue(Collections.disjoint(p.getHand(), o.getHand()));
	}

}
