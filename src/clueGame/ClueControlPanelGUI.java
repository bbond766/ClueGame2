package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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
	private JComboBox<String> person, weapon;
	private Board board;
	private boolean humanFinished = false;
	private ArrayList<Player> players;
	private Player current;
	private int currentPlayerIndex = 0;
	private int roll = 0;
	
	public ClueControlPanelGUI(Board board){
		this.board = board;
		this.players = board.getPlayers();
		current = players.get(currentPlayerIndex);
		setLayout(new BorderLayout());
		add(createCurrentPlayer(), BorderLayout.CENTER);
		add(createDiceRoll(), BorderLayout.WEST);
		add(createButtons(), BorderLayout.NORTH);
		add(createGuessDisplays(), BorderLayout.SOUTH);
		System.out.println("current player: " + current);

	}
	private JPanel createCurrentPlayer() {
		JPanel currentPlayerPanel = new JPanel();
		JLabel whosTurn = new JLabel("CURRENT PLAYER:");
		currentPlayer = new JTextField(16);
		currentPlayer.setEditable(false);
		currentPlayerPanel.setLayout(new GridLayout(1,1));
		currentPlayerPanel.add(whosTurn);
		currentPlayerPanel.add(currentPlayer);
		currentPlayerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Whose turn is it anyway?"));
		currentPlayerPanel.setSize(new Dimension(100, 10));
		return currentPlayerPanel;
	}
	private JPanel createDiceRoll(){
		JPanel diceRollPanel = new JPanel();
		diceRoll = new JTextField(roll);
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
		nextPlayer.addActionListener(new ButtonListener());
		//makeAccu.addActionListener(new ButtonListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(nextPlayer);
		buttonPanel.add(makeAccu);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Take Action!"));
		return buttonPanel;
	}

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			move();
			if(humanFinished){
				current = players.get(currentPlayerIndex+1);
				currentPlayer.setText(current.getName());
			}
		}
		
	}
	
	public void move(){
		System.out.println("current player: " + current);
		rollDie();
		System.out.println("die roll: " + roll);
		String die = roll + "";
		diceRoll.setText(die);
		board.calcTargets(current.getRow(), current.getColumn(), roll);
		current.makeMove(board);
		humanFinished = true;
		repaint();
	}
	
	public void rollDie(){
		Random r = new Random();
		roll= r.nextInt(6)+1;
	}

//	public static void main(String[] args) {
//		Board board = new Board();
//		board.initialize();
//		ClueControlPanelGUI display = new ClueControlPanelGUI(board);
//		JFrame frame = new JFrame();
//		frame.add(display);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(700, 300);
//		frame.setVisible(true);
//		
//	}
}