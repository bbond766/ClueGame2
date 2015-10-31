package experiment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FirstGUI extends JFrame {
	private JTextField myName;
	private FirstGUI gui;
	
	public FirstGUI() {
		gui = this;
		setTitle("My First GUI");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	
	public static void main(String[] args) {
		FirstGUI gui = new FirstGUI();
		gui.setVisible(true);
	}
	
	private void createLayout(){
		//create a JPanel
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Name");
		myName = new JTextField(20);
		//Add element to the panel
		panel.add(nameLabel);
		panel.add(myName);
		//Add the panel to the layout.
		add(panel, BorderLayout.CENTER);	
//		add(nameLabel, BorderLayout.NORTH);
//		add(myName, BorderLayout.CENTER);
		JButton nameButton = new JButton("OK");
		add(nameButton, BorderLayout.SOUTH);
		nameButton.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
		//	String message = "Hello" + myName.getText();
		//	JOptionPane.showMessageDialog(null,message);
			
		//	String numStr = JOptionPane.showInputDialog("Enter you age");
		//	int num = Integer.parseInt(numStr);
		//	int yearsLeft = 100 - num;
		//	JOptionPane.showMessageDialog(null, "You have " + yearsLeft + " years left");
			
			int ready = JOptionPane.showConfirmDialog(null,"Are you ready to continue?");
			if(ready == JOptionPane.YES_OPTION)
				JOptionPane.showMessageDialog(gui, "Here we go!");
			else
				JOptionPane.showMessageDialog(gui, "Ok, we'll wait.");
		}
	}
}
