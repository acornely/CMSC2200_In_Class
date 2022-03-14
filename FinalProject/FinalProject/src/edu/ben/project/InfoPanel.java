package edu.ben.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	private JPanel mainPane;

	public static final Color TEXT_COLOR = Color.WHITE;
	public static final Color BACKGROUND_COLOR = GUI.getButtonBackground().darker();
	public static final Font DESCRIPTION_FONT = new Font("OCR A Extended", Font.PLAIN, 18);
	public static final Font BORDER_TITLE_FONT = new Font("OCR A Extended", Font.PLAIN, 20);

	public InfoPanel(String title) {
		setBorder(new EmptyBorder(7, 10, 7, 10));
		setBackground(BACKGROUND_COLOR);
		setVisible(true);

		TitledBorder border = new TitledBorder(BorderFactory.createLoweredSoftBevelBorder(), title);
		border.setTitleFont(BORDER_TITLE_FONT);
		border.setTitleColor(TEXT_COLOR);

		mainPane = new JPanel();
		mainPane.setBackground(BACKGROUND_COLOR);
		mainPane.setBorder(border);
		add(mainPane, BorderLayout.CENTER);

		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
	}

	/**
	 * Getter for main pane JPanel.
	 * 
	 * @return The main pane JPanel.
	 */
	public JPanel getMainPane() {
		return mainPane;
	}

}
