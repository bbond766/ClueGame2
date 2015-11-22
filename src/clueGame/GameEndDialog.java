package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameEndDialog extends JDialog implements ActionListener {
	private JFrame parent;
	public GameEndDialog(JFrame parent, String title, String message) {
		super(parent, title);
		setSize(400, 200);
		this.parent = parent;
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		add(messagePane);
		JPanel buttonPane = new JPanel();
		JButton button = new JButton ("OK");
		button.addActionListener(this);
		buttonPane.add(button);
		add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		parent.removeAll();
		parent.dispose();
		dispose();
	}
}
