package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueControlPanelGUI extends JPanel {
	private JTextField currentPlayer, diceRoll;
	private static JTextField guessResult, lastGuess;
	private Board board;
	private static boolean humanFinished = false;
	private ArrayList<Player> players;
	private Player current;
	private int currentPlayerIndex = 0;
	private int roll = 0;
	private JFrame frame;
	private SuggestionDialog suggestionDialog;
	public static boolean first = true;  // Flag indicates this is the first move
	private static boolean suggested;
	
	public ClueControlPanelGUI(Board board, JFrame frame){
		this.board = board;
		this.players = board.getPlayers();
		this.frame = frame;
		current = players.get(currentPlayerIndex);
		setLayout(new BorderLayout());
		add(createCurrentPlayer(), BorderLayout.CENTER);
		add(createDiceRoll(), BorderLayout.WEST);
		add(createButtons(), BorderLayout.NORTH);
		add(createGuessDisplays(), BorderLayout.SOUTH);
		currentPlayer.setText(current.getName());
	}
	private JPanel createCurrentPlayer() {
		JPanel currentPlayerPanel = new JPanel();
		JLabel whosTurn = new JLabel("Current Player:");
		currentPlayer = new JTextField(16);
		currentPlayer.setEditable(false);
		currentPlayerPanel.setLayout(new GridLayout(1,1));
		currentPlayerPanel.add(whosTurn);
		currentPlayerPanel.add(currentPlayer);
		currentPlayerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Whose turn is it anyway?"));
		return currentPlayerPanel;
	}
	private JPanel createDiceRoll(){
		JPanel diceRollPanel = new JPanel();
		diceRoll = new JTextField(3);
		diceRoll.setEditable(false);
		JLabel diceRollLabel = new JLabel("Dice Roll");
		diceRollPanel.setLayout(new FlowLayout());
		diceRollPanel.add(diceRollLabel);
		diceRollPanel.add(diceRoll);
		diceRollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Your move"));
		return diceRollPanel;
	}
	private JPanel createGuessDisplays(){
		JPanel guessPanel = new JPanel();
		JPanel lastPanel = new JPanel();
		lastPanel.setLayout(new GridLayout(2, 1));
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(2, 1));
		guessPanel.setLayout(new BorderLayout());
		JLabel guessLabel = new JLabel("Last Suggestion:");
		JLabel guessResultLabel = new JLabel("Last Disproving Card:");
		guessResult =  new JTextField(19);
		guessResult.setEditable(false);
		lastGuess = new JTextField(45);
		lastGuess.setEditable(false);
		lastPanel.add(guessLabel);
		lastPanel.add(lastGuess);
		resultPanel.add(guessResultLabel);
		resultPanel.add(guessResult);
		guessPanel.add(lastPanel, BorderLayout.WEST);
		guessPanel.add(resultPanel, BorderLayout.EAST);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Suggestion Results"));
		return guessPanel;
	}

	private JPanel createButtons() {
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccu = new JButton("Make an Accusation");
		nextPlayer.addActionListener(new ButtonListener());
		makeAccu.addActionListener(new AccuListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(nextPlayer);
		buttonPanel.add(makeAccu);
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Take Action!"));
		return buttonPanel;
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (first) {
				move();
				first = false;
			}
			else if (humanFinished){
				suggested = false;
				current = players.get(++currentPlayerIndex%(players.size()));
				currentPlayer.setText(current.getName());
				move();
			}
		}
	}
	
	private class AccuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (current.isHuman() && !suggested) {
				suggestionDialog = new SuggestionDialog(frame, board, "Make Your Accusation!", true, current.getRoom(board));
				suggestionDialog.setVisible(true);
				if (suggested) {
					lastGuess.setText(Board.lastGuessSolution.person + " with the " + Board.lastGuessSolution.weapon + " in the " + Board.lastGuessSolution.room);
					if(board.checkAccusation(Board.lastGuessSolution)) {
						GameEndDialog ged = new GameEndDialog(frame, "Winner!", current.getName() + " wins!");
						ged.setVisible(true);
					}
					else {
						guessResult.setText("incorrect");
					}
				}
			}
		}
	}
	
	public void move() {
		rollDie();
		String die = roll + "";
		diceRoll.setText(die);
		board.calcTargets(current.getRow(), current.getColumn(), roll);
		current.makeMove(board, frame);
		repaint();
	}
	
	public static void toggleFinished() {
		humanFinished = !humanFinished;
	}
	
	public void rollDie(){
		Random r = new Random();
		roll= r.nextInt(6)+1;
	}
	
	public static void submitInfo(Solution last) {
		suggested = true;
		Board.lastGuessSolution = last;
		Board.suggested = true;
	}
	
	public static void setLastGuess(String last) {
		lastGuess.setText(last);
	}
	
	public static void setLastResult(String result) {
		guessResult.setText(result);
	}
}