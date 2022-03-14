package edu.ben.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * 
 * Contains logic necessary for zombies in game.
 * 
 * @author Adrian Cornely, Colom Boyle
 *
 */
public class ZombieLogic {
	private ArrayList<ZombieHorde> zombieList;
	private Board b;
	public Game g;

	/**
	 * Constructor. Creates horde arraylist and assigns Board parameter.
	 * 
	 * @param b
	 *            A Board object.
	 */
	public ZombieLogic(Game game) {
		zombieList = new ArrayList<ZombieHorde>();

		this.b = game.getBoard();
		this.g = game;

		this.b = game.getBoard();
		g = game;

	}

	/**
	 * Getter for zombie horde arraylist.
	 * 
	 * @return Returns the zombie horde arraylist.
	 */
	public ArrayList<ZombieHorde> getZombieList() {
		return zombieList;
	}

	/**
	 * Setter for zombie horde arraylist.
	 * 
	 * @param zombieList
	 *            A new zombie horde arraylist.
	 */
	public void setZombieList(ArrayList<ZombieHorde> zombieList) {
		this.zombieList = zombieList;
	}

	/**
	 * Removes the passed in horde from the horde arraylist.
	 * 
	 * @param horde
	 *            The horde to be removed from horde arraylist.
	 */
	public void removeHorde(ZombieHorde horde) {
		if (zombieList != null) {
			zombieList.remove(horde);
		}
	}

	public boolean findTargets() {

		for (int i = 0; i < zombieList.size(); i++) {
			ZombieHorde z = zombieList.get(i);
			Integer[] target = new Integer[2];
			int x = z.getCol();
			int y = z.getRow();
			int twelve = y - 1;
			int three = x + 1;
			int six = y + 1;
			int nine = x - 1;
			// need to check 12, 3, 6, 9
			if (b.isInsideBounds(twelve, x) && !b.getBuildingHolder().isBuilding(twelve, x) && isTeam(twelve, x)) {
				target[0] = twelve;
				target[1] = x;
				z.setAttackTarget(target);
				return true;
			} else if (b.isInsideBounds(y, three) && !b.getBuildingHolder().isBuilding(y, three) && isTeam(y, three)) {
				target[0] = y;
				target[1] = three;
				z.setAttackTarget(target);
				return true;
			} else if (b.isInsideBounds(six, x) && !b.getBuildingHolder().isBuilding(six, x) && isTeam(six, x)) {
				target[0] = six;
				target[1] = x;
				z.setAttackTarget(target);
				return true;
			} else if (b.isInsideBounds(y, nine) && !b.getBuildingHolder().isBuilding(y, nine) && isTeam(y, nine)) {
				target[0] = y;
				target[1] = nine;
				z.setAttackTarget(target);
				return true;
			} else {
				z.setAttackTarget(null);

			}

		}

		return false;

	}

