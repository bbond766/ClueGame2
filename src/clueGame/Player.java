package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	protected String playerName;
	protected int row;
	protected int column;
	protected Color color;
	protected ArrayList<Card> hand;
	
	public Player(String name, Color color, int row, int col) {
		this.playerName = name;
		this.color = color;
		this.row = row;
		this.column = col;
		this.hand = new ArrayList<Card>();
	}
	
	abstract public Card disproveSuggestion(Solution suggestion);
	
	public void giveCard(Card c) {
		hand.add(c);
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Color getColor() {
		return color;
	}
	
	abstract public void makeMove(Set<BoardCell> targets);
	
	abstract public boolean isHuman();

	abstract public void makeMove(Set<BoardCell> targets, Board board);
}
