package edu.ben.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
/**
 * Generates GUI for game
 * 
 * @author Adrian Cornely
 * @version 2
 *
 */
public class GUI extends JFrame {

	private JPanel grid;
	private JPanel buttonsPanel;
	private static JButton[][] buttonArray;

	private JLabel dieView = new JLabel("ROLL: ");
	private JLabel dieImageLabel;
	private JLabel statusLabel = new JLabel("MOVE STATUS: ");

	private JButton legendButton;
	private JButton moveButton;
	private JButton attackButton;
	private JButton cancelButton;
	private JButton storeButton;
	private JButton endTurnButton;
	private JButton helpButton;
	private JButton airStrikeButton;
	private JButton airStrikeCancelButton = new JButton();
	
	private Game game;

	private final EmptyBorder statsLabelBorder = new EmptyBorder(2, 10, 2, 10);

	private static final Color WARRIOR = Color.GREEN;
	private static final Color STEALTH = new Color(46, 185, 87);
	private static final Color ZOMBIE = Color.RED;
	private static final Color BUTTON_BACKGROUND = Color.DARK_GRAY;
	private static final Color BUTTON_HIGHLIGHT = Color.WHITE;
	private static final Color HOME_BASE_COLOR = Color.BLUE;
	private static final Color ARMY_BASE_COLOR = Color.ORANGE;
	private static final Color HOUSE_COLOR = Color.YELLOW;
	private static final Color DEPLOY_COLOR = new Color(255, 80, 180);

	private static final ImageIcon ICON_WARRIOR = new ImageIcon("images/warrior32px.png");
	private static final ImageIcon ICON_STEALTH = new ImageIcon("images/stealth32px.png");
	private static final ImageIcon ICON_ZOMBIE = new ImageIcon("images/zombieicon32px.png");
	private static final ImageIcon ICON_HOUSE = new ImageIcon("images/house32px.png");
	private static final ImageIcon REKT = new ImageIcon("images/rainbow_frog_rekt.gif");

	private static final int ICON_LABEL_SIZE = 32;

	private final String title = "Nerds vs. Zombies";

	private TeamInventory teamInv;

	private static final ImageIcon DICE_ONE = new ImageIcon("images/dice1.png");
	private static final ImageIcon DICE_TWO = new ImageIcon("images/dice2.png");
	private static final ImageIcon DICE_THREE = new ImageIcon("images/dice3.png");
	private static final ImageIcon DICE_FOUR = new ImageIcon("images/dice4.png");
	private static final ImageIcon DICE_FIVE = new ImageIcon("images/dice5.png");
	private static final ImageIcon DICE_SIX = new ImageIcon("images/dice6.png");
	private Store store;
	public boolean airstrikeForMirsky = false;

	/**
	 * Create the application.
	 */
	public GUI(Game g) {
		// set size of jbutton array based on board array size
		this.game = g;
		buttonArray = new JButton[g.getBoard().NUM_ROWS][g.getBoard().NUM_COLS];
		airStrikeCancelButton.setVisible(false);

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initialize(g.getBoard(), g.getPlayer(), g.getLogic());

		// set game icon
		ImageIcon zombieIcon = new ImageIcon("images/zombieicon128px.png");
		setIconImage(zombieIcon.getImage());
		g.getBoard().setButtonArray(buttonArray);

		// set button colors and icons to initial values
		g.getBoard().updateBoard();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param board
	 *            board to be used
	 */
	private void initialize(Board board, Player player, ZombieLogic logic) {
		// creates frame and maximizes it
		// frame = new JFrame();
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// sets up button panel
		initButtons(board, player, logic);
		// sets up panel for board
		initGrid(board);

		// adds panels to frame
		add(buttonsPanel, BorderLayout.PAGE_END);
		add(grid, BorderLayout.CENTER);
		setTitle(title);
	}

	/**
	 * Initiates grid jpanel
	 * 
	 * @param board
	 *            board to be used for grid
	 *
	 * @param player
	 *            A Player object.
	 */
	private void initGrid(Board board) {
		GridLayout boardLayout = new GridLayout(21, 39);
		grid = new JPanel(boardLayout);
		grid.setLayout(boardLayout);

		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 39; j++) {
				JButton newButton = new JButton();
				buttonArray[i][j] = newButton;
				buttonArray[i][j].setOpaque(true);
				newButton.addActionListener(new BoardListener(board, i, j, statusLabel, moveButton, attackButton,
						airStrikeCancelButton, cancelButton, storeButton, teamInv, game));

				grid.add(newButton);

			}
		}
	}

	/**
	 * Initiates button jpanel
	 * 
	 * @param board
	 *            board to be used
	 *
	 * @param player
	 *            Player object.
	 */
	private void initButtons(Board board, Player player, ZombieLogic logic) {
		buttonsPanel = new JPanel(new FlowLayout());
		helpButton = new JButton("?");
		legendButton = new JButton("LEGEND");
		storeButton = new JButton("STORE/INVENTORY");
		moveButton = new JButton("MOVE");
		attackButton = new JButton("ATTACK");
		cancelButton = new JButton("CANCEL");
		endTurnButton = new JButton("END TURN");
		airStrikeButton = new JButton("USE AIR STRIKE");
		airStrikeCancelButton.setText("CANCEL AIR STRIKE");

		dieImageLabel = new JLabel();

		store = new Store(player, game.getLog());
		teamInv = new TeamInventory(player, store, board, buttonArray, game);

		add(teamInv, BorderLayout.WEST);
		add(store, BorderLayout.EAST);

		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, new HelpPanel("STORE"), "Help", JOptionPane.PLAIN_MESSAGE);
			}
		});

		legendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, new LegendPanel("LEGEND"), "Legend", JOptionPane.PLAIN_MESSAGE);
			}
		});
		cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (!game.getBoard().isTeamMoved()) {
					moveButton.setEnabled(true);
					board.resetMoveBooleans();
				}
				if (!game.getBoard().isTeamAttacked()) {
					attackButton.setEnabled(true);
					board.resetAttackBooleans();
				}
				// used for the air strike
				storeButton.setEnabled(true);
				board.setAirStrikeAvailable(false);

				board.updateBoard();
			}
		});

		storeButton.addActionListener(new ActionListener() {
			private boolean isOpen = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isOpen) {
					teamInv.setVisible(true);
					store.setVisible(true);
					isOpen = true;
				} else {
					teamInv.setVisible(false);
					store.setVisible(false);
					isOpen = false;
				}

			}
		});
		attackButton.addActionListener(new AttackButtonListener(board, statusLabel, moveButton));
		moveButton.addActionListener(new MoveButtonListener(board, dieView, statusLabel, dieImageLabel, attackButton));

		endTurnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveButton.setEnabled(true);
				attackButton.setEnabled(true);
				storeButton.setEnabled(true);
				teamInv.getWarrior().setEnabled(true);
				teamInv.getStealth().setEnabled(true);
				cancelButton.setEnabled(true);

				if (game.getBoard().getAirStrikeClickCount() > 4) {
					airStrikeButton.setEnabled(true);
				}
				if(airstrikeForMirsky) {
					game.getBoard().setAirStrikeClickCount(game.getBoard().getAirStrikeClickCount() + 1);
				}
				
				airStrikeButton.setVisible(true);
				airStrikeCancelButton.setVisible(false);

				board.resetAttackAndMoveBooleans();
				board.setTeamMoved(false);
				board.setTeamAttacked(false);

				player.setEndTurn(true);
				logic.zombieTurn();

				// on the end click button, if a team is in the zombie range,
				// the horde will pursue.
				// for (int i = 0; i < game.logic.getHordesInRange().size();
				// i++) {
				// game.logic.zombiePursuit(game.logic.getZombieTargetTeam(),
				// (ZombieHorde) game.logic.getHordesInRange().get(i));
				//
				// }
				// if (!game.logic.getHordesInRange().isEmpty()) {
				// for (int i = 0; i < game.logic.getHordesInRange().size();
				// i++) {
				// game.logic.getHordesInRange().remove(i);
				// }
				//
				//
				// }
				board.updateBoard();
				board.getDie().roll();
			}
		});

		airStrikeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] airStrikeOptions = { "Row", "Column" };

				int airStrikeChosenOption = JOptionPane.showOptionDialog(null,
						"Choose if you want to perform an a air strike a row or column", "Air Strike",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, airStrikeOptions,
						airStrikeOptions[1]);

				// if row was selected
				if (airStrikeChosenOption == 0) {
					board.setAirStrikeAvailable(true);
					board.setAirStrikeChosenOption(0);
					
					moveButton.setEnabled(false);
					storeButton.setEnabled(false);
					attackButton.setEnabled(false);
					cancelButton.setEnabled(false);
					airStrikeButton.setEnabled(false);
					game.getBoard().setAirStrikeClickCount(0);
					airStrikeButton.setVisible(false);
					airStrikeCancelButton.setVisible(true);
					
					// if column was selected
				} else if (airStrikeChosenOption == 1) {
					board.setAirStrikeAvailable(true);
					board.setAirStrikeChosenOption(1);
					
					moveButton.setEnabled(false);
					storeButton.setEnabled(false);
					attackButton.setEnabled(false);
					airStrikeButton.setVisible(false);
					cancelButton.setEnabled(false);
					airStrikeButton.setEnabled(false);
					airStrikeCancelButton.setVisible(true);
					game.getBoard().setAirStrikeClickCount(0);
					
				} else if (airStrikeChosenOption == JOptionPane.CLOSED_OPTION) {
					
					storeButton.setEnabled(true);
					moveButton.setEnabled(true);
					attackButton.setEnabled(true);
					board.setAirStrikeAvailable(false);
				}
				
				

			}

		});
		
		airStrikeCancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.setAirStrikeAvailable(false);
				
				airStrikeButton.setVisible(true);
				airStrikeCancelButton.setVisible(false);
				storeButton.setEnabled(true);
				moveButton.setEnabled(true);
				attackButton.setEnabled(true);
				cancelButton.setEnabled(true);
				airStrikeButton.setEnabled(true);
				
				
			}
			
		});
		
		dieImageLabel.setOpaque(true);
		dieImageLabel.setPreferredSize(new Dimension(ICON_LABEL_SIZE, ICON_LABEL_SIZE));
		dieImageLabel.setBackground(BUTTON_BACKGROUND);

		dieView.setOpaque(true);
		dieView.setBackground(Color.DARK_GRAY);
		dieView.setForeground(Color.WHITE);
		dieView.setBorder(statsLabelBorder);

		statusLabel.setOpaque(true);
		statusLabel.setBackground(Color.DARK_GRAY);
		statusLabel.setForeground(Color.WHITE);

		buttonsPanel.add(helpButton);
		buttonsPanel.add(legendButton);
		buttonsPanel.add(storeButton);
		buttonsPanel.add(attackButton);
		buttonsPanel.add(moveButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(endTurnButton);
		buttonsPanel.add(airStrikeButton);
		buttonsPanel.add(airStrikeCancelButton);

		buttonsPanel.add(dieView);
		buttonsPanel.add(dieImageLabel);
		buttonsPanel.add(statusLabel);

	}

	/**
	 * Generates a new Color object with the same RGB values as the passed in
	 * Color object.
	 * 
	 * @param color
	 *            The Color Object to be copied.
	 * @return A new Color object with the same RGB values as the passed in
	 *         parameter.
	 */
	private static Color copyColor(Color color) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Copies the passed in ImageIcon object.
	 * 
	 * @param imgIcon
	 *            The ImageIcon object to be copied.
	 * @return Returns a new ImageIcon object containing the same image as the
	 *         passed in parameter.
	 */
	private static ImageIcon copyImageIcon(ImageIcon imgIcon) {
		return new ImageIcon(imgIcon.getImage());
	}

	/**
	 * Getter for JButton array.
	 * 
	 * @return Returns the JButton array used for the board.
	 */
	public JButton[][] getButtonArray() {
		return buttonArray;
	}

	/**
	 * Getter for warrior color.
	 * 
	 * @return Returns a copy of the warrior Color object.
	 */
	public static Color getWarrior() {
		return copyColor(WARRIOR);
	}

	/**
	 * Getter for stealth color.
	 * 
	 * @return Returns a copy of the stealth Color object.
	 */
	public static Color getStealth() {
		return copyColor(STEALTH);
	}

	/**
	 * Getter for zombie color.
	 * 
	 * @return Returns a copy of the zombie Color object.
	 */
	public static Color getZombie() {
		return copyColor(ZOMBIE);
	}

	/**
	 * Getter for button background color.
	 * 
	 * @return Returns a copy of the button background Color object.
	 */
	public static Color getButtonBackground() {
		return copyColor(BUTTON_BACKGROUND);
	}

	/**
	 * Getter for button highlight color.
	 * 
	 * @return Returns a copy of the button highlight Color object.
	 */
	public static Color getButtonHighlight() {
		return copyColor(BUTTON_HIGHLIGHT);
	}

	/**
	 * Getter for home base color.
	 * 
	 * @return Returns a copy of the home base Color object.
	 */
	public static Color getHomeBaseColor() {
		return copyColor(HOME_BASE_COLOR);
	}

	/**
	 * Getter for army base color.
	 * 
	 * @return Returns a copy of the army base Color object.
	 */
	public static Color getArmyBaseColor() {
		return copyColor(ARMY_BASE_COLOR);
	}

	/**
	 * Getter for house color.
	 * 
	 * @return Returns a copy of the house Color object.
	 */
	public static Color getHouseColor() {
		return copyColor(HOUSE_COLOR);
	}

	/**
	 * Getter for deploy color.
	 * 
	 * @return Returns a copy of the deploy Color object.
	 */
	public static Color getDeployColor() {
		return copyColor(DEPLOY_COLOR);
	}

	/**
	 * Getter for warrior icon.
	 * 
	 * @return Returns a copy of the warrior ImageIcon object.
	 */
	public static ImageIcon getIconWarrior() {
		return copyImageIcon(ICON_WARRIOR);
	}

	/**
	 * Getter for stealth icon.
	 * 
	 * @return Returns a copy of the stealth ImageIcon object.
	 */
	public static ImageIcon getIconStealth() {
		return copyImageIcon(ICON_STEALTH);
	}

	/**
	 * Getter for zombie icon.
	 * 
	 * @return Returns a copy of the zombie ImageIcon object.
	 */
	public static ImageIcon getIconZombie() {
		return copyImageIcon(ICON_ZOMBIE);
	}

	/**
	 * Getter for house icon.
	 * 
	 * @return Returns a copy of the warrior ImageIcon object.
	 */
	public static ImageIcon getIconHouse() {
		return copyImageIcon(ICON_HOUSE);
	}

	/**
	 * Getter for meme icon.
	 * 
	 * @return Returns a copy of the meme ImageIcon object.
	 */
	public static ImageIcon getRekt() {
		return copyImageIcon(REKT);
	}

	/**
	 * Returns an array containing copies of the dice image icons. Array
	 * locations correspond to the respective dice value - 1.
	 * 
	 * USAGE: use roll value - 1 to access corresponding die image icon. E.g. if
	 * roll is 6, use roll - 1 to access the image icon for 6.
	 * 
	 * @return Returns an array of ImageIcons containing an icon for each face
	 *         of a six-sided die.
	 */
	public static ImageIcon[] getDiceIcons() {
		return new ImageIcon[] { copyImageIcon(DICE_ONE), copyImageIcon(DICE_TWO), copyImageIcon(DICE_THREE),
				copyImageIcon(DICE_FOUR), copyImageIcon(DICE_FIVE), copyImageIcon(DICE_SIX) };
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public JButton getMoveButton() {
		return moveButton;
	}

	public void setMoveButton(JButton moveButton) {
		this.moveButton = moveButton;
	}

	public JButton getEndTurnButton() {
		return endTurnButton;
	}

	public void setEndTurnButton(JButton endTurnButton) {
		this.endTurnButton = endTurnButton;
	}

	public JButton getAirStrikeButton() {
		return airStrikeButton;
	}

	public void setAirStrikeButton(JButton airStrikeButton) {
		this.airStrikeButton = airStrikeButton;
	}

}
