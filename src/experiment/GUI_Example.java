package experiment;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_Example extends JFrame {
	private JTextField name;

	public GUI_Example()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GUI Example");
		setSize(250, 150);
		JPanel panel = createNamePanel();
		add(panel, BorderLayout.NORTH);
		panel = createButtonPanel();
		add(panel, BorderLayout.CENTER);
	}

	 private JPanel createNamePanel() {
		 	JPanel panel = new JPanel();
		 	JLabel nameLabel = new JLabel("Name");
			name = new JTextField(20);
			panel.setLayout(new GridLayout(1,2));
			panel.add(nameLabel);
			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
			return panel;
	}
	 
	private JPanel createButtonPanel() {
		JButton agree = new JButton("I agree");
		JButton disagree = new JButton("I disagree");
		JPanel panel = new JPanel();
		panel.add(agree);
		panel.add(disagree);
		return panel;
	}
	
	public static void main(String[] args) {
		GUI_Example gui = new GUI_Example();	
		gui.setVisible(true);
	}


}