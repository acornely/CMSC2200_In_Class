package edu.ben.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Inventory controller allowing a user to select teams, units and purchase
 * weapons
 * 
 * @author Jaime Sanchez - jsanchez
 * @version 1.0
 */
public class StoreActionListener implements ActionListener {
	private boolean isOpen = false;
	private Store panel;
	private JButton[][] buttonArray;

	public StoreActionListener(Store s, JButton[][] buttonArray) {
		this.buttonArray = buttonArray;
		panel = s;
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
	public void actionPerformed(ActionEvent arg0) {
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
