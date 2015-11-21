package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	public static final int SIZE_X = 850;
	public static final int SIZE_Y = 775;
	private static Board gameBoardPanel;
	private DetectiveNotes dialog;
	private CardPanel cardPanel;
	private ClueControlPanelGUI controlPanel;
	private List<Player> humanPlayers;

	public ClueGame() {
		// Displays the board, control panel, and card panel
		// Also creates a File menu with Show Notes and Exit options
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("Clue Game");
		setSize(SIZE_X, SIZE_Y);	
		
		gameBoardPanel  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		gameBoardPanel.initialize();
		
		humanPlayers = new ArrayList<Player>();
		for (Player p : gameBoardPanel.getPlayers())
			if (p.isHuman())
				humanPlayers.add(p);
		
		// For now, humanPlayers will only contain one Player
		cardPanel = new CardPanel(humanPlayers.get(0).getHand());
		controlPanel = new ClueControlPanelGUI(gameBoardPanel);
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(gameBoardPanel, BorderLayout.CENTER);
		top.add(cardPanel, BorderLayout.EAST);
		add(top, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
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
	
	class UpdateDisplay extends TimerTask {
		public void run() {
			repaint();
		}
	}

	public static void main(String[] args) {
		// Displays the JFrame as well as a splash message to the player
		ClueGame cg = new ClueGame();
		cg.setVisible(true);
		String name = "";
		Timer timer = new Timer();
		
		for (Player p : gameBoardPanel.getPlayers())
			if (p.isHuman()) {
				name = p.getName();
				break;
			}
		
		JOptionPane.showMessageDialog(cg, "You are " + name + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		timer.schedule(cg.new UpdateDisplay(),  0, 5);
	}
}
