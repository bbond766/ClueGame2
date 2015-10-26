package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}
	public void pickLocation(Set<BoardCell> targets) {}
	public void makeAccusation() {}
	public void makeSuggestion(Board board, BoardCell location) {}
}
