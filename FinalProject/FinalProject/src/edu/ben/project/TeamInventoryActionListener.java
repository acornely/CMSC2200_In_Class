package edu.ben.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TeamInventoryActionListener implements ActionListener {
	private boolean isOpen = false;
	private TeamInventory panel;
	private JButton[][] buttonArray;

	public TeamInventoryActionListener(TeamInventory ti, JButton[][] buttonArray) {
		this.buttonArray = buttonArray;
		panel = ti;

	}

	private void disableButtons() {
		for (int i = 0; i < buttonArray.length; i++) {
			for (int j = 0; j < buttonArray[i].length; j++) {
				buttonArray[i][j].setEnabled(false);
			}

		}
	}

	private void enableButtons() {
		for (int i = 0; i < buttonArray.length; i++) {
			for (int j = 0; j < buttonArray[i].length; j++) {
				buttonArray[i][j].setEnabled(true);

			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isOpen) {
			// disableButtons();
			panel.setVisible(true);
			isOpen = true;
		} else {
			enableButtons();
			panel.setVisible(false);

			isOpen = false;
		}

	}

}
