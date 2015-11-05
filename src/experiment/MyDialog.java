package experiment;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyDialog extends JDialog {
	private JTextField myName;
	private JPasswordField password;
	
	public MyDialog() {
		setTitle("Login Dialog");
		setSize(300, 200);
		setLayout(new GridLayout(0, 2));
		JLabel nameLabel = new JLabel("Name");
		myName = new JTextField(20);
		JLabel pwdLabel = new JLabel("Password");
		password = new JPasswordField(20);
		add(nameLabel);
		add(myName);
		add(pwdLabel);
		add(password);

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(button);
	}
	
	public String getMyName() {
		return myName.getText();
	}
}
