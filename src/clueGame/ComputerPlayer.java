package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private String lastVisited;
	
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		/* If the list of targets includes a room, select that
		 * location unless the player was just in that room.
		 * If the list does not include a room, or the room was
		 * just visited, randomly choose from ALL locations.
		 * 
		 * 
		 * Avoid going into a room if the card has been seen.
		 * Always pick a room if you can get to it UNLESS that
		 * room was just visited.
		 * Otherwise pick randomly from targets.
		 * Call makeSuggestion?
		 */

		for (BoardCell bc : targets)
			// If target is a room that was not the last visited room
			if (bc.isRoom() && !Board.getRooms().get(bc.getInitial()).equals(lastVisited))
				return bc;
		
		// If no suitable room found, pick target randomly
		int size = targets.size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (BoardCell bc : targets) {
			if (i == item)
				return bc;
			i += 1;
		}
		
		return null;    // SOMETHING WENT WRONG
	}
	
	public void makeAccusation() {}
	
	public void makeSuggestion(Board board, BoardCell location) {}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		for(Card card : hand){
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon)){
				return card;
			}
		}
		return null;
	}
	
	public void setLastVisited(String last) {
		this.lastVisited = last;
	}
}
