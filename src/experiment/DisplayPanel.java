package experiment;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DisplayPanel extends JPanel {
	private String name, fromCity, toCity, willDrive, music;
	private JTextArea display;
	
	public DisplayPanel() {
		name = "Someone";
		fromCity = "Somewhere";
		toCity = "Somewhere";
		willDrive = "needs a ride";
		music = "no music";
		display = new JTextArea(2, 20);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		updateDisplay();
		add(display);
	}
	
	private void updateDisplay() {
		display.setText(name + " " + willDrive + " from " + fromCity
						+ " to " + toCity + " and prefers " + music);
	}
	
	public void setMusic(String music) {
		this.music = music;
		updateDisplay();
	}
}
