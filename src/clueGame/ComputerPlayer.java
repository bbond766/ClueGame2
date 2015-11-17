package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private String lastVisited;
	
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// If the AI's targets list contains a room, the AI will always choose to visit the room
		// unless it was just visited. Otherwise, the AI will choose a target at random.

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
		
		return null;
	}
	
	public void makeAccusation() {
		// TODO: this should have been done already??
	}

	public Solution makeSuggestion(Board board) {
		// Called when an AI enters a room. The AI makes a suggestion from the current room and
		// the person and weapon cards are chosen at random.
		String name = null;
		String weapon = null;
		
		// Pick a person and weapon card
		while(name == null || weapon == null){
			int choice = new Random().nextInt(board.getChoices().size());
			Card testCard = board.getChoices().get(choice);
			if(testCard.getType()== CardType.PERSON && !board.getSeenCards().contains(testCard) && !hand.contains(testCard)){
				name = testCard.getName();
			}
			if(testCard.getType()== CardType.WEAPON && !board.getSeenCards().contains(testCard) && !hand.contains(testCard)){
				weapon = testCard.getName();
			}
		}
		
		// Suggestion must contain the current room
		String room = Board.getRooms().get(board.getCellAt(row, column).getInitial());

		return new Solution(room, name, weapon);
	}

	@Override
	public boolean isHuman() {
		return false;
	}
	
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		/* If the AI has a card to disprove the suggestion, return it.
		 * This method always returns the same card each time it is called with the same suggestion
		 * to prevent players from making the same suggestion repeatedly and getting new information
		 * each time.
         */

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

	@Override
	public void makeMove(Set<BoardCell> targets, Board board){
		BoardCell newLocation = pickLocation(targets);
		row = newLocation.getRow();
		column = newLocation.getColumn();
	}

}
