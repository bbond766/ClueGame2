package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BoardCell {
	private int row, column;
	private char initial;
	private DoorDirection doorDirection;
	private ArrayList<Player> players;
	
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
	
	public void updateCell() {
		for (Player p : Board.getPlayers()) {
			if (p.getRow() == row && p.getColumn() == column)
				players.add(p);
			else
				if (players.contains(p))
					players.remove(p);
		}
	}

	public boolean isWalkway(){
		if(initial == 'W')
			return true;
		else
			return false;
	}
	
	public boolean isRoom(){
		if(initial != 'W')
			return true;
		else
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
	
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(20,20,20,20);
	}
}
