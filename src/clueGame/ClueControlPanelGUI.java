package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueControlPanelGUI extends  JPanel{
	private JTextField currentPlayer, diceRoll, guessResult;
	private JComboBox<String> guess;
	
	public ClueControlPanelGUI(){
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
		guess = setUpComboBoxes();
		guessPanel.add(guessLabel);
		guessPanel.add(guess);
		guessPanel.add(guessResultLabel);
		guessPanel.add(guessResult);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Go for the WIN"));
		return guessPanel;
	}
	private JComboBox<String> setUpComboBoxes(){
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Miss Scarlet");
		combo.addItem("Mrs. Peacock");
		combo.addItem("Professor Plum");
		combo.addItem("Colonel Mustard");
		combo.addItem("Mrs. White");
		combo.addItem("Mr. Green");
		combo.addItem("Candlestick");
		combo.addItem("Wrench");
		combo.addItem("Rope");
		combo.addItem("Revolver");
		combo.addItem("Knife");
		combo.addItem("Lead Pipe");
		return combo;
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


	public static void main(String[] args) {
		ClueControlPanelGUI display = new ClueControlPanelGUI();
		display.setVisible(true);
		
	}
}