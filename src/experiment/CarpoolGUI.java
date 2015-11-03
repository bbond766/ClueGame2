package experiment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CarpoolGUI extends JFrame {
	public CarpoolGUI() {
		setSize(new Dimension(400, 250));
		setTitle("Let's carpool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DisplayPanel dp = new DisplayPanel();
		add(dp, BorderLayout.SOUTH);
		
		ToFromPanel tfPanel = new ToFromPanel();
		add(tfPanel, BorderLayout.CENTER);
		PreferencePanel pPanel = new PreferencePanel(dp);
		add(pPanel, BorderLayout.EAST);
		WillDrivePanel wdPanel = new WillDrivePanel();
		add(wdPanel, BorderLayout.NORTH);
		JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		
		return item;
	}
	
	public static void main(String[] args) {
		CarpoolGUI gui = new CarpoolGUI();
		gui.setVisible(true);
	}
}
