package clueGame;

import java.awt.Color;
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
		 * Avoid going into a room if the card has been seen
		 */
		return null;
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
