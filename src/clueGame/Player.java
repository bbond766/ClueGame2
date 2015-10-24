package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private List<Card> cards = new ArrayList<Card>();
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
}
