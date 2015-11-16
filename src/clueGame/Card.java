package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card() {}
	
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
	
	public Card clone() {
		Card c = new Card();
		c.cardName = this.cardName;
		c.type = this.type;
		return c;
	}
	
	public String toString(){
		return cardName;
	}
}
