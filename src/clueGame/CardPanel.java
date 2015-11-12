package clueGame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel {
	private JTextArea person, room, weapon;
	private List<Card> hand;
	
	public CardPanel(ArrayList<Card> hand) {
		this.hand = hand;
		add(createPeople());
	}
	
	private JPanel createPeople() {
		JPanel p = new JPanel();
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
		person = new JTextArea(3, 20);
		
		for (Card c : hand)
			if (c.getType().equals(CardType.PERSON))
				person.append(c.getName());

		p.add(person);
		return p;
	}
}
