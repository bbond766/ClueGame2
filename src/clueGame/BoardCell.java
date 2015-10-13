package clueGame;

public class BoardCell {
	private int row, column;
	private char initial;
	private DoorDirection doorDirection;
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public BoardCell(int row, int column, char initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
	}

	public boolean isWalkway(){
		return false;
	}
	
	public boolean isRoom(){
		return false;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}
	
	public boolean isDoorway(){
		if(doorDirection != null)
			return true;
		return false;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
}
