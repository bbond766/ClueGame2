package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import experiment.MyDialog;



public class ClueGame extends JFrame {
	private Board gameBoardPanel;
	private DetectiveNotes dialog;

	public ClueGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(821, 831);	
		gameBoardPanel  = new Board("ClueLayout/Layout.csv", "ClueLayout/Legend.txt", "ClueLayout/Players.txt", "ClueLayout/Cards.txt");
		gameBoardPanel.initialize();
		add(gameBoardPanel, BorderLayout.CENTER);
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
	
	public static void main(String[] args) {
		ClueGame cg = new ClueGame();
		cg.setVisible(true);
		
		

	}

}
