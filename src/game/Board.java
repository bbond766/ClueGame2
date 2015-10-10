package game;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int BOARD_SIZE = 21;
	private BoardCell board = new BoardCell();
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private BoardCell[][] grid;
	private String boardConfigFile;
	private String roomConfigfile;
	
	public Board() {
		
	}
	
	public void initialize(){
		
	}
	
	public void loadRoomConfigFile(String roomConfigfile) throws BadConfigFormatException{
		
	}
	
	public void loadBoardConfig(String filename) throws BadConfigFormatException{
		
	}
	
	public void calcAdjacencies(){

	}
	
	//Needs to be update to match UML
	public void calcTargets(BoardCell boardCell, int distance){

	}
	
	public BoardCell getCellAt(int row, int column){
		return null;
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
		return null;
	}
	
	public BoardCell getCell(int row, int column) {
		return null;
	}
}
