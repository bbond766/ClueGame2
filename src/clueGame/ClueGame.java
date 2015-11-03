package clueGame;

import javax.swing.JFrame;



public class ClueGame {

	public ClueGame() {
		
	}


	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Clue Game");
				frame.setSize(1000, 950);	
				// Create the JPanel and add it to the JFrame
	//			GUI_Example gui = new GUI_Example();
		//		frame.add(gui, BorderLayout.CENTER);
				// Now let's view it
				frame.setVisible(true);

	}

}
