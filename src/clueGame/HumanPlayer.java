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
			System.out.println("The cards you have to disprove this suggestion are: ");
			for(Card card : disprovers){
				System.out.print(card.getName()+", ");
			}
			System.out.println();
			while(true){
				System.out.print("Please enter the index of the card to disprove with: ");
				Scanner scan=new Scanner(System.in);
				String response=scan.next();
				int choice;
				try{
					choice=Integer.parseInt(response);
					switch(choice){
						case 0:
							return disprovers.get(0);
						case 1:
							return disprovers.get(1);
						case 2:
							return disprovers.get(2);
						default:
							System.out.println("Please enter a valid index");
							continue;
					}
				}
				catch(NumberFormatException e){
					System.out.println();
					System.out.println("Please enter a valid index");
					continue;
				}
				finally {
					if (scan != null)
						scan.close();
				}
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
	
	public void changePosition(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public boolean checkIsValid(BoardCell bc, Board b) {
		if (b.getTargets().contains(bc))
			return true;
		return false;
	}
}
