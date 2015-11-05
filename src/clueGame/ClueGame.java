package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class ClueGame extends JFrame {
	
	private Board gameBoardPanel;

	public ClueGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(1000, 950);	
		gameBoardPanel  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		add(gameBoardPanel, BorderLayout.CENTER);
	}


	public static void main(String[] args) {
		
		Board  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		
		
		

	}

}
