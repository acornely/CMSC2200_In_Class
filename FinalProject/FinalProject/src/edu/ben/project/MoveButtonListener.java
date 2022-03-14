package edu.ben.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Button listener for move button. Sets boolean moveEnabled value in Board when
 * clicked and rolls the die. Updates text of roll and move status JLabels.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class MoveButtonListener implements ActionListener {

	private Board b;
	private JLabel dieRollLabel;
	private JLabel statusLabel;
	private JLabel dieImageLabel;
	private JButton attackButton;
	

	/**
	 * Constructor. Sets Board and necessary JLabels from parameters.
	 * 
	 * @param b
	 *            A Board object.
	 * @param dieRollLabel
	 *            A JPanel for die rolls.
	 * @param statusLabel
	 *            A JPanel for status messages.
	 */
	public MoveButtonListener(Board b, JLabel dieRollLabel, JLabel statusLabel, JLabel dieImageLabel,
			JButton attackButton) {
		this.b = b;
		this.dieRollLabel = dieRollLabel;
		this.statusLabel = statusLabel;
		this.attackButton = attackButton;
		this.dieImageLabel = dieImageLabel;
		
	}

	/**
	 * Enables player moves if button has not already been pressed. Rolls die
	 * for move and updates JLabel with new roll values.
	 * 
	 * @param e
	 *            ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (!b.isMoveEnabled()) {
			b.setMoveEnabled(true);
			if (e != null && e.getSource() instanceof JButton) {
				JButton button = (JButton) e.getSource();
				button.setEnabled(false);
			}
			attackButton.setEnabled(false);
			if (b.isMoveCompleted()) {
//				b.getDie().roll();
				dieImageLabel.setIcon(GUI.getDiceIcons()[b.getDie().getCurrentRoll() - 1]);
			}
			dieRollLabel.setText("ROLL: " + Integer.toString(b.getDie().getCurrentRoll()));
			statusLabel.setText("MOVE STATUS: SELECT A TEAM TO MOVE");
		}

	}
}
