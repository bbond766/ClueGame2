package clueGame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
	private int numRows;
	private int numColumns;
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] board;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String cardConfigFile;
	private Solution theAnswer;
	private List<Player> players = new ArrayList<Player>();
	
	public Board() {
		this("ClueLayout.csv", "ClueLegend.txt");
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

//		initialize();
	} 
	
	public void initialize() {
		rooms = new HashMap<Character, String>();
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {

		}

		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {

		}

		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		calcAdjacencies();
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);

			while(in.hasNextLine()) {
				String[] room = in.nextLine().split(",");
				if(room.length != 3)
					throw new BadConfigFormatException();

				rooms.put(room[0].trim().charAt(0), room[1].trim());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
						throw new BadConfigFormatException();

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
					throw new BadConfigFormatException();
				}
				numRows++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadPlayerConfig() {}
	
	public void loadCardConfig() {}
		
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
	
	public void selectAnswer() {}
	
	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked) {
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		return (Boolean) null;
	}
	
	public void dealCards() {}
	
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

}
