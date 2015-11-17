package clueGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
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
		setLayout(new GridLayout(3, 1));
		add(createPeople());
		add(createRooms());
		add(createWeapons());
		Dimension size = getPreferredSize();
		size.height = 250;
		setPreferredSize(size);
	}
	
	private JPanel createPeople() {
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		person = new JTextArea(3, 20);
		
		for (Card c : hand)
			if (c.getType().equals(CardType.PERSON)) {
				person.append(c.getName() + "\n");
			}

		p.add(person);
		return p;
	}
	
	private JPanel createRooms() {
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		room = new JTextArea(3, 20);
		
		for (Card c : hand)
			if (c.getType().equals(CardType.ROOM))
				room.append(c.getName() + "\n");
		
		p.add(room);
		return p;
	}
	
	private JPanel createWeapons() {
		JPanel p = new JPanel();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weapon = new JTextArea(3, 20);
		
		for (Card c : hand)
			if (c.getType().equals(CardType.WEAPON))
				weapon.append(c.getName() + "\n");
		
		p.add(weapon);
		return p;
	}
}
