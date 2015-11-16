package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueControlPanelGUI extends  JPanel{
	private JTextField currentPlayer, diceRoll, guessResult;
	private JComboBox<String> person, weapon;
	private Board board;
	
	public ClueControlPanelGUI(Board board){
		this.board = board;
		add(createCurrentPlayer(), BorderLayout.CENTER);
		add(createDiceRoll(), BorderLayout.WEST);
		add(createButtons(), BorderLayout.NORTH);
		add(createGuessDisplays(), BorderLayout.SOUTH);

	}
	private JPanel createCurrentPlayer() {
		JPanel currentPlayerPanel = new JPanel();
		JLabel whosTurn = new JLabel("CURRENT PLAYER:");
		currentPlayer = new JTextField(16);
		currentPlayer.setEditable(false);
		currentPlayerPanel.setLayout(new GridLayout(1,1));
		currentPlayerPanel.add(whosTurn);
		currentPlayerPanel.add(currentPlayer);
		currentPlayerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Who's turn is it anyway?"));
		currentPlayerPanel.setSize(new Dimension(100, 10));
		return currentPlayerPanel;
	}
	private JPanel createDiceRoll(){
		JPanel diceRollPanel = new JPanel();
		diceRoll = new JTextField();
		diceRoll.setEditable(false);
		JLabel diceRollLabel = new JLabel("Dice Roll");
		diceRollPanel.setLayout(new GridLayout(1,1));
		diceRollPanel.add(diceRollLabel);
		diceRollPanel.add(diceRoll);
		diceRollPanel.setSize(new Dimension(100, 10));
		diceRollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Your move"));
		return diceRollPanel;
	}
	private JPanel createGuessDisplays(){
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,1));
		JLabel guessLabel = new JLabel("Make a guess");
		JLabel guessResultLabel = new JLabel("The result of your guess");
		guessResult =  new JTextField();
		guessResult.setEditable(false);
		person = setUpPersonBox();
		weapon = setUpWeaponBox();
		guessPanel.add(guessLabel);
		guessPanel.add(person);
		guessPanel.add(weapon);
		guessPanel.add(guessResultLabel);
		guessPanel.add(guessResult);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Go for the WIN"));
		return guessPanel;
	}
	private JComboBox<String> setUpPersonBox(){
		JComboBox<String> jcb = new JComboBox<String>();
		for (Card c : board.getChoices())
			if (c.getType().equals(CardType.PERSON))
				jcb.addItem(c.getName());
		return jcb;
	}
	
	private JComboBox<String> setUpWeaponBox() {
		JComboBox<String> jcb = new JComboBox<String>();
		for (Card c : board.getChoices())
			if (c.getType().equals(CardType.WEAPON))
				jcb.addItem(c.getName());
		return jcb;
	}
	private JPanel createButtons(){
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccu = new JButton("Make an Accusation");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(nextPlayer);
		buttonPanel.add(makeAccu);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Take Action!"));
		return buttonPanel;
	}


	/*public static void main(String[] args) {
		ClueControlPanelGUI display = new ClueControlPanelGUI();
		display.setVisible(true);
		
	}*/
}