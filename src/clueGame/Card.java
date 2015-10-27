package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.type = type;
	}
	
	public String getName() {
		return cardName;
	}
	
	public CardType getType() {
		return type;
	}
	
	public String toString(){
		return cardName;
	}
}
