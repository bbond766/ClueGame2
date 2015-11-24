package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BoardCell {
	private int row, column;
	private char initial;
	private DoorDirection doorDirection;
	public List<Player> players = new ArrayList<Player>();
	private String name;
	private boolean highlighted;
	
	public BoardCell(int row, int column) {
		// Constructor that doesn't take an initial used only for testing
		this.row = row;
		this.column = column;
		this.highlighted = false;
	}

	public BoardCell(int row, int column, char initial) {
		// Parameterized constructor
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.name = "";
		this.players = new ArrayList<Player>();
		this.highlighted = false;
	}
	
	public void updateCell(ArrayList<Player> boardPlayers) {
		// Checks if any players are on the cell
		for (Player p : boardPlayers) {
			if (p.getRow() == row && p.getColumn() == column) {
				if (!players.contains(p))
					players.add(p);
			}
			else
				while (players.contains(p))
					players.remove(p);
		}
	}
	
	public void setName(String name) {
		this.name = name;
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
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}
	
	public String toString() {
		return "(" + row + ", " + column + ")";
	}
	
	public void draw(Graphics g) {
		// Draws the cell based on the type of cell and whether or not
		// any players are currently on the cell
		int size = 25;
		int x = size * column;
		int y = size * row;
		
		if (isWalkway()) {
			if (highlighted)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.LIGHT_GRAY);
			
			g.fillRect(x, y, size, size);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, size, size);
		}
		else {
			if (highlighted)
				g.setColor(Color.MAGENTA);
			else
				g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, size, size);
			if (isDoorway()) {
				if (highlighted)
					g.setColor(Color.GREEN);
				else
					g.setColor(Color.BLUE);
				
				switch (doorDirection) {
				case UP: 
					g.fillRect(x, y, size, 5);
					break;
				case RIGHT:
					g.fillRect(x+size-5, y, 5, size);
					break;
				case DOWN:
					g.fillRect(x, y+size-5, size, 5);
					break;
				case LEFT:
					g.fillRect(x, y, 5, size);
					break;
				default:
				}
			}
		}
		if (!players.isEmpty()) {
			if (players.size() == 2) {
				g.setColor(players.get(0).getColor());
				g.fillOval(x, y, size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x, y, size/2, size/2);
				g.setColor(players.get(1).getColor());
				g.fillOval(x+size/2, y+size/2, size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x+size/2, y+size/2, size/2, size/2);
			}
			else if (players.size() >= 3) {
				// Also draws the first 3 circles of the 4 player case
				g.setColor(players.get(0).getColor());
				g.fillOval(x, y, size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x, y, size/2, size/2);
				
				g.setColor(players.get(1).getColor());
				g.fillOval(x+size-(size/2), y, size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x+size-(size/2), y, size/2, size/2);
				
				g.setColor(players.get(2).getColor());
				g.fillOval(x, y+size/2, size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x, y+size/2, size/2, size/2);
			}
			else
				for (Player p : players) {
					g.setColor(p.getColor());
					g.fillOval(x, y, size, size);
					g.setColor(Color.BLACK);
					g.drawOval(x, y, size, size);
				}
			if (players.size() == 4) {
				// 4 players looks just like 3 but with one extra circle
				g.setColor(players.get(3).getColor());
				g.fillOval(x+size-(size/2), y+size-(size/2), size/2, size/2);
				g.setColor(Color.BLACK);
				g.drawOval(x+size-(size/2), y+size-(size/2), size/2, size/2);
			}
		}
		if (!name.equals("")) {
			g.setColor(Color.WHITE);
			g.drawString(name, x, y);
		}
	}
	
	public void toggleHighlight() {
		highlighted = !highlighted;
	}
}
