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

public class ClueControlPanelGUI extends  JFrame{
	private JTextField currentPlayer, diceRoll, guessResult;
	private JComboBox<String> guess;
	
	public ClueControlPanelGUI(){
		setSize(new Dimension(1000,300));
		setTitle("Clue Game Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createCurrentPlayer(), BorderLayout.CENTER);
		add(createDiceRoll(), BorderLayout.WEST);
		add(createButtons(), BorderLayout.NORTH);
		add(createGuessDisplays(), BorderLayout.SOUTH);

	}
	private JPanel createCurrentPlayer() {
		JPanel panel1 = new JPanel();
		JLabel whosTurn = new JLabel("CURRENT PLAYER---------------------->");;
		currentPlayer = new JTextField(25);
		currentPlayer.setEditable(false);
		panel1.setLayout(new GridLayout(1,1));
		panel1.add(whosTurn);
		panel1.add(currentPlayer);
		panel1.setBorder(new TitledBorder(new EtchedBorder(), "Who's turn is it anyway?"));
		return panel1;
	}
	private JPanel createDiceRoll(){
		JPanel panel4 = new JPanel();
		diceRoll = new JTextField();
		diceRoll.setEditable(false);
		JLabel diceRollLabel = new JLabel("Dice Roll");
		panel4.setLayout(new GridLayout(1,1));
		panel4.add(diceRollLabel);
		panel4.add(diceRoll);
		panel4.setBorder(new TitledBorder(new EtchedBorder(), "Your move"));
		return panel4;
	}
	private JPanel createGuessDisplays(){
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,1));
		JLabel guessLabel = new JLabel("Make a guess");
		JLabel guessResultLabel = new JLabel("The result of your guess");
		guessResult =  new JTextField();
		guessResult.setEditable(false);
		guess = setUpComboBoxes();
		panel2.add(guessLabel);
		panel2.add(guess);
		panel2.add(guessResultLabel);
		panel2.add(guessResult);
		panel2.setBorder(new TitledBorder(new EtchedBorder(), "Go for the WIN"));
		return panel2;
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
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1,2));
		panel3.add(nextPlayer);
		panel3.add(makeAccu);
		panel3.setBorder(new TitledBorder(new EtchedBorder(), "Take Action!"));
		return panel3;
	}


	public static void main(String[] args) {
		ClueControlPanelGUI display = new ClueControlPanelGUI();
		display.setVisible(true);
		
	}
}