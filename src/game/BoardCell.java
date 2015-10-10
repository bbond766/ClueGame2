package game;

public class BoardCell {
	private int row, column;
	private char initial;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE}
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway(){
		return false;
	}
	
	public boolean isRoom(){
		return false;
	}
	
	public boolean isDoorway(){
		return false;
	}
}
