package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public Card disproveSuggestion(Solution suggestion) {
		/*ArrayList<Card> disprovers=new ArrayList<Card>(3);
		for(Card card : hand){
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon)){
				disprovers.add(card);
			}
		}
		if(disprovers.size()==1){
			return disprovers.get(0);
		}
		int choice=-1;
		while(choice>=0&&choice<disprovers.size()){
			System.out.println("Your cards that disprove this suggestion are: "+disprovers);
			System.out.print("Please enter the index of the card you'd like to show: ");
			Scanner sc=new Scanner(System.in);
			try{
				choice=Integer.parseInt(sc.next());
			}
			catch(NumberFormatException e){
				System.out.println("Please enter a valid index. (Remember, start with 0)");
				continue;
			}
			if(choice>=disprovers.size()||choice<0){
				System.out.println("Please enter a valid index. (Remember, start with 0)");
				continue;
			}
			sc.close();
			return disprovers.get(choice);
		}*/
		for(Card card : hand){
			if(card.getName().equals(suggestion.person)||card.getName().equals(suggestion.room)||card.getName().equals(suggestion.weapon)){
				return card;
			}
		}
		return null;
	}

}
