package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SuggestionDialog extends JDialog {
	private Board board;
	private boolean accusation;
	private String room;
	private JComboBox<String> personGuess, weaponGuess, roomGuess;

	public SuggestionDialog(JFrame parent, Board board, String title, boolean accusation, String room) {
		super(parent, title, true);
		this.accusation = accusation;
		this.board = board;
		this.room = room;
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		JPanel suggestionPane = createSuggestions();
		add(suggestionPane);
		JPanel buttonPane = new JPanel();
		JButton submitButton = new JButton ("Submit");
		JButton cancelButton = new JButton("Cancel");
		buttonPane.setLayout(new GridLayout(1, 0));
		buttonPane.add(submitButton);
		buttonPane.add(cancelButton);
		submitButton.addActionListener(new SubmitListener());
		cancelButton.addActionListener(new CancelListener());
		add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	public class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String person = (String) personGuess.getSelectedItem();
			String weapon = (String) weaponGuess.getSelectedItem();
			if (accusation)
				room = (String) roomGuess.getSelectedItem();
			ClueControlPanelGUI.submitInfo(new Solution (room, person, weapon));
			setVisible(false);
			dispose();
		}
	}
	
	public class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		}
	}
	
	public JPanel createSuggestions() {
		JPanel returnFrame = new JPanel();
		returnFrame.setLayout(new GridLayout(1, 0));
		
		JPanel people = createPersonGuess();
		returnFrame.add(people);
		
		JPanel weapons = createWeaponGuess();
		returnFrame.add(weapons);
		
		if (accusation) {
			JPanel rooms = createRoomGuess();
			returnFrame.add(rooms);
		}


		return returnFrame;
	}
	
	public JPanel createPersonGuess() {
		JPanel p = new JPanel();
		personGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		
		for (Card c : board.getChoices()) 
			if (c.getType().equals(CardType.PERSON)) 
				personGuess.addItem(c.getName());
		
		p.add(personGuess);
		return p;
	}
	
	public JPanel createRoomGuess() {
		JPanel p = new JPanel();
		roomGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		
		for (Map.Entry<Character, String> entry : Board.getRooms().entrySet())
			roomGuess.addItem(entry.getValue());
		
		p.add(roomGuess);
		
		return p;
	}
	
	public JPanel createWeaponGuess() {
		JPanel p = new JPanel();
		weaponGuess = new JComboBox<String>();
		p.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));

		for (Card c : board.getChoices()) {
			if (c.getType().equals(CardType.WEAPON))
				weaponGuess.addItem(c.getName());
		}
		
		p.add(weaponGuess);
		
		return p;		
	}
}
