package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
	private int numRows;
	private int numColumns;
	private BoardCell board = new BoardCell();
	private static Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] grid;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board() {
		this("ClueLayout.csv", "ClueLegend.txt");
	}

	public Board(int rows, int columns) {
		numRows = rows;
		numColumns = columns;
		
		grid = new BoardCell[numRows][numColumns];
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				grid[i][j] = new BoardCell(i, j);
	}
	
	public Board(String layoutFile, String legendFile) {
		boardConfigFile = layoutFile;
		roomConfigFile = legendFile;

//		initialize();
	} 
	
	public void initialize(){
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
			Scanner in = new Scanner(reader).useDelimiter(",");

			while(in.hasNextLine()) {
				Character c = in.next().charAt(0);
				String s = in.next();
				rooms.put(c, s);
				in.nextLine();
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

		grid = new BoardCell[NUM_ROWS][NUM_COLUMNS];

		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);

			numRows = 0;
			while(in.hasNextLine()) {
				Scanner line = new Scanner(in.nextLine()).useDelimiter(",");
				
				numColumns = 0;
				while(line.hasNext()) {
					String s = line.next();
					grid[numRows][numColumns] = new BoardCell(numRows, numColumns, s.charAt(0));
					if(s.length() > 1) {
						switch(s.charAt(1)) {
						case 'U':
							grid[numRows][numColumns].setDoorDirection(DoorDirection.UP);
							break;
						case 'D':
							grid[numRows][numColumns].setDoorDirection(DoorDirection.DOWN);
							break;
						case 'L':
							grid[numRows][numColumns].setDoorDirection(DoorDirection.LEFT);
							break;
						case 'R':
							grid[numRows][numColumns].setDoorDirection(DoorDirection.RIGHT);
							break;
						}
					}
					numColumns++;
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
	
	public void selectAnswer() {
		
	}
	
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	
	public void calcAdjacencies(){
		for(int i=0; i < numRows; i++)
			for(int j=0; j < numColumns; j++)
				adjMtx.put(grid[i][j], getAdjList(grid[i][j]));
	}

	public void calcTargets(int row, int column, int distance) {
		calcTargets(new BoardCell(row, column), distance);
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
		
		for(BoardCell target : adjacent)
			targets.addAll(findAllTargets(target, visited, distance-1));
		
		return targets;
	}
	
	public BoardCell getCellAt(int row, int column){
		return grid[row][column];
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(int row, int column) {
		return getAdjList(new BoardCell(row, column));
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
		LinkedList<BoardCell> list = new LinkedList<BoardCell>();
		
		if(boardCell.isDoorway()) {
			switch(boardCell.getDoorDirection()) {
			case UP:
				list.add(grid[boardCell.getRow() - 1][boardCell.getColumn()]);
				break;
			case DOWN:
				list.add(grid[boardCell.getRow() + 1][boardCell.getColumn()]);
				break;
			case LEFT:
				list.add(grid[boardCell.getRow()][boardCell.getColumn() - 1]);
				break;
			case RIGHT:
				list.add(grid[boardCell.getRow()][boardCell.getColumn() + 1]);
				break;
			}
		} else {
			if(boardCell.getRow() - 1 >= 0) {
				BoardCell cell = grid[boardCell.getRow() - 1][boardCell.getColumn()];
				if(!cell.isDoorway() || cell.getDoorDirection() == DoorDirection.DOWN)
					list.add(cell);
			}
			if(boardCell.getRow() + 1 < numRows) {
				BoardCell cell = grid[boardCell.getRow() + 1][boardCell.getColumn()];
				if(!cell.isDoorway() || cell.getDoorDirection() == DoorDirection.UP)
					list.add(cell);
			}
			if(boardCell.getColumn() - 1 >= 0) {
				BoardCell cell = grid[boardCell.getRow()][boardCell.getColumn() - 1];
				if(!cell.isDoorway() || cell.getDoorDirection() == DoorDirection.RIGHT)
					list.add(cell);
			}
			if(boardCell.getColumn() + 1 < numColumns) {
				BoardCell cell = grid[boardCell.getRow()][boardCell.getColumn() + 1];
				if(!cell.isDoorway() || cell.getDoorDirection() == DoorDirection.LEFT)
					list.add(cell);
			}
		}
		
		return list;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
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
