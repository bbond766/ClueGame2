package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}
	public void pickLocation(Set<BoardCell> targets) {
		/* If the list of targets includes a room, select that
		 * location unless the player was just in that room.
		 * If the list does not include a room, or the room was
		 * just visited, randomly choose from ALL locations.
		 */
		
	}
	
	public void makeAccusation() {}
	
	public void makeSuggestion(Board board, BoardCell location) {}
	
	@Override
	public boolean isHuman() {
		return false;
	}
}
