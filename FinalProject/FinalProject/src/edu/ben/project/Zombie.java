package edu.ben.project;

/**
 * Represents an individual zombie in a ZombieHorde. Each Zombie has its own
 * health value, which starts at the base health value.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class Zombie implements Comparable<Object> {

	public static final int BASE_HEALTH = 16;
	private int health;

	/**
	 * Creates a new Zombie with health set to the base health value.
	 */
	public Zombie() {
		setHealth(BASE_HEALTH);
	}

	/**
	 * Getter for health value.
	 * 
	 * @return Returns current health value.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Setter for health value.
	 * 
	 * @param health
	 *            The new value to use for zombie health.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Creates a string displaying the zombie's current health.
	 * 
	 * @return A String with the current health value.
	 */
	@Override
	public String toString() {
		return Integer.toString(health);
	}

	/**
	 * Determines if passed in object is equal.
	 * 
	 * @param o
	 *            An object to be compared.
	 * 
	 * @return Returns true if passed in object is equal, else false.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Zombie) {
			Zombie zombie = (Zombie) o;
			return health == zombie.getHealth();
		} else {
			return false;
		}
	}

	/**
	 * Allows for Zombies to be sorted by health value.
	 * 
	 * @param o
	 *            The object to be compared to.
	 */
	@Override
	public int compareTo(Object o) {
		Zombie z = (Zombie) o;
		return health - z.getHealth();
	}

}
