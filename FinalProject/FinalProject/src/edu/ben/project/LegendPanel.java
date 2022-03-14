package edu.ben.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Functions as a legend for game squares and icons. Displays information about
 * the board icons and their respective colors.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
@SuppressWarnings("serial")
public class LegendPanel extends InfoPanel {

	private static final int SIZE = 32;

	private JLabel warriorLabel;
	private JLabel stealthLabel;
	private JLabel zombieLabel;
	private JLabel houseLabel;
	private JLabel homeBaseLabel;
	private JLabel armyBaseLabel;
	private JLabel openSquareLabel;

	private JLabel warriorTextLabel;
	private JLabel stealthTextLabel;
	private JLabel zombieTextLabel;
	private JLabel houseTextLabel;
	private JLabel homeBaseTextLabel;
	private JLabel armyBaseTextLabel;
	private JLabel openSquareTextLabel;

	private JLabel[] iconLabels;
	private JLabel[] textLabels;

	private static final String WARRIOR_TEXT = "WARRIOR TEAM";
	private static final String STEALTH_TEXT = "STEALTH TEAM";
	private static final String ZOMBIE_TEXT = "ZOMBIE HORDE";
	private static final String HOUSE_TEXT = "HOUSE";
	private static final String HOME_BASE_TEXT = "HOME BASE";
	private static final String ARMY_BASE_TEXT = "ARMY BASE";
	private static final String OPEN_SQUARE_TEXT = "OPEN SQUARE";

	private String[] texts;

	private static final Color TEXT_COLOR = Color.WHITE;
	private static final Color BACKGROUND_COLOR = GUI.getButtonBackground().darker();
	private static final Font DESCRIPTION_FONT = new Font("OCR A Extended", Font.PLAIN, 18);
	private static final Font BORDER_TITLE_FONT = new Font("OCR A Extended", Font.PLAIN, 20);

	/**
	 * Constructor. Assigns values to panels and labels to for the legend panel.
	 */
	public LegendPanel(String title) {
		super(title);
		setBorder(new EmptyBorder(7, 10, 7, 10));
		setBackground(BACKGROUND_COLOR);
		// setPreferredSize(new Dimension(500, 360));
		setVisible(true);

		TitledBorder border = new TitledBorder(BorderFactory.createLoweredSoftBevelBorder(), "LEGEND");
		border.setTitleFont(BORDER_TITLE_FONT);
		border.setTitleColor(TEXT_COLOR);

		// create labels and set icons if applicable
		warriorLabel = new JLabel(GUI.getIconWarrior());
		stealthLabel = new JLabel(GUI.getIconStealth());
		zombieLabel = new JLabel(GUI.getIconZombie());
		houseLabel = new JLabel(GUI.getIconHouse());
		homeBaseLabel = new JLabel();
		armyBaseLabel = new JLabel();
		openSquareLabel = new JLabel();

		// set label background colors
		warriorLabel.setBackground(GUI.getWarrior());
		stealthLabel.setBackground(GUI.getStealth());
		zombieLabel.setBackground(GUI.getZombie());
		houseLabel.setBackground(GUI.getHouseColor());
		homeBaseLabel.setBackground(GUI.getHomeBaseColor());
		armyBaseLabel.setBackground(GUI.getArmyBaseColor());
		openSquareLabel.setBackground(GUI.getButtonBackground());

		// create text labels for descriptions
		warriorTextLabel = new JLabel();
		stealthTextLabel = new JLabel();
		zombieTextLabel = new JLabel();
		houseTextLabel = new JLabel();
		homeBaseTextLabel = new JLabel();
		armyBaseTextLabel = new JLabel();
		openSquareTextLabel = new JLabel();

		// put labels into array
		iconLabels = new JLabel[] { warriorLabel, stealthLabel, zombieLabel, houseLabel, homeBaseLabel, armyBaseLabel,
				openSquareLabel };

		texts = new String[] { WARRIOR_TEXT, STEALTH_TEXT, ZOMBIE_TEXT, HOUSE_TEXT, HOME_BASE_TEXT, ARMY_BASE_TEXT,
				OPEN_SQUARE_TEXT };

		textLabels = new JLabel[] { warriorTextLabel, stealthTextLabel, zombieTextLabel, houseTextLabel,
				homeBaseTextLabel, armyBaseTextLabel, openSquareTextLabel };

		// set text label values
		for (int i = 0; i < textLabels.length; i++) {
			textLabels[i].setText(texts[i]);
			textLabels[i].setForeground(TEXT_COLOR);
			textLabels[i].setFont(DESCRIPTION_FONT);
		}

		// set icon label values
		for (int i = 0; i < iconLabels.length; i++) {
			iconLabels[i].setPreferredSize(new Dimension(SIZE, SIZE));
			iconLabels[i].setOpaque(true);
		}

		// add each label to its own panel and then add the panel to the main
		// panel.
		for (int i = 0; i < textLabels.length; i++) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panel.add(iconLabels[i]);
			panel.add(textLabels[i]);
			panel.setBackground(BACKGROUND_COLOR);
			getMainPane().add(panel);
		}

	}

}
