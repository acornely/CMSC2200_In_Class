package edu.ben.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Attack controller that allows a team to attack a zombie horde
 * 
 * @author Jaime Sanchez - jsanchez
 * @version 1.0
 *
 */
public class AttackButtonListener implements ActionListener {

	private Board b;
	private JLabel statusLabel;
	private JButton moveButton;

	public AttackButtonListener(Board b, JLabel statusLabel, JButton moveButton) {
		this.b = b;
		this.statusLabel = statusLabel;
		this.moveButton = moveButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!b.isAttackEnabled()) {
			moveButton.setEnabled(false);
			if (e.getSource() instanceof JButton) {
				JButton button = (JButton) e.getSource();
				button.setEnabled(false);
			}
			b.setMoveEnabled(false);
			b.setAttackEnabled(true);
			b.getDie().roll();
			statusLabel.setText("ATTACK STATUS: SELECT A TEAM");
		}
	}
}
