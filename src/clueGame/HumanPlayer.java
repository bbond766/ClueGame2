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

	public HumanPlayer() {
		super();
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		// TODO: this method needs to be more player-friendly
		// The player might not understand "enter the index of the card"
		ArrayList<Card> disprovers=new ArrayList<Card>();
		for(Card card : hand){
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon)){
				disprovers.add(card);
			}
		}
		if(disprovers.size()==1){
			return disprovers.get(0);
		}
		else if(disprovers.size()>0){
			for(Card card : disprovers){
				
			}
		}
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
	
	public boolean checkIsValid(BoardCell bc, Board b) {
		if (b.getTargets().contains(bc))
			return true;
		return false;
	}
}
