package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JFrame{
	private Board board;
	
	public DetectiveNotes(Board board) {
		this.board = board;
		setSize(new Dimension(500, 650));
		setTitle("Detective Notes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		add(createPeople(), BorderLayout.CENTER);
		add(createPersonGuess(), BorderLayout.CENTER);
		add(createRooms(), BorderLayout.CENTER);
		add(createRoomGuess(), BorderLayout.CENTER);
		add(createWeapons(), BorderLayout.CENTER);
		add(createWeaponGuess(), BorderLayout.CENTER);
	}
	
	public JPanel createPeople() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 2));
		p.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		for (Card c : board.getChoices()) 
			if (c.getType().equals(CardType.PERSON)) 
				p.add(new JCheckBox(c.getName()), BorderLayout.CENTER);

		return p;
	}
	
	public JPanel createPersonGuess() {
		JPanel p = new JPanel();
		JComboBox<String> personGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		personGuess.addItem("Unsure");
		
		for (Card c : board.getChoices()) 
			if (c.getType().equals(CardType.PERSON)) 
				personGuess.addItem(c.getName());
		
		p.add(personGuess);
		return p;
	}
	
	public JPanel createRooms() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(5, 2));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		for (Map.Entry<Character, String> entry : board.getRooms().entrySet())
			p.add(new JCheckBox(entry.getValue()), BorderLayout.CENTER);
		
		return p;
	}
	
	public JPanel createRoomGuess() {
		JPanel p = new JPanel();
		JComboBox<String> roomGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		roomGuess.addItem("Unsure");
		
		for (Map.Entry<Character, String> entry : board.getRooms().entrySet())
			roomGuess.addItem(entry.getValue());
		
		p.add(roomGuess);
		
		return p;
	}
	
	public JPanel createWeapons() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 2));
		p.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		
		for (Card c : board.getChoices()) 
			if (c.getType().equals(CardType.WEAPON)) 
				p.add(new JCheckBox(c.getName()), BorderLayout.CENTER);
		
		return p;
	}
	
	public JPanel createWeaponGuess() {
		JPanel p = new JPanel();
		JComboBox<String> weaponGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		weaponGuess.addItem("Unsure");
		
		for (Card c : board.getChoices()) {
			if (c.getType().equals(CardType.WEAPON))
				weaponGuess.addItem(c.getName());
		}
		
		p.add(weaponGuess);
		
		return p;		
	}
	
	public static void main(String[] args) {
		Board b = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		b.initialize();
		DetectiveNotes dn = new DetectiveNotes(b);
		dn.setVisible(true);
	}
}