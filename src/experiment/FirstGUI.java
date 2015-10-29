package experiment;

import javax.swing.JFrame;

public class FirstGUI extends JFrame {
	public FirstGUI() {
		setTitle("My First GUI");
		setSize(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		FirstGUI gui = new FirstGUI();
		gui.setVisible(true);
	}
}
