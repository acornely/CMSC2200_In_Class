package edu.ben.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Displays help information to the user. User may select different help
 * buttons.
 * 
 * @author Colom Boyle
 *
 */
@SuppressWarnings("serial")
public class HelpPanel extends InfoPanel {

	private JButton gameRulesHelpButton;
	private JButton storeHelpButton;
	private JButton combatHelpButton;
	private JButton teamsHelpButton;
	private JButton moveHelpButton;

	private ArrayList<JButton> helpButtons;

	/**
	 * Constructor. Creates buttons and help info.
	 * 
	 * @param title
	 *            The panel title
	 */
	public HelpPanel(String title) {
		super(title);

		helpButtons = new ArrayList<>();

		gameRulesHelpButton = new JButton("GAME RULES");
		storeHelpButton = new JButton("STORE HELP");
		combatHelpButton = new JButton("COMBAT HELP");
		moveHelpButton = new JButton("MOVEMENT HELP");
		teamsHelpButton = new JButton("TEAMS HELP");

		helpButtons.add(gameRulesHelpButton);
		helpButtons.add(storeHelpButton);
		helpButtons.add(combatHelpButton);
		helpButtons.add(teamsHelpButton);
		helpButtons.add(moveHelpButton);

		for (int i = 0; i < helpButtons.size(); i++) {
			helpButtons.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
			getMainPane().add(helpButtons.get(i));
		}

		gameRulesHelpButton.addActionListener(new ActionListener() {

			private InfoPanel gameRulesInfo = new InfoPanel("Game Rules");
			private JLabel description = new JLabel("<html><br>GAME RULES: See spec for further details...<br></hmtl>");

			@Override
			public void actionPerformed(ActionEvent e) {
				setInfoPanelContents(gameRulesInfo, description);
				JOptionPane.showMessageDialog(null, gameRulesInfo, "Game Rules Help", JOptionPane.PLAIN_MESSAGE);
			}
		});

		storeHelpButton.addActionListener(new ActionListener() {

			private InfoPanel storeInfo = new InfoPanel("Store Help");
			private JLabel description = new JLabel(
					"<html>The store is where teams, weapons, and ammunition can be purchased.<br>"
							+ "To use the store, first select the [STORE/INVENTORY] button.<br><br>RIGHT PANEL: Purchase teammates, weapons and ammo from this panel."
							+ "<br><br>LEFT PANEL: Create and deploy teams from this panel.<br>"
							+ "Select the type of team, then add the purchased teammates, weapons, and ammo as necessary.<br><br>"
							+ "Click the [RESET TEAM] button to cancel the creation of the current team.<br>"
							+ "Click the [DEPLOY] button to deploy the team on the field.<br>"
							+ "The deployment area will be highlighted; click anywhere within the highlighted area to deploy the team.</html>");

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setInfoPanelContents(storeInfo, description);
				JOptionPane.showMessageDialog(null, storeInfo, "Store Help", JOptionPane.PLAIN_MESSAGE);
			}
		});

		combatHelpButton.addActionListener(new ActionListener() {
			private InfoPanel combatInfo = new InfoPanel("Combat Help");
			private JLabel description = new JLabel(
					"<html>Combat is turn-based. A team may use their weapons to attack if they are in range of a zombie horde.<br>"
							+ "Zombies attack adjacent targets. Moving or attacking will create noise that will alert nearby hordes to your presence.<br>"
							+ "<br>To intiate an attack, click the [ATTACK] button, select the team to attack, then select the target.<br>"
							+ "A dialog message will display the results of the attack. If a bat is used, the damage is multiplied by a die roll.<br>"
							+ "<br>On a horde attack, the horde and team roll individual values. If team defense calculation is lower than the horde's attack value, team members will be killed<br>A dialog message will display the result of a horde attack.</html>");

			@Override
			public void actionPerformed(ActionEvent e) {
				setInfoPanelContents(combatInfo, description);
				JOptionPane.showMessageDialog(null, combatInfo, "Combat Help", JOptionPane.PLAIN_MESSAGE);

			}
		});

		teamsHelpButton.addActionListener(new ActionListener() {
			private InfoPanel teamInfo = new InfoPanel("Team Help");
			private JLabel description = new JLabel("<html>Players have two types of teams available.<br><br>"
					+ "WARRIOR: Warriors function as a heavy damage class, capable of using shotguns and/or a baseball bat.<br>"
					+ "Their damage output is balanced by slower movement speed and a larger noise generation radius.<br><br>"
					+ "STEALTH: Stealth teams lack the damage output of warriors, but have double the movement speed and a much smaller noise radius.<br>"
					+ "Stealth teams make use of crossbows.</hmtl>");

			@Override
			public void actionPerformed(ActionEvent e) {
				setInfoPanelContents(teamInfo, description);
				JOptionPane.showMessageDialog(null, teamInfo, "Team Help", JOptionPane.PLAIN_MESSAGE);

			}
		});

		moveHelpButton.addActionListener(new ActionListener() {
			private InfoPanel moveInfo = new InfoPanel("Move Help");
			private JLabel description = new JLabel(
					"<html>To iniate a move, select the [MOVE] button. The move die will be rolled and displayed in bottom right corner."
							+ "<br>Next, click the team to be moved. All avilable move locations will be highlighted white.<br>"
							+ "Select a destination location within the white move zone to move th team.<br>"
							+ "A player can still select a move if an invalid move is performed first.</html>");

			@Override
			public void actionPerformed(ActionEvent e) {
				setInfoPanelContents(moveInfo, description);
				JOptionPane.showMessageDialog(null, moveInfo, "Move Help", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Adds the passed in JLabel to the passed in InfoPanel's main pane and sets
	 * the foreground text color of the JLabel.
	 * 
	 * @param panel
	 *            An InfoPanel object.
	 * @param description
	 *            A JLabel used for a description.
	 */
	private void setInfoPanelContents(InfoPanel panel, JLabel description) {
		description.setForeground(TEXT_COLOR);
		panel.getMainPane().add(description);
	}

}
