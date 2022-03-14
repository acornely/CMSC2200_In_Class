package edu.ben.project;

import java.util.Random;

/**
 * Contains methods to simulate die rolls. Die can be rolled to generate values
 * from 1 to 6 (both inclusive) or 1 to 3 (both inclusive).
 * 
 * Whenever the die is rolled, the value is stored as a current roll, which can
 * be retrieved as needed.
 * 
 * @author Colom Boyle
 * @version 1.2
 * 
 */
public class Die {

	private Random r = new Random();
	private static final int MAX_SIDES = 6;
	private int currentRoll;

	/**
	 * Constructor. Rolls the die and sets the current roll to the initial roll
	 * value.
	 */
	public Die() {
		currentRoll = roll();
	}

	/**
	 * Rolls the die, generating a number from 1 to 6 (both inclusive).
	 * 
	 * @return An int value randomly chosen from 1 to 6 (both inclusive).
	 */
	public int roll() {
		currentRoll = r.nextInt(MAX_SIDES) + 1;
		return currentRoll;
	}

	/**
	 * Rolls the die, generating a number from 1 to 3 (both inclusive).
	 * 
	 * @return An int value randomly chosen between 1 to 3 (both inclusive).
	 */
	public int halfRoll() {
		currentRoll = r.nextInt(MAX_SIDES / 2) + 1;
		return currentRoll;
	}

	/**
	 * Getter method for current roll value.
	 * 
	 * @return Returns the latest roll value as an int.
	 */
	public int getCurrentRoll() {
		return currentRoll;
	}

}
