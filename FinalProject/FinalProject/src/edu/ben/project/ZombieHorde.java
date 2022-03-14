package edu.ben.project;

import java.util.ArrayList;

/**
 * Represents a single horde of zombies. Zombies are kept track of as a single
 * unit with this class. Uses an ArrayList of type Zombie to store individual
 * Zombie objects.
 * 
 * @author Colom Boyle, Steve Schultz
 *
 */
public class ZombieHorde extends Group {

	public static final int INITIAL_ZOMBIE_COUNT = 10;

	private ArrayList<Zombie> zombies = new ArrayList<>();
	private Integer[] attackTarget;
	private Integer[] soundTarget;
	private boolean isAttackPerformed;

	/**
	 * Constructor. Creates a new ZombieHorde at specified coordinates with 10
	 * zombies added to priority queue.
	 * 
	 * @param row
	 *            Starting row.
	 * @param col
	 *            Starting column.
	 */
	public ZombieHorde(int row, int col) {
		setRow(row);
		setCol(col);
		for (int i = 0; i < INITIAL_ZOMBIE_COUNT; i++) {
			zombies.add(new Zombie());
		}
		isAttackPerformed = false;
	}

	/**
	 * Returns the arraylist containing individual zombies.
	 * 
	 * @return Returns an arraylist with Zombie objects.eee
	 */
	public ArrayList<Zombie> getZombies() {
		return zombies;
	}

	/**
	 * Returns an Integer array containing the horde's destination target.
	 * 
	 * @return An Integer array with row and column values of the horde's target
	 *         destination.
	 */
	public Integer[] getAttackTarget() {
		return attackTarget;
	}

	/**
	 * Sets a new attack target destination. Array may be null.
	 * 
	 * @param attackTarget
	 *            A new attack destination coordinate.
	 */
	public void setAttackTarget(Integer[] attackTarget) {
		this.attackTarget = attackTarget;
	}

	/**
	 * Returns an Integer array containing the horde's sound target destination.
	 * 
	 * @return An Integer array with row and column values of the horde's sound
	 *         target
	 * 
	 */
	public Integer[] getSoundTarget() {
		return soundTarget;
	}

	/**
	 * Sets a new sound target. Array may be null.
	 * 
	 * @param soundTarget
	 *            A new sound target coordinate.
	 */
	public void setSoundTarget(Integer[] soundTarget) {
		this.soundTarget = soundTarget;
	}

	/**
	 * Determines if the passed in object is equal.
	 * 
	 * @param An
	 *            Object to be compared.
	 * 
	 * @return Returns true if passed in object is equal, else false.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ZombieHorde) {
			ZombieHorde zh = (ZombieHorde) o;
			// check row and col values are the same
			if (!(getRow() == zh.getRow() && getCol() == zh.getCol())) {
				return false;
				// check number of zombies is the same
			} else if (zombies.size() != zh.getZombies().size()) {
				return false;
				// if number of zombies is the same, check each zombie's health
			} else if (zombies.size() == zh.getZombies().size()) {
				for (int i = 0; i < zombies.size(); i++) {
					// return false if health values do not match
					if (zombies.get(i).getHealth() != zh.getZombies().get(i).getHealth()) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Boolean for horde has attacked status.
	 * 
	 * @return Returns true for horde has attacked, else false.
	 */
	public boolean isAttackPerformed() {
		return isAttackPerformed;
	}

	/**
	 * Sets new value of hasAttacked boolean.
	 * 
	 * @param hasAttacked
	 *            A new value for hasAttacked.
	 */
	public void setAttackPerformed(boolean hasAttacked) {
		this.isAttackPerformed = hasAttacked;
	}

	/**
	 * Returns a formatted String representing the current state of the horde.
	 * 
	 * @return The formatted String.
	 */
	@Override
	public String toString() {
		return "<html>ZOMBIE HORDE<br>ROW: " + getRow() + "<br>COL: " + getCol() + "<br>ZOMBIE COUNT: " + zombies.size()
				+ "<br>ZOMBIE HEALTH:<br>" + zombies.toString() + "</html>";
	}

}
