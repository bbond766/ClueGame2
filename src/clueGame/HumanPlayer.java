package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

public class HumanPlayer extends Player {
	private boolean finished = false;

	public HumanPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disprovers=new ArrayList<Card>();
		
		for(Card card : hand)
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon))
				disprovers.add(card);

		if(disprovers.size()==1)
			return disprovers.get(0);
		
		return null;
	}

	@Override
	public void makeMove(Board board, JFrame frame) {
		if (!ClueControlPanelGUI.first)
			ClueControlPanelGUI.toggleFinished();
		board.highlightTargets();
		finished = false;		
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
	
	public void changePosition(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public boolean checkIsValid(BoardCell bc, Board b) {
		if (b.getTargets().contains(bc))
			return true;
		return false;
	}
	
	public void addSeenCard(Card c) {}
	
	public void initializeSeenCards() {}
}