	/**
	 * determines whether a given coordinate contains a team for purposes of
	 * zombie target validation
	 * 
	 * @param xDest
	 *            x coord to check
	 * @param yDest
	 *            y coord to check
	 * @return true if team, false else
	 */
	public boolean isTeam(int xDest, int yDest) {
		if (b.getBoardArray()[xDest][yDest] instanceof Team) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Merges second zombie horde with first horde. Transfers all zombies over
	 * to first hoard, then sets value of second horde to null.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param zh1
	 *            The horde to receive merged zombies.
	 * @param zh2
	 *            The horde to be added to first horde. This horde is removed
	 *            from board after merge.
	 */
	public void merge(ZombieHorde zh1, ZombieHorde zh2) {
		Group[][] board = b.getBoardArray();
		ArrayList<Zombie> zombies = zh2.getZombies();
		// move all zombies from zh2 to zh1
		while (!zombies.isEmpty()) {
			zh1.getZombies().add(zombies.remove(0));
		}
		// sort first horde array in ascending health order
		Collections.sort(zh1.getZombies());
		// remove zh2 from board after merging
		board[zh2.getRow()][zh2.getCol()] = null;
		zombieList.remove(zh2);
	}

	/**
	 * Randomly moves all hordes that do not currently have a target. ZombieList
	 * is looped through and any zombies without a target are assigned to a new
	 * random position as defined by the randomMove() method.
	 * 
	 * @author Colom Boyle
	 */
	public void randomMoveAllHordesNoSoundTargets() {
		// loop through horde list
		for (int i = 0; i < zombieList.size(); i++) {
			// verify selected zombie has no target
			if (zombieList.get(i).getAttackTarget() == null && zombieList.get(i).getSoundTarget() == null) {
				randomMove(zombieList.get(i));
			}
		}
	}

	/**
	 * Randomly moves an individual zombie horde if a move is possible.
	 * Movements are constrained to the zombie horde movement limit. A
	 * successful move will return true, else false.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param horde
	 *            ZombieHorde to randomly move.
	 * @return Returns true if the move was successful, else return false, such
	 *         as in the case of a move not being possible.
	 */
	public boolean randomMove(ZombieHorde horde) {
		Random r = new Random();

		int startRow = horde.getRow();
		int startCol = horde.getCol();
		int moveRow;
		int moveCol;
		ArrayList<Node> potentialMoves = new ArrayList<>();

		// loop through potential moves
		for (int i = startRow - 3; i < startRow + 3; i++) {
			for (int j = startCol - 3; j < startCol + 3; j++) {
				// verify coordinates are inside bounds, within zombie
				// distance limit, and is not a building, before generating path
				if (b.isInsideBounds(i, j) && !(b.getBoardArray()[i][j] instanceof Team)
						&& b.getDistance(startRow, startCol, i, j) <= 3 && !b.getBuildingHolder().isBuilding(i, j)) {
					// generate path to destination
					Stack<Node> zombiePath = b.getZombiePath(startRow, startCol, i, j);
					// add path to potential moves if generated path is
					// within zombie move distance limit
					if (zombiePath != null && zombiePath.size() <= 3) {
						potentialMoves.add(new Node(i, j));
					}
				}
			}
		}

		// choose random move if potential move has moves, else return false for
		// no move possible
		if (potentialMoves.size() > 0) {
			Node moveCoord = potentialMoves.get(r.nextInt(potentialMoves.size()));
			moveRow = moveCoord.getRow();
			moveCol = moveCoord.getCol();

			// perform merge if horde found at destination
			if (b.getBoardArray()[moveRow][moveCol] instanceof ZombieHorde) {
				merge((ZombieHorde) b.getBoardArray()[moveRow][moveCol], horde);
				return true;
			}

			// move horde
			b.getBoardArray()[moveRow][moveCol] = horde;
			b.getBoardArray()[horde.getRow()][horde.getCol()] = null;
			horde.updatePosition(moveRow, moveCol);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks to see if a zombie is in the range of the movement or weapon
	 * noise, then it sets the zombie's move destination.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param row
	 *            radius row.
	 * @param col
	 *            radius col.
	 * @param noiseRadius
	 *            noise radius value.
	 */
	public void scanForNoiseRadius(int row, int col, int noiseRadius) {
		for (int i = row - noiseRadius + 1; i < (row - noiseRadius) + noiseRadius * 2; i++) {
			for (int j = col - noiseRadius + 1; j < (col - noiseRadius) + noiseRadius * 2; j++) {
				// do check if row and col inside array bounds, noise radius,
				// and horde found at location
				if (b.isInsideBounds(i, j) && b.getDistance(row, col, i, j) < noiseRadius
						&& b.getBoardArray()[i][j] instanceof ZombieHorde) {

					Integer[] destination = { row, col };
					ZombieHorde hordeInPursuit = (ZombieHorde) b.getBoardArray()[i][j];
					hordeInPursuit.setSoundTarget(destination);
				}
			}
		}

	}

	/**
	 * 
	 * @param die
	 * @param row
	 * @param col
	 * @param currentHorde
	 */
	public void zombieSoundPursuit(Die die, int row, int col, ZombieHorde currentHorde) {
		if (currentHorde.getZombies().size() > 0) {
			Stack<Node> zombiePath = b.getZombiePath(currentHorde.getRow(), currentHorde.getCol(), row, col);
			// if path possible
			if (zombiePath != null) {
				int moves = 0;
				int roll = die.roll();
				Node node = null;
				// pop path nodes off until destination reached or roll move
				// limit reached
				while (!zombiePath.isEmpty() && moves < roll) {
					node = zombiePath.pop();
					moves++;
				}
				// move horde to destination, merge if another horde exists at
				// destination.
				if (node != null && b.getBoardArray()[node.getRow()][node.getCol()] instanceof ZombieHorde) {
					merge((ZombieHorde) b.getBoardArray()[node.getRow()][node.getCol()], currentHorde);
				} else if (node != null) {
					b.getBoardArray()[node.getRow()][node.getCol()] = currentHorde;
					b.getBoardArray()[currentHorde.getRow()][currentHorde.getCol()] = null;
					currentHorde.updatePosition(node.getRow(), node.getCol());
				}
			}
		}
	}

	/**
	 * Creates ten randomly placed zombie hordes across the board. Uses random
	 * number generation for each row and column coordinate.
	 * 
	 * Does not place zombies in buildings.
	 * 
	 * For Round 2, places all zombies surrounding house with radio
	 * 
	 * @author Colom Boyle
	 */
	public void initializeZombieHordes() {
		if (g.getRoundIndex() != 1) {
		Random r = new Random();
		ArrayList<ZombieHorde> newList = new ArrayList<ZombieHorde>();
		// create hordes at random locations that are not buildings.
		for (int i = 0; i < b.NUM_INITIAL_HORDES; i++) {
			int row = r.nextInt(b.NUM_ROWS);
			int col = r.nextInt(b.NUM_COLS);

			// only allow hordes on squares that do not have an existing unit
			// and do not contain a building.
			while (b.getBoardArray()[row][col] != null || b.getBuildingHolder().isBuilding(row, col)) {
				row = r.nextInt(b.NUM_ROWS);
				col = r.nextInt(b.NUM_COLS);
			}

			ZombieHorde z = new ZombieHorde(row, col);
			b.getBoardArray()[row][col] = z;
			newList.add(z);
			zombieList = newList;
		}
		} else {
			ArrayList<ZombieHorde> newList = new ArrayList<ZombieHorde>();
			int row = 0;
			int col = 0;
			Building house = null;
			for (int i = 0; i < 4; i++ ) {
				if (b.getBuildingHolder().getHouses().get(i).getItem() != null) {
					row = b.getBuildingHolder().getHouses().get(i).getRow();
					col = b.getBuildingHolder().getHouses().get(i).getCol();
					house = b.getBuildingHolder().getHouses().get(i);
					break;
				}
				
			}
			ZombieHorde one = new ZombieHorde(row - 2, col);
			b.getBoardArray()[row - 2][col] = one;
			ZombieHorde two = new ZombieHorde(row - 1, col-1);
			b.getBoardArray()[row - 1][col-1] = two;
			ZombieHorde three = new ZombieHorde(row - 1, col);
			b.getBoardArray()[row - 1][col] = three;
			ZombieHorde four = new ZombieHorde(row - 1, col +1);
			b.getBoardArray()[row - 1][col + 1] = four;
			ZombieHorde five = new ZombieHorde(row, col-1);
			b.getBoardArray()[row][col-1] = five;
			ZombieHorde six = new ZombieHorde(row, col+1);
			b.getBoardArray()[row][col+1] = six;
			ZombieHorde seven = new ZombieHorde(row + 1, col-1);
			b.getBoardArray()[row +1][col-1] = seven;
			ZombieHorde eight = new ZombieHorde(row +1, col);
			b.getBoardArray()[row +1][col] = eight;
			ZombieHorde nine = new ZombieHorde(row +1, col+1);
			b.getBoardArray()[row +1][col+1] = nine;
			newList.add(one);
			newList.add(two);
			newList.add(three);
			newList.add(four);
			newList.add(five);
			newList.add(six);
			newList.add(seven);
			newList.add(eight);
			newList.add(nine);
			if(house.getCol() != 10) {
				ZombieHorde ten = new ZombieHorde(row + 2, col);
				b.getBoardArray()[row + 2][col] = ten;
				b.updateBoard();
				newList.add(ten);
			} else {
				ZombieHorde ten = new ZombieHorde(row - 2, col-1);
				b.getBoardArray()[row-2][col+1] = ten;
				b.updateBoard();
				newList.add(ten);
			}
			zombieList = newList;
			} 
		
	}

/**
 * Logic for zombie turn
 */
	public void zombieTurn() {
		// if targets found, attack and move, else move and attack
		if (findTargets()) {
			// perform attack
			attack();
			// move all hordes that have a sound target to their sound
			// destination
			moveHordesWithSoundTargets();
			// move all hordes that do not have a sound target randomly
			randomMoveAllHordesNoSoundTargets();
		} else {
			// move all hordes that have a sound target to their sound
			moveHordesWithSoundTargets();
			// move all hordes that do not have a sound target randomly
			randomMoveAllHordesNoSoundTargets();

			// check for adjacent targets, then attack if targets found
			if (findTargets()) {
				attack();
			}
		}

		// set sound targets to null
		resetSoundTargets();
		// set attack status booleans to false
		resetAttackPerformedStatusAllZombies();

		if (g.getRounds()[g.getRoundIndex()].isRoundLost()) {
			JOptionPane.showMessageDialog(null, "Round failed, game over!");
			System.exit(0);
		}
	}
/**
 * Zombies attack team if available
 */
	public void attack() {
		Integer[] target = null;
		int numZombies = 0;
		ZombieHorde attacker = null;

		A: for (int i = 0; i < zombieList.size(); i++) {
			ZombieHorde z = zombieList.get(i);
			if (z.getAttackTarget() != null) {
				target = z.getAttackTarget();
				numZombies = z.getZombies().size();
				attacker = z;
				break A;
			}
		}
		if (target != null && numZombies != 0) {
			Group[][] board = b.getBoardArray();
			Die die = new Die();

			Team targetTeam = (Team) board[attacker.getAttackTarget()[0]][attacker.getAttackTarget()[1]];

			int computerRoll = die.roll();
			int playerRoll = die.roll();

			// calculate damage and defense values
			int zombieDamage = (int) Math.round(((double) attacker.getZombies().size() * (double) computerRoll) / 4);
			int targetDefense = targetTeam.getSizeOfTeam() * playerRoll;

			int unitsKilled = zombieDamage - targetDefense;

			// generate output string
			String result = "COMPUTER ROLL: " + computerRoll + "\nPLAYER ROLL: " + playerRoll + "\n\nZOMBIE DAMAGE: "
					+ zombieDamage + "\nPLAYER DEFENSE: " + targetDefense + "\n";

			if (targetDefense < zombieDamage && targetTeam.getSizeOfTeam() > unitsKilled) {

				int[] teamList = targetTeam.getTeammateCount();
				teamList[targetTeam.getType()] = teamList[targetTeam.getType()] - unitsKilled;

				JOptionPane.showMessageDialog(null, result + "\n" + unitsKilled + " TEAM MEMBERS KILLED FROM TEAM AT ("
						+ targetTeam.getRow() + "," + targetTeam.getCol() + ")!");
				return;
			} else if (targetDefense < zombieDamage) {

				b.getBoardArray()[targetTeam.getRow()][targetTeam.getCol()] = null;

				if (targetTeam.getItem() != null) {
					Random r = new Random();

					Building b = this.getBoard().getBuildingHolder().getHouses().get(r.nextInt(4));
					b.setItem(targetTeam.getItem());
					b.getItem().setRow(b.getRow());
					b.getItem().setCol(b.getCol());
				}
				b.updateBoard();
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream audioInputStream;
					audioInputStream = AudioSystem.getAudioInputStream(new File("music/taps.wav"));
					clip.open(audioInputStream);
					clip.start();
					JOptionPane.showMessageDialog(null,
							"\nTEAM ANNIHLIATED AT (" + targetTeam.getRow() + "," + targetTeam.getCol() + ")!");
					clip.stop();

					
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
				// weapons are stored locally by team, so weapons disappear when
				// team does
			} else {

				// add neo gif
				b.updateBoard();
				JOptionPane.showMessageDialog(null, result + "\nFEELING LUCKY, PUNK? NO MEMBERS KILLED AT ("
						+ targetTeam.getRow() + "," + targetTeam.getCol() + ")");

				return;
			}

		} else {
			return;
		}
	}

	/**
	 * Getter for Board object.
	 * 
	 * @return The Board object reference.
	 */
	public Board getBoard() {
		return b;
	}

	/**
	 * Calculates a noise radius for every node the team moves across the passed
	 * in path. Runs through path stack and sets the target destination of any
	 * zombies found in the radius to the node's coordinates.
	 * 
	 * @param path
	 *            A path to be examined.
	 * @param t
	 *            A moving Team.
	 * 
	 * @author Colom Boyle
	 */
	public void checkMoveNoiseRadius(Stack<Node> path, Team t) {
		// check path is possible and team is not null
		if (path != null && t != null) {
			// generate sound radius for movement
			int radius = t.getTeamNoise();

			Node node = null;
			// check sound radius of each node in path for zombies and assign
			// node location as target
			while (!path.isEmpty()) {
				node = path.pop();
				// perform radius check for zombies
				scanForNoiseRadius(node.getRow(), node.getCol(), radius);
			}
		}
	}

	/**
	 * Checks weapon noise radius and assigns the position of the passed in team
	 * as the target destination of any hordes found within the radius.
	 * 
	 * 
	 * @param t
	 *            An attacking Team.
	 * 
	 * @author Colom Boyle
	 */
	public void checkWeaponNoiseRadius(Team t) {
		// check team not null
		if (t != null) {
			// get weapon noise radius based on team's current weapon and ammo
			// status
			int weaponNoiseRadius = t.generateSoundOffWeapon();
			// search for hordes within radius and assign target destination as
			// team location.
			scanForNoiseRadius(t.getRow(), t.getCol(), weaponNoiseRadius);
		}
	}

	/**
	 * Moves all hordes that currently have a target destination to their target
	 * destination.
	 * 
	 * @author Colom Boyle
	 */
	public void moveHordesWithSoundTargets() {
		Die die = new Die();
		// loop through horde list
		for (int i = 0; i < zombieList.size(); i++) {
			// if horde has a target
			if (zombieList.get(i).getSoundTarget() != null && !zombieList.get(i).isAttackPerformed()) {
				// create target
				Integer[] zombieCoordinates = zombieList.get(i).getSoundTarget();
				// move horde to destination
				zombieSoundPursuit(die, zombieCoordinates[0], zombieCoordinates[1], zombieList.get(i));
			}
		}
	}

	/**
	 * Sets the sound targets of all hordes to null.
	 */
	public void resetSoundTargets() {
		for (int i = 0; i < zombieList.size(); i++) {
			zombieList.get(i).setSoundTarget(null);
		}
	}

	/**
	 * Sets the attack status booleans of all hordes to false;
	 */
	public void resetAttackPerformedStatusAllZombies() {
		for (int i = 0; i < zombieList.size(); i++) {
			zombieList.get(i).setAttackPerformed(false);
		}
	}

	/**
	 * Removes hordes in main zombieList array based on hordes passed in through
	 * parameter.
	 * 
	 * @param hordesKilled
	 *            The hordes to be removed from zombieList arraylist.
	 */
	public void removeKilledHordes(ArrayList<ZombieHorde> hordesKilled) {
		if (hordesKilled != null) {
			for (int i = 0; i < hordesKilled.size(); i++) {
				zombieList.remove(hordesKilled.get(i));
			}
		}
	}

	public Board getB() {
		return b;
	}

	public void setB(Board b) {
		this.b = b;
	}

}
