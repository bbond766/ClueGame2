package clueGame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Color;

public class Board {
	private int numRows;
	private int numColumns;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] board;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String cardConfigFile;
	private Solution theAnswer;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Board() {
		this("ClueLayout.csv", "ClueLegend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
	}

	public Board(int rows, int columns) {
		numRows = rows;
		numColumns = columns;
		
		board = new BoardCell[numRows][numColumns];
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				board[i][j] = new BoardCell(i, j);
	}
	
	public Board(String layoutFile, String legendFile) {
		boardConfigFile = layoutFile;
		roomConfigFile = legendFile;
		playerConfigFile = "ClueLayout/Players.txt";
		cardConfigFile = "ClueLayout/Cards.txt";

//		initialize();
	} 
	
	public Board(String layoutFile, String legendFile, String playerFile, String cardFile) {
		boardConfigFile = layoutFile;
		roomConfigFile = legendFile;
		playerConfigFile = playerFile;
		cardConfigFile = cardFile;

//		initialize();
	} 
	
	public void initialize() {
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
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
				String[] room = in.nextLine().split(", ");
				if(room.length != 3)
					throw new BadConfigFormatException("Too many fields in room config");

				rooms.put(room[0].charAt(0), room[1]);
				
				if (room[2].equals("Card")) 
					deck.add(new Card(room[1], CardType.ROOM));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException {
		//TODO Fix for dynamic loading of rows and columns
		final int NUM_ROWS = 22;
		final int NUM_COLUMNS = 23;

		board = new BoardCell[NUM_ROWS][NUM_COLUMNS];

		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);

			numRows = 0;
			while(in.hasNextLine()) {
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
						}
					}
				}

				if(numColumns != NUM_COLUMNS) {
					throw new BadConfigFormatException("numColumns: " + numColumns + ", NUM_COLUMNS: " + NUM_COLUMNS);
				}
				numRows++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPlayerConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(playerConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String[] player = in.nextLine().split(", ");
				if (player.length != 5)
					throw new BadConfigFormatException("Too many fields in player config file");
				
				if (!Player.colors.containsKey(player[2]))
					throw new BadConfigFormatException("Color " + player[2] + " is not valid");
				
				if (Integer.parseInt(player[3]) >= numRows || Integer.parseInt(player[3]) < 0)
					throw new BadConfigFormatException("row value is " + player[3] + ", must be between 0 and " + numRows);
				
				if (Integer.parseInt(player[4]) >= numColumns || Integer.parseInt(player[4]) < 0)
					throw new BadConfigFormatException("column value is " + player[4] + ", must be between 0 and " + numColumns);
				
				// Add human player to list
				if (player[1].equals("Human"))
					players.add(new HumanPlayer(player[0], player[2], Integer.parseInt(player[3]), Integer.parseInt(player[4])));
				
				// Add AI player to list
				else if (player[1].equals("Computer"))
					players.add(new ComputerPlayer(player[0], player[2], Integer.parseInt(player[3]), Integer.parseInt(player[4])));
				else
					throw new BadConfigFormatException("Player must be either Human or Computer controlled, field says: " + player[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadCardConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(cardConfigFile);
			Scanner in = new Scanner(reader);
			
			while (in.hasNextLine()) {
				String[] card = in.nextLine().split(", ");
				if (card.length != 2)
					throw new BadConfigFormatException("Too many fields in card config file");
				
				// Add card to deck
				if (card[1].equals("Person"))
					deck.add(new Card(card[0], CardType.PERSON));
				else if (card[1].equals("Weapon"))
					deck.add(new Card(card[0], CardType.WEAPON));
				else
					throw new BadConfigFormatException("Invalid type: " + card[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	
	public void calcAdjacencies(){
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				adjMtx.put(board[i][j], getAdjList(board[i][j]));
	}

	public void calcTargets(int row, int column, int distance) {
		calcTargets(board[row][column], distance);
	}
	
	public void calcTargets(BoardCell boardCell, int distance){
		visited = new HashSet<BoardCell>();
		targets = findAllTargets(boardCell, visited, distance);
	}
	
	private Set<BoardCell> findAllTargets(BoardCell boardCell, Set<BoardCell> oldVisited, int distance) {
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
	
	public BoardCell getCellAt(int row, int column){
		return board[row][column];
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int row, int column) {
		return getAdjList(board[row][column]);
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
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
		} else if(boardCell.getInitial() != 'W') {
		} else {
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
	
	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked) {
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
	
	public void dealCards() {
		int counter = 0;
		while (!deck.isEmpty()) {                          // while the deck has cards
			players.get(counter).giveCard(deck.get(0));    // give the top card in the deck to the current player
			deck.remove(0);                                // remove the top card from the deck
			counter = (counter + 1) % players.size();      // look at the next player
		}
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	public BoardCell getCell(int row, int column) {
		return board[row][column];
	}
	
	public static void main(String[] args) {
		
	}
	
	public int getNumberOfRooms(){
		return rooms.size();
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Solution getAnswer() {
		return theAnswer;
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
}
