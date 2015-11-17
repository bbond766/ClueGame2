package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Board extends JPanel{
	public static final int MAX_ROWS = 50;
	public static final int MAX_COLS = 50;
	private int numRows;
	private int numColumns;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private BoardCell[][] board;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String cardConfigFile;
	private Solution theAnswer;
	private BoardCell validCell;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> suggestionChoices = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Card> seenCards =  new ArrayList<Card>();
	
	public Board() {
		// Default constructor creates a new board using Cyndi Rader's config files
		this("ClueLayout.csv", "ClueLegend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		addMouseListener(new CellListener());
	}

	public Board(int rows, int columns) {
		// Constructor used to test 4x4 board, not used in actual game
		numRows = rows;
		numColumns = columns;
		addMouseListener(new CellListener());
		
		board = new BoardCell[numRows][numColumns];
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				board[i][j] = new BoardCell(i, j);
	}
	
	public Board(String layoutFile, String legendFile) {
		// Constructor for using custom board with our player and card configs
		boardConfigFile = layoutFile;
		roomConfigFile = legendFile;
		playerConfigFile = "ClueLayout/Players.txt";
		cardConfigFile = "ClueLayout/Cards.txt";
		addMouseListener(new CellListener());
	} 

	public Board(String layoutFile, String legendFile, String playerFile, String cardFile) {
		// Parameterized constructor
		boardConfigFile = layoutFile;
		roomConfigFile = legendFile;
		playerConfigFile = playerFile;
		cardConfigFile = cardFile;
		addMouseListener(new CellListener());
	} 
	
	public BoardCell getCell(int row, int column) {
		return board[row][column];
	}
	
	public int getNumberOfRooms(){
		return rooms.size();
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Card> getSeenCards(){
		return seenCards;
	}
	
	public void addSeenCard(Card entry){
		seenCards.add(entry);
	}

	public Solution getAnswer() {
		return theAnswer;
	}
	
	public ArrayList<Card> getChoices(){
		return suggestionChoices;
	}
	
	public BoardCell getCellAt(int row, int column){
		return board[row][column];
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int row, int column) {
		return getAdjList(board[row][column]);
	}
	
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	
	public BoardCell getValidCell(){
		return validCell;
	}
	
	public void initialize() {
		/* Loads all config files, then randomly chooses three cards for the answer,
		 * deals the remaining cards, calculates adjacencies for each cell, and
		 * updates each cell
		 */
		rooms = new HashMap<Character, String>();
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		try {
			loadPlayerConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		try {
			loadCardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		selectAnswer();
		dealCards();
		calcAdjacencies();
		updateBoard();
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		// Fills the rooms Map and the deck with cards found in the card config file
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
				// room[0] is the initial, room[1] is the name, and room[2]
				// determines whether or not the room is a card
				String[] room = in.nextLine().split(", ");
				if(room.length != 3)
					throw new BadConfigFormatException("Wrong number of fields in room config");

				rooms.put(room[0].charAt(0), room[1]);
				
				if (room[2].equals("Card")) 
					deck.add(new Card(room[1], CardType.ROOM));
			}
			
			in.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException {
		// Determines actual number of rows and columns and sets the DoorDirection
		// and name for each room
		board = new BoardCell[MAX_ROWS][MAX_COLS];

		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);

			numRows = 0;
			while(in.hasNextLine()) {
				// row[n] represents the nth column in the current row
				String[] row = in.nextLine().split(",");
				numColumns = row.length;
				
				for(int i = 0; i < numColumns; i++) {
					if(!rooms.containsKey(row[i].charAt(0)))
						throw new BadConfigFormatException("Unrecognized room");

					board[numRows][i] = new BoardCell(numRows, i, row[i].charAt(0));
					if(row[i].length() > 1) {
						switch(row[i].charAt(1)) {
						case 'U':
							board[numRows][i].setDoorDirection(DoorDirection.UP);
							break;
						case 'D':
							board[numRows][i].setDoorDirection(DoorDirection.DOWN);
							break;
						case 'L':
							board[numRows][i].setDoorDirection(DoorDirection.LEFT);
							break;
						case 'R':
							board[numRows][i].setDoorDirection(DoorDirection.RIGHT);
							break;
						case 'N':
							String cellName = rooms.get(row[i].charAt(0));
							board[numRows][i].setName(cellName);
							break;
						}
					}
				}

				numRows++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		// Loads player information from the player config file
		try {
			FileReader reader = new FileReader(playerConfigFile);
			Scanner in = new Scanner(reader);
			
			while (in.hasNextLine()) {
				// player[0] is the name, player[1] determines whether the player is human or computer controlled,
				// player[2] is the player's color, player[3] is the player's starting row, and player[4] is the
				// player's starting column
				String[] player = in.nextLine().split(", ");
				if (player.length != 5)
					throw new BadConfigFormatException("Too many fields in player config file");
				
				if (Integer.parseInt(player[3]) >= numRows || Integer.parseInt(player[3]) < 0)
					throw new BadConfigFormatException("row value is " + player[3] + ", must be between 0 and " + numRows);
				
				if (Integer.parseInt(player[4]) >= numColumns || Integer.parseInt(player[4]) < 0)
					throw new BadConfigFormatException("column value is " + player[4] + ", must be between 0 and " + numColumns);
				
				// Add human player to list
				if (player[1].equals("Human"))
					players.add(new HumanPlayer(player[0], convertColor(player[2]), Integer.parseInt(player[3]), Integer.parseInt(player[4])));
				
				// Add AI player to list
				else if (player[1].equals("Computer"))
					players.add(new ComputerPlayer(player[0], convertColor(player[2]), Integer.parseInt(player[3]), Integer.parseInt(player[4])));
				else
					throw new BadConfigFormatException("Player must be either Human or Computer controlled, field says: " + player[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadCardConfig() throws BadConfigFormatException {
		// Loads each card in the card config file into the deck
		try {
			FileReader reader = new FileReader(cardConfigFile);
			Scanner in = new Scanner(reader);
			
			while (in.hasNextLine()) {
				// card[0] is the card name, card[1] is the card's type
				String[] card = in.nextLine().split(", ");
				if (card.length != 2)
					throw new BadConfigFormatException("Too many fields in card config file");
				
				// Add card to deck
				if (card[1].equals("Person")){
					deck.add(new Card(card[0], CardType.PERSON));
					suggestionChoices.add(new Card(card[0], CardType.PERSON));
				}
				else if (card[1].equals("Weapon")){
					deck.add(new Card(card[0], CardType.WEAPON));
					suggestionChoices.add(new Card(card[0], CardType.WEAPON));
				}
				else
					throw new BadConfigFormatException("Invalid type: " + card[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Color convertColor(String strColor) {
		// We can use reflection to convert the string to a color
		Color color; 
		try {     
			// Be sure to trim the color, we don't want spaces around the name
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

	public void calcAdjacencies(){
		// Calculates the adjacency list for each cell in the board
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				adjMtx.put(board[i][j], getAdjList(board[i][j]));
	}

	public void calcTargets(int row, int column, int distance) {
		// Converts row and column to a BoardCell and calls again, used in unit tests
		calcTargets(board[row][column], distance);
	}
	
	public void calcTargets(BoardCell boardCell, int distance){
		// Outer function calls recursive function findAllTargets
		targets = findAllTargets(boardCell, visited, distance);
	}
	
	private Set<BoardCell> findAllTargets(BoardCell boardCell, Set<BoardCell> oldVisited, int distance) {
		// Recursively calculates all targets the player can reach after rolling the die
		HashSet<BoardCell> visited = new HashSet<BoardCell>(oldVisited);
		visited.add(boardCell);

		HashSet<BoardCell> targets = new HashSet<BoardCell>();
		HashSet<BoardCell> adjacent = new HashSet<BoardCell>();
		LinkedList<BoardCell> cells = adjMtx.get(boardCell);
		
		for(BoardCell target : cells)
			if(!visited.contains(target))
				adjacent.add(target);

		if(distance == 1) {
			return adjacent;
		}
		
		for(BoardCell target : adjacent) {
			if(target.isDoorway())
				targets.add(target);
			else
				targets.addAll(findAllTargets(target, visited, distance-1));
		}
		
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
		// Returns a linked list of cells adjacent to boardCell
		// Adjacent means we can reach them with a die roll of 1
		LinkedList<BoardCell> list = new LinkedList<BoardCell>();
		
		if(boardCell.isDoorway()) {
			switch(boardCell.getDoorDirection()) {
			case UP:
				list.add(board[boardCell.getRow() - 1][boardCell.getColumn()]);
				break;
			case DOWN:
				list.add(board[boardCell.getRow() + 1][boardCell.getColumn()]);
				break;
			case LEFT:
				list.add(board[boardCell.getRow()][boardCell.getColumn() - 1]);
				break;
			case RIGHT:
				list.add(board[boardCell.getRow()][boardCell.getColumn() + 1]);
				break;
			}
		}
		else if(boardCell.getInitial() != 'W') {}    // If cell is not a door or walkway then it has no adjacent cells
		else {
			if(boardCell.getRow() - 1 >= 0) {
				BoardCell cell = board[boardCell.getRow() - 1][boardCell.getColumn()];
				if((!cell.isDoorway() && cell.getInitial() == 'W') || cell.getDoorDirection() == DoorDirection.DOWN)
					list.add(cell);
			}
			if(boardCell.getRow() + 1 < numRows) {
				BoardCell cell = board[boardCell.getRow() + 1][boardCell.getColumn()];
				if((!cell.isDoorway() && cell.getInitial() == 'W') || cell.getDoorDirection() == DoorDirection.UP)
					list.add(cell);
			}
			if(boardCell.getColumn() - 1 >= 0) {
				BoardCell cell = board[boardCell.getRow()][boardCell.getColumn() - 1];
				if((!cell.isDoorway() && cell.getInitial() == 'W') || cell.getDoorDirection() == DoorDirection.RIGHT)
					list.add(cell);
			}
			if(boardCell.getColumn() + 1 < numColumns) {
				BoardCell cell = board[boardCell.getRow()][boardCell.getColumn() + 1];
				if((!cell.isDoorway() && cell.getInitial() == 'W') || cell.getDoorDirection() == DoorDirection.LEFT)
					list.add(cell);
			}
		}
		
		return list;
	}
	
	public void shuffleDeck() {
		// Puts the cards in random order within the deck
		Collections.shuffle(deck);
	}
	
	public void selectAnswer() {
		// Selects answer from first room, person, and weapon in
		// the deck after shuffling
		
		shuffleDeck();
		String room=null;
		String person=null;
		String weapon=null;
		
		for (int i=0; i<deck.size(); i++)
			if (deck.get(i).getType() == CardType.ROOM) {
				room = deck.get(i).getName();
				deck.remove(i);
				break;
			}
		for (int i=0; i<deck.size(); i++)
			if (deck.get(i).getType() == CardType.PERSON) {
				person = deck.get(i).getName();
				deck.remove(i);
				break;
			}
		for (int i=0; i<deck.size(); i++)
			if (deck.get(i).getType() == CardType.WEAPON) {
				weapon = deck.get(i).getName();
				deck.remove(i);
				break;
			}
		
		theAnswer = new Solution(room, person, weapon);
	}
		
	public void dealCards() {
		shuffleDeck();
		
		ArrayList<Card> clone = new ArrayList<Card>();
		for (Card c : deck)
			clone.add(c.clone());
		
		int counter = 0;
		while (!clone.isEmpty()) {                          // while the deck has cards
			players.get(counter).giveCard(clone.get(0));    // give the top card in the deck to the current player
			clone.remove(0);                                // remove the top card from the deck
			counter = (counter + 1) % players.size();      // look at the next player
		}
	}
	
 	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked) {
 		// First checks if the suggestion is correct (i.e. it is the same as theAnswer)
 		// If not, it checks each player's hand for a card to disprove the suggestion
		if(this.checkAccusation(suggestion)){
			return null;
		}
		for(Player player : this.players){
			if(player.disproveSuggestion(suggestion)!=null){
				return player.disproveSuggestion(suggestion);
			}
		}
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if (!accusation.room.equals(theAnswer.room))
			return false;
		if (!accusation.person.equals(theAnswer.person))
			return false;
		if (!accusation.weapon.equals(theAnswer.weapon))
			return false;
		return true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		for(int i=0;i<numRows;++i){
			for(int j=0;j<numColumns;++j){
				board[i][j].draw(g);
			}
		}
	}
	
	public void highlightTargets(){
		for(BoardCell bc : targets){
			bc.repaint();
		}
	}

	public void updateBoard() {
		for (int i=0; i<numRows; i++)
			for (int j=0; j<numColumns; j++)
				board[i][j].updateCell(players);
	}
	
	public class CellListener implements MouseListener{

		BoardCell bc;
		int size = 25;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			bc = getCellAt(e.getPoint().y%size, e.getPoint().x%size);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	
		public void checkIsValid(){
			for(BoardCell b : targets){
				if(b == bc){
					validCell = bc;
				}
			}
		}
	
	}
	
	////////////////////////////////////////////
	//            Testing Methods              //
	////////////////////////////////////////////
	
	public void addPlayer(Player player){
		players.add(player);
	}

	public void setAnswer() {
		/* For debugging, sets the answer to Miss Scarlet,
		 * Canary Room, and Candlestick.
	     */
		
		for (int i=0; i<deck.size(); i++) {
			if (deck.get(i).getName() == "Miss Scarlet")
				deck.remove(i);
			if (deck.get(i).getName() == "Canary Room")
				deck.remove(i);
			if (deck.get(i).getName() == "Candlestick")
				deck.remove(i);
		}
			
		theAnswer = new Solution("Canary Room", "Miss Scarlet", "Candlestick");
	}
	
	public void dealOnePlayer(){
		//for debugging makeSuggestion.
		for (int i=0; i<deck.size(); i++) {
			if (deck.get(i).getName() == "Mr. Green")
				deck.remove(i);
			if (deck.get(i).getName() == "Hall")
				deck.remove(i);
			if (deck.get(i).getName() == "Wrench")
				deck.remove(i);
		}
	}

	public void fillSeenCards(){
		for(int i=0; i<suggestionChoices.size();++i) {
			if(!suggestionChoices.get(i).getName().equals("Miss Scarlet") && !suggestionChoices.get(i).getName().equals("Candlestick"))
			addSeenCard(suggestionChoices.get(i));
		}
	}	
}