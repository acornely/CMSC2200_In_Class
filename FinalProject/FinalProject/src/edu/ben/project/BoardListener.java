package edu.ben.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Contains logic to allow a Team to move. Displays available moves when a Team
 * is selected, if desired move is valid the selected Team is moved to the new
 * location. Valid moves will be indicated with a success message, while invalid
 * moves are indicated with an invalid move message. This includes separate
 * messages for different scenarios, such as a destination square being occupied
 * or a move selected which exceeds the current turn's move distance limit.
 * 
 * @author Multiple
 * @version 1
 * 
 */
public class BoardListener implements ActionListener {

	private Board b;
	private int row;
	private int col;
	private TeamInventory teamInv;
	private JButton moveButton;
	private JButton attackButton;
	private JButton airStrikeCancelButton;
	private JButton cancelButton;
	private JButton storeButton;
	private JLabel statusLabel;
	private Game game;
	


	/**
	 * Constructor. Takes a Board object, current row and column, a JButton
	 * array, and a JLabel for movement output messages.
	 * 
	 * @param b
	 *            A Board object.
	 * @param row
	 *            Row value.
	 * @param col
	 *            Column value.
	 * @param buttonArray
	 *            A JButton array.
	 * @param statusLabel
	 *            A JLabel for movement output messages.
	 */
	public BoardListener(Board b, int row, int col, JLabel statusLabel, JButton moveButton, JButton attackButton, JButton airStrikeCancelButton, JButton cancelButton,
			 JButton storeButton, TeamInventory teamInv, Game game) {
		this.b = b;
		this.row = row;
		this.col = col;
		this.statusLabel = statusLabel;
		this.moveButton = moveButton;
		this.attackButton = attackButton;
		this.airStrikeCancelButton = airStrikeCancelButton;
		this.game = game;
		this.cancelButton = cancelButton;
		this.storeButton = storeButton;
		this.teamInv = teamInv;
		


	}

	/**
	 * Enables the movement of Teams. Works in two stages.
	 * 
	 * First Stage: if no team location has been selected, a Team must be
	 * picked. The movement button needs to have been clicked in order to enable
	 * movement, otherwise this method will not perform an action. Coordinate
	 * data is saved to CoordinateHolder for use in the destination listener.
	 * 
	 * Second Stage: If a Team has been selected and the user selects an open
	 * destination, the destination is evaluated for validity. Destination must
	 * be within distance limit. If it is valid, the Team is moved to this new
	 * location, completing the movement operation. If not valid, an error
	 * message is displayed and the user may attempt a valid move again.
	 * 
	 * Third Stage (Optional): Displays an error message if the user selects a
	 * destination that is currently occupied. User may attempt a valid move
	 * again.
	 * 
	 * @param e
	 *            ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// initial team selection click. To proceed, the move button must have
		// been clicked, no teams may be currently selected, and the selected
		// location must be of type Team.
		if (b.isMoveEnabled() && !b.isTeamSelected() && b.getBoardArray()[row][col] instanceof Team) {
			selectTeamToMove();

			// second click for destination. Move button must have been enabled,
			// user must currently be able to select a destination. The
			// desired destination must not be currently occupied (null location
			// will not have Team or ZombieHorde).
		} else if (b.isMoveEnabled() && b.isDestinationSelectable() && b.getBoardArray()[row][col] == null) {
			selectMoveDestination();

		} else if (b.isMoveEnabled() && b.isTeamSelected()
				&& (b.getBoardArray()[row][col] instanceof Team || b.getBoardArray()[row][col] instanceof ZombieHorde)
				&& b.getCoordinateHolder().getCurrentGroup() != b.getBoardArray()[row][col]) {

			destinationFilledLogic();

		}

		// check for attacks initial team selection click. To proceed, the
		// attack button must have been clicked, no teams may be currently
		// selected, and the selected location must be of type Team.
		if (b.isAttackEnabled() && !b.isTeamSelected() && b.getBoardArray()[row][col] instanceof Team) {

			selectAttackingTeam();

			// second click for attack coordinate. Attack button must have been
			// enabled, user must currently be able to select a target. The
			// selected cell must be a horde
		} else if (b.isAttackEnabled() && b.isTargetSelectable()
				&& b.getBoardArray()[row][col] instanceof ZombieHorde) {

			selectAttackCoordinate();

		} else if (b.isAttackEnabled() && b.isTargetSelectable()
				&& !(b.getBoardArray()[row][col] instanceof ZombieHorde)) {

			attackCoordNotZombie();

		}

		// Deploy
		if (b.isDeployable()) {
			Team t = teamInv.getPlayer().getCurrentTeam();

			if (t != null && t.isTeamDeployable() && b.isValidDeployCoordinate(row, col)) {
				b.getBoardArray()[row][col] = t;
				t.setRow(row);
				t.setCol(col);
				b.setDeployable(false);

				teamInv.disableDeployButton();
				teamInv.getWarrior().setEnabled(false);
				teamInv.getStealth().setEnabled(false);
				teamInv.getPlayer().setCurrentTeam(null);
				b.updateBoard();

				b.setMoveCompleted(true);

				game.getLog().gameLogs("Team was deployed at row " + row + " col " + col);

			}
		}
		// AirStrike attack
		if (b.isAirStrikeAvailable()) {
			ArrayList<ZombieHorde> hordesKilled = b.airStrikeAttack(b.getAirStrikeChosenOption(), row, col);
			game.getLogic().removeKilledHordes(hordesKilled);
			b.updateBoard();
			airStrikeCancelButton.setEnabled(false);
			cancelButton.setEnabled(false);
			b.setAirStrikeAvailable(false);
			storeButton.setEnabled(true);
			b.setAirStrikeClickCount(0);
			
			
		}

	}

	private void selectTeamToMove() {
		b.setTeamSelected(true);
		b.setMoveCompleted(false);

		// assign current coord values of Team to coordinate holder
		b.getCoordinateHolder().setCurrentRow(row);
		b.getCoordinateHolder().setCurrentCol(col);

		b.getCoordinateHolder().setCurrentGroup(b.getBoardArray()[row][col]);

		// highlight movable areas
		int distance = b.getDie().getCurrentRoll() * b.getCoordinateHolder().getCurrentGroup().getSpeedMultiplier();
		b.highlightColors(distance, row, col, GUI.getButtonHighlight());

		// enable second part of move: destination selection
		b.setDestinationSelectable(true);
	}

	private void selectMoveDestination() {
		b.getCoordinateHolder().setDestinationRow(row);
		b.getCoordinateHolder().setDestinationCol(col);

		int currentRow = b.getCoordinateHolder().getCurrentRow();
		int currentCol = b.getCoordinateHolder().getCurrentCol();

		int distance = b.getDie().getCurrentRoll() * b.getCoordinateHolder().getCurrentGroup().getSpeedMultiplier();

		// check valid move
		if (b.isMoveValid(distance, currentRow, currentCol, row, col)) {
			// generate path
			Stack<Node> path = b.getPath(currentRow, currentCol, row, col);
			Team t = (Team) b.getBoardArray()[currentRow][currentCol];

			game.getLogic().checkMoveNoiseRadius(path, t);
			// move team to destination location
			moveCommand(b.getCoordinateHolder().getCurrentGroup(), currentRow, currentCol);

			b.setMoveEnabled(false);
			// b.setDestinationSelectable(false);

			statusLabel.setText("MOVE STATUS: SUCCESS!");
			b.setMoveCompleted(true);

			// team has moved
			b.setTeamMoved(true);
			// disable move button
			moveButton.setEnabled(false);

			// log after team moves
			game.getLog().gameLogs("Team at row " + currentRow + " col " + currentCol + " moved to row " + row + " col " + col);

			// logic to check house, if questItem,

			if (b.getBuildingHolder().isHouse(row, col)) {
				Building house = null;
				for (int i = 0; i < b.getBuildingHolder().getHouses().size(); i++) {

					if (b.getBuildingHolder().getHouses().get(i).getRow() == row
							&& b.getBuildingHolder().getHouses().get(i).getCol() == col) {
						house = b.getBuildingHolder().getHouses().get(i);
					}
				}
				if (house != null) {
					if (house.getItem() != null) {
						JOptionPane.showMessageDialog(null, Team.getItemName() + " found!");
						Team team = (Team) b.getBoardArray()[row][col];
						team.setItem(house.getItem());
						house.setItem(null);
						game.getLog().gameLogs("Row " + row + " col " + col
								+ " is the location of the house. The item is found in this house.");
					} else {
						game.getLog().gameLogs("Row " + row + " col " + col
								+ " is the location of a house house; however, the item is not in this house.");
						JOptionPane.showMessageDialog(null, Team.getItemName() + " not found!");

					}
				}
			}
			if (game.getRounds()[game.getRoundIndex()].isComplete()) {
				// adds 50 dollars
				game.getPlayer().setSalary(game.getPlayer().getSalary() + 50);
				game.getGui().getStore().displayUpdatedSalary();
				game.getRounds()[game.getRoundIndex()].clearRound();
				game.getBoard().resetAttackAndMoveBooleans();
				game.getRounds()[game.getRoundIndex()].advanceRound();
				

				game.getLog().gameLogs("Round 1 is complete. ");
			}

		} else {
			statusLabel.setText("MOVE STATUS: DESTINATION TOO FAR! ENTER AGAIN!");
			b.setMoveCompleted(false);
			moveButton.setEnabled(true);
		}

		if (!b.isTeamAttacked()) {
			attackButton.setEnabled(true);
		}

		b.updateBoard();
		b.getCoordinateHolder().reset();
		b.setMoveEnabled(false);
		b.setTeamSelected(false);
		b.setDestinationSelectable(false);
	}

	private void destinationFilledLogic() {
		statusLabel.setText("MOVE STATUS: INVALID! DESTINATION OCCUPIED! ENTER AGAIN!");

		if (!b.isTeamMoved()) {
			attackButton.setEnabled(true);
		}
		if (!b.isTeamAttacked()) {
			moveButton.setEnabled(true);
		}

		b.setMoveCompleted(false);
		b.updateBoard();
		b.setMoveEnabled(false);
		b.getCoordinateHolder().reset();
		b.setTeamSelected(false);
		b.setDestinationSelectable(false);
	}

	/**
	 * Moves the selected Group to the desired row and col.
	 * 
	 * @param g
	 *            The selected group.
	 * @param currentRow
	 *            destination row.
	 * @param currentCol
	 *            destination column.
	 */
	private void moveCommand(Group g, int currentRow, int currentCol) {
		b.getBoardArray()[currentRow][currentCol] = null;

		// assign Group to new location
		b.getBoardArray()[row][col] = g;
		g.updatePosition(row, col);

		// update Group location variables
		g.setRow(row);
		g.setCol(col);
	}

	private void selectAttackingTeam() {
		b.setTeamSelected(true);

		// assign current coordinate values of Team to coordinate holder
		b.getCoordinateHolder().setCurrentRow(row);
		b.getCoordinateHolder().setCurrentCol(col);

		b.getCoordinateHolder().setCurrentGroup(b.getBoardArray()[row][col]);

		// Tell the board that now we're waiting for target selection
		b.setTargetSelectable(true);

		statusLabel.setText("ATTACK STATUS: SELECT A TARGET");
	}

	private void selectAttackCoordinate() {
		b.getCoordinateHolder().setDestinationRow(row);
		b.getCoordinateHolder().setDestinationCol(col);

		int currentRow = b.getCoordinateHolder().getCurrentRow();
		int currentCol = b.getCoordinateHolder().getCurrentCol();

		/* gets the selected team from CoordinateHolder */
		Team team = ((Team) b.getBoardArray()[currentRow][currentCol]);
		ZombieHorde horde = (ZombieHorde) b.getBoardArray()[row][col];

		int range = b.getDistance(currentRow, currentCol, row, col);
		int initialHordeSize = horde.getZombies().size();

		// perform several checks below to determine if the selected attack
		// is valid.

		// check team has ammo first and display message if none present.
		if (team.getAmmoCount() == 0 && !team.hasBat()) {
			// display no bat message if team is a warrior team, else
			// display ordinary no ammo message.
			if (team.isWarriorTeam()) {
				statusLabel.setText("ATTACK STATUS: NO AMMO OR BAT!");
			} else {
				statusLabel.setText("ATTACK STATUS: NO AMMO!");
			}

			// verify team is within firing range. If distance exceeds
			// allowable range, display target too far away message.
		} else if (b.getDistance(currentRow, currentCol, row, col) > team.getRange()) {
			statusLabel.setText("ATTACK STATUS: TARGET TOO FAR AWAY!");

			// check team attacking is not within a house. Display
			// team can't attack from house message if team is currently
			// located in a house.
		} else if (b.getBuildingHolder().isBuilding(currentRow, currentCol)) {
			statusLabel.setText("ATTACK STATUS: CAN'T ATTACK FROM BUILDING!");

			// verify team has line of sight on the target. If not, display
			// a message indicating no sight line is present.
		} else if (!b.hasLineOfSight(currentRow, currentCol, row, col)) {
			statusLabel.setText("ATTACK STATUS: NO SIGHT LINE!");

			// all checks passed at this point. Attack may commence. Proceed
			// to user input and damage calculations.
		} else {
			int numShots = 0;
			int index = 0;
			String numShotsString = "";

			// increment to next teammate as one teammate currently has a
			// bat and can use it.
			if (team.hasBat()) {
				index++;
			}

			// loop through all teammates within the current team. Prompt
			// for the number of shots for the teammate to fired.
			// input validation is performed within the inner loop.
			int stop = Math.min(team.getTotalNumberOfWeapons(), team.getTeammateCount()[team.getType()]);
			while (team.getAmmoCount() > 0 && index < stop) {

				String projectileWeapon = "";
				String batReadied = "BAT READIED!";

				// change string to type of weapon
				if (team.isWarriorTeam()) {
					projectileWeapon = "SHOTGUN";
				} else {
					projectileWeapon = "CROSSBOW";
				}

				// user prompt for number of shots to fire per teammate
				String prompt = "AMMO AVAILABLE: " + team.getAmmoCount() + "\n\nTOTAL SHOTS TO FIRE: " + numShots
						+ "\n\nENTER NUMBER OF SHOTS FOR USABLE " + projectileWeapon + " #" + (index + 1) + " OF "
						+ stop + ".\nMAX SHOTS PER TEAMMATE: " + Team.SHOT_CAP;

				// user input prompt
				if (team.hasBat() && range == Weapon.MAX_RANGE_BASEBALL_BAT) {
					numShotsString = JOptionPane.showInputDialog(batReadied + "\n\n" + prompt);
				} else {
					numShotsString = JOptionPane.showInputDialog(prompt);
				}

				// verify user input was valid. Perform user cancel check,
				// is a number check, has ammo check, and verify
				// entered shot value does not exceed teammate shot limit.
				// Uses break command to exit loop when either cancel value
				// or completely valid number is entered.
				while (true) {

					// break if user enters cancel value during
					// input.
					if (numShotsString == null) {
						break;

						// b.generateSoundOffWeapon((Team)
						// b.getBoard()[row][col], numShots);
						// set the team noise off a weapon being used

						// check input is valid value to be
						// parsed
					} else if (!isValidInput(numShotsString)) {
						numShotsString = JOptionPane
								.showInputDialog("INVALID VALUE (" + numShotsString + "). ENTER NUMBER OF SHOTS:");

						// check input value is within team ammo
						// limit
					} else if (Integer.parseInt(numShotsString) + numShots > team.getAmmoCount()) {
						numShotsString = JOptionPane.showInputDialog("VALUE (" + Integer.parseInt(numShotsString)
								+ ") EXCEEDS TEAM'S REMAINING AMMO CAPACITY (" + team.getAmmoCount()
								+ " SHOTS).\nENTER NUMBER OF SHOTS:");

						// check input value does not exceed
						// teammate shot limit
					} else if (Integer.parseInt(numShotsString) > Team.SHOT_CAP) {
						numShotsString = JOptionPane.showInputDialog(
								"VALUE (" + Integer.parseInt(numShotsString) + ") EXCEEDS MAX SHOT LIMIT OF "
										+ Team.SHOT_CAP + " PER TEAMMATE.\nENTER NUMBER OF SHOTS:");

						// all checks passed, break out of loop
						// to parse value for damage
						// calculation.
					} else {
						break;
					}
				}

				// break outer loop if null cancel value is entered.
				if (numShotsString != null) {
					numShots += Integer.parseInt(numShotsString);
					index++;
				} else {
					break;
				}
			}

			// do attack if user has not entered a cancel value
			// of null from input prompt.
			if (numShotsString != null) {

				// calculate damage value to be used in attack
				Damage damg = new Damage();
				int roll = b.getDie().roll();
				int damage = damg.calculateDamage(team, numShots, range, roll);

				// display message if damage value is zero, else continue as
				// normal with damage application.
				if (damage > 0) {

					// apply damage to the target horde. If the method
					// returns true, the horde is removed from the board, as
					// all zombies in the horde have been killed. If method
					// returns false, damage is still applied but horde
					// remains on board.
					if (damg.applyDamage((ZombieHorde) horde, damage)) {
						// log for defeating a horde of zombies
						game.getLog().gameLogs(
								"Team at row " + currentRow + " col " + currentCol + "attacked a zomombue horde at row "
										+ horde.getRow() + " col " + horde.getCol() + ". The horde was eliminated");

						b.getBoardArray()[horde.getRow()][horde.getCol()] = null;
						game.getLogic().removeHorde(horde);

						statusLabel.setText("ATTACK STATUS: TARGET DESTROYED!");

					} else {
						// log when a zombie is attacked but not killed.
						game.getLog().gameLogs("Team at row " + currentRow + " col " + currentCol
								+ " attacked a zombie horde at row " + horde.getRow() + " col " + horde.getCol() + ".");
						statusLabel.setText("ATTACK STATUS: TARGET HIT!");
					}

					// remove ammo used in attack
					team.setAmmoCount(team.getAmmoCount() - numShots);

					// display damage message for warrior team attacking
					// with bat, else display normal damage message
					// applicable to either team type.
					if (team.getType() == Team.TYPE_WARRIOR && team.getWeaponArray()[Weapon.BASEBALL_BAT] >= 1
							&& range == Weapon.MAX_RANGE_BASEBALL_BAT) {
						JOptionPane.showMessageDialog(null,
								"ROLL: " + roll + "\n" + Weapon.BASE_DAMAGE_BAT * roll + " BAT DAMAGE!\n" + numShots
										+ " SHOT(S) FIRED!\n\n" + damage + " TOTAL DAMAGE!\n\n"
										+ (initialHordeSize - horde.getZombies().size()) + " ZOMBIE(S) KILLED!\n"
										+ horde.getZombies().size() + " ZOMBIE(S) REMAIN IN HORDE!");
						game.getLog().gameLogs(initialHordeSize - horde.getZombies().size() + " zombie(s) killed at row "
								+ horde.getRow() + "col " + horde.getCol() + ".");
					} else {
						JOptionPane.showMessageDialog(null,
								numShots + " SHOT(S) FIRED!\n\n" + damage + " TOTAL DAMAGE!\n\n"
										+ (initialHordeSize - horde.getZombies().size()) + " ZOMBIE(S) KILLED!\n"
										+ horde.getZombies().size() + " ZOMBIE(S) REMAIN IN HORDE!");
						game.getLog().gameLogs(initialHordeSize - horde.getZombies().size() + " zombie(s) killed at row "
								+ horde.getRow() + " col " + horde.getCol() + ".");
					}

					// generates the sound a weapon makes

					game.getLogic().checkWeaponNoiseRadius(team);


					// initiate dank memes
					if (horde.getZombies().size() == 0) {
						JOptionPane.showMessageDialog(null, null, "Horde Eliminated!", JOptionPane.INFORMATION_MESSAGE,
								GUI.getRekt());
					}

					// team has performed attack
					b.setTeamAttacked(true);
					// disable attack button
					attackButton.setEnabled(false);

				} else {
					statusLabel.setText("ATTACK STATUS: NO DAMAGE!");
				}
			} else {
				statusLabel.setText("ATTACK STATUS: ATTACK CANCELLED!");
			}
		}

		// Reset
		b.setAttackEnabled(false);
		b.setTargetSelectable(false);

		if (!b.isTeamMoved()) {
			moveButton.setEnabled(true);
		}

		if (!b.isTeamAttacked()) {
			attackButton.setEnabled(true);
		}

		b.updateBoard();
		b.getCoordinateHolder().reset();
		b.setTeamSelected(false);
		b.setDestinationSelectable(false);
	}

	private void attackCoordNotZombie() {
		statusLabel.setText("ATTACK STATUS: INVALID! NOT A ZOMBIE!");

		if (!b.isTeamMoved()) {
			moveButton.setEnabled(true);
		}

		if (!b.isTeamAttacked()) {
			attackButton.setEnabled(true);
		}

		b.updateBoard();
		b.getCoordinateHolder().reset();
		b.setTeamSelected(false);
		b.setTargetSelectable(false);
		b.setAttackEnabled(false);
	}

	private boolean isValidInput(String stringNum) {
		if (stringNum != null && !stringNum.isEmpty()) {
			char[] characters = stringNum.toCharArray();

			for (int i = 0; i < characters.length; i++) {
				if (!Character.isDigit(characters[i])) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public Game getGame() {
		return game;
	}

	public void setRound(Game g) {
		this.game = g;
	}

}
