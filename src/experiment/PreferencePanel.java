package experiment;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PreferencePanel extends JPanel {
	private JRadioButton musicButton, noMusicButton;
	private DisplayPanel dp;
	
	public PreferencePanel(DisplayPanel dp) {
		this.dp = dp;		
		musicButton = new JRadioButton("Music");
		noMusicButton = new JRadioButton("No Music");
		noMusicButton.setSelected(true);
		add(musicButton);
		add(noMusicButton);
		ButtonGroup group = new ButtonGroup();
		group.add(musicButton);
		group.add(noMusicButton);
		setBorder(new TitledBorder (new EtchedBorder(), "Preferences"));
		setLayout(new GridLayout(2, 1));
		RadioListener listener = new RadioListener();
		musicButton.addActionListener(listener);
		noMusicButton.addActionListener(listener);
	}
	
	private class RadioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (musicButton.isSelected())
				dp.setMusic("music");
			else
				dp.setMusic("no music");
		}
	}
}
