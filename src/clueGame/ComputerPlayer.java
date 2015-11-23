package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;

public class ComputerPlayer extends Player {
	private String lastVisited;
	private boolean shouldMakeAccusation = false;
	private List<String> seenCards = new ArrayList<String>();
	private Solution bestAccusation = null;
	
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public void initializeSeenCards() {
		for (Card c : hand)
			seenCards.add(c.getName());
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// If the AI's targets list contains a room, the AI will always choose to visit the room
		// unless it was just visited. Otherwise, the AI will choose a target at random.

		for (BoardCell bc : targets)
			// If target is a room that was not the last visited room and has not been seen yet
			if (bc.isRoom() && !Board.getRooms().get(bc.getInitial()).equals(lastVisited) && !seenCards.contains(Board.getRooms().get(bc.getInitial()))) {
				lastVisited = Board.getRooms().get(bc.getInitial());
				return bc;
			}
		
		// If no suitable room found, pick target randomly
		int size = targets.size();
		int item = new Random().nextInt(size);
		int i = 0;
		for (BoardCell bc : targets) {
			if (i == item) {
				if (!bc.isWalkway())
					lastVisited = Board.getRooms().get(bc.getInitial());
				return bc;
			}
			i += 1;
		}
		
		return null;
	}
	
	public Solution makeAccusation(Board board) {
		if (bestAccusation != null)
			return bestAccusation;
		
		if (seenCards.size() == board.getDeck().size())    // if we have seen all but 3 cards
			return board.getAnswer();
		
		return null;
	}

	public Solution makeSuggestion(Board board) {
		// Called when an AI enters a room. The AI makes a suggestion from the current room and
		// the person and weapon cards are chosen at random.
		String person = null;
		String weapon = null;
		
		// Pick a person and weapon card
		while(person == null || weapon == null){
			int choice = new Random().nextInt(board.getChoices().size());
			Card testCard = board.getChoices().get(choice);
			if(testCard.getType()== CardType.PERSON && !seenCards.contains(testCard.getName()) && !hand.contains(testCard)){
				person = testCard.getName();
			}
			if(testCard.getType()== CardType.WEAPON && !seenCards.contains(testCard.getName()) && !hand.contains(testCard)){
				weapon = testCard.getName();
			}
		}
		
		
		// Suggestion must contain the current room
		String room = Board.getRooms().get(board.getCellAt(row, column).getInitial());

		return new Solution(room, person, weapon);
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
		for(Card card : hand)
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon))
				return card;

		return null;
	}
	
	public void setLastVisited(String last) {
		this.lastVisited = last;
	}

	@Override
	public void makeMove(Board board, JFrame frame){
		System.out.println(playerName + " has seen:");
		for (String s: seenCards) {
			System.out.println(s);
		}
		Solution solution;
		if(shouldMakeAccusation){
			System.out.println(playerName + " is ready to make an accusation!");
			solution = makeAccusation(board);
			System.out.println(playerName + " made accusation with " + solution);
			if(board.checkAccusation(solution)) {
				System.out.println("the solution was correct, should display dialog");
				GameEndDialog ged = new GameEndDialog(frame, "Game Over", playerName + " wins!");
				ged.setVisible(true);
			}
			else {
				shouldMakeAccusation = false;
				bestAccusation = null;
			}
		}
		BoardCell newLocation = pickLocation(board.getTargets());
		row = newLocation.getRow();
		column = newLocation.getColumn();
		board.updateBoard();
		board.repaint();
		if(newLocation.isRoom()){
			solution = makeSuggestion(board);
			ClueControlPanelGUI.setLastGuess(solution.toString());
			Card returnedCard = board.handleSuggestion(solution, this);
			if (returnedCard != null){
				for (Player p : board.getPlayers())
					p.addSeenCard(returnedCard);
				ClueControlPanelGUI.setLastResult(returnedCard.getName());
			}
			else{
				ClueControlPanelGUI.setLastResult("Nothing to disprove");
				bestAccusation = solution;
				shouldMakeAccusation = true;
			}
		}
		if (seenCards.size() == board.getDeck().size()) {    // player has seen all but 3 cards
			System.out.println(playerName + " has seen all but 3 cards and is accusing!");
			bestAccusation = makeAccusation(board);
			if (board.checkAccusation(bestAccusation)) {
				System.out.println("the solution was correct, should display dialog");
				GameEndDialog ged = new GameEndDialog(frame, "Game Over", playerName + " wins!");
				ged.setVisible(true);
			}
			else
				System.out.println("THIS SHOULDN'T HAPPEN");
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
	
	@Override
	public void addSeenCard(Card c) {
		if (!seenCards.contains(c.getName()))
			seenCards.add(c.getName());
	}
	
	public List<String> getSeenCards() {
		return seenCards;
	}

}
