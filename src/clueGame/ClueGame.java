package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private static Board gameBoardPanel;
	private DetectiveNotes dialog;
	private CardPanel cardPanel;
	private ClueControlPanelGUI controlPanel;
	private List<Player> humanPlayers;

	public ClueGame() {
		// Displays the board, control panel, and card panel
		// Also creates a File menu with Show Notes and Exit options
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setTitle("Clue Game");
		setSize(1000, 831);	
		gameBoardPanel  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		gameBoardPanel.initialize();
		humanPlayers = new ArrayList<Player>();

		for (Player p : gameBoardPanel.getPlayers())
			if (p.isHuman())
				humanPlayers.add(p);
		
		// For now, humanPlayers will only contain one Player
		cardPanel = new CardPanel(humanPlayers.get(0).getHand());
		controlPanel = new ClueControlPanelGUI(gameBoardPanel);

		GridBagConstraints b = new GridBagConstraints();
		b.weightx = 1.0;
		b.weighty = 1.0;
		b.gridx = 0;
		b.gridy = 0;
		b.fill = GridBagConstraints.BOTH;
		add(gameBoardPanel, b);
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridy = GridBagConstraints.RELATIVE;
		c.gridwidth = GridBagConstraints.REMAINDER;
		//c.fill = GridBagConstraints.VERTICAL;
		add(cardPanel,c);
		
		GridBagConstraints cp = new GridBagConstraints();
		cp.weightx = 0.2;
		cp.weighty = 0.8;
		cp.gridy = 400;
		cp.fill = GridBagConstraints.HORIZONTAL;
		add(controlPanel, cp);

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
		// Displays the JFrame as well as a splash message to the player
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
