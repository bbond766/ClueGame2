package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import experiment.MyDialog;



public class ClueGame extends JFrame {
	private static Board gameBoardPanel;
	private DetectiveNotes dialog;
	private CardPanel cardPanel;
	private List<Player> humanPlayers = new ArrayList<Player>();

	public ClueGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(821, 831);	
		gameBoardPanel  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		gameBoardPanel.initialize();

		for (Player p : gameBoardPanel.getPlayers())
			if (p.isHuman())
				humanPlayers.add(p);
		
		// For now, humanPlayers will only contain one Player
		cardPanel = new CardPanel(humanPlayers.get(0).getHand());
		add(gameBoardPanel, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());	
		ClueControlPanelGUI display = new ClueControlPanelGUI();
		add(display, BorderLayout.SOUTH);
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class NotesListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dialog = new DetectiveNotes(gameBoardPanel);
				dialog.setVisible(true);
			}
		}
		item.addActionListener(new NotesListener());
		return item;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new ExitListener());
		return item;
	}
	

	public static void main(String[] args) {
		ClueGame cg = new ClueGame();
		cg.setVisible(true);
		String name = "";
		
		for (Player p : gameBoardPanel.getPlayers())
			if (p.isHuman()) {
				name = p.getName();
				break;
			}
		
		JOptionPane.showMessageDialog(cg, "You are " + name + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		

	}

}
