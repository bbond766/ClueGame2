package clueGame;

import javax.swing.JFrame;

public class ClueGame {

	public ClueGame() {
		
	}


	public static void main(String[] args) {
		//Create the JFrame for the control panel
//		ClueGame game = new ClueGame();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game Control Panel");
		frame.setSize(900,300);
		//create the JPanel and add it to the JFrame
//		ClueControlPanelGUI cPanel = new ClueControlPanelGUI();
//		frame.add(cPanel, BorderLayout.CENTER);
		//Now make the JFrame visible
		frame.setVisible(true);
		
		

	}

}
