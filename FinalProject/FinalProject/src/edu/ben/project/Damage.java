package edu.ben.project;

/**
 * Provides methods for damage usage and calculations. A Team damage calculation
 * may be performed with the calculateDamage method. This will return the amount
 * of damage a Team may inflict based on the passed in parameters. The
 * applyDamage method allows for a damage value to be applied to a ZombieHorde.
 * The damage is applied individually to each zombie within the horde, removing
 * zombies that have their health set to zero.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class Damage {

	/**
	 * Used for Team attack damage calculation. Calculates damage based on type
	 * of team (uses respective team's weapon type).
	 * 
	 * It is important to note that this method functions only as a calculation,
	 * and does not check if the entered data is within the game's rules.
	 * 
	 * Always includes bat in calculation for warrior teams if the team has a
	 * bat and the range is within the bat's range limit.
	 * 
	 * NOTE: ENTERED VALUES MUST BE VALID FOR CORRECT RESULTS! DOES NOT PERFORM
	 * INPUT CHECKS ASIDE FROM VERIFYING WHETHER OR NOT THE PASSED IN TEAM HAS A
	 * BAT!
	 * 
	 * @param team
	 *            An attacking Team. Used to obtain weapon type for calculations
	 *            and is checked to see if the team has a bat.
	 * @param numShots
	 *            The number of shots to be fired. Shot count is not checked to
	 *            be within the game's valid shot limit; proper input validation
	 *            must be performed first.
	 * @param range
	 *            The range to the target. Used to determine if a bat is to be
	 *            included in calculations, assuming a warrior team is entered
	 *            and has a bat.
	 * @param roll
	 *            A die roll. Used as a multiplier for the bat damage.
	 *
	 * @return Returns an int value representing the total amount of damage the
	 *         passed in team will inflict. Damage is based on team type
	 *         (affects weapon type), range (affects bat usage), die roll (if
	 *         applicable), and the number of shots to be fired.
	 */
	public int calculateDamage(Team team, int numShots, int range, int roll) {

		// check whether team is type warrior or type stealth before performing
		// calculations.
		if (team.getType() == Team.TYPE_WARRIOR) {

			// obtain the number of bats the team is carrying
			int bats = team.getWeaponArray()[Weapon.BASEBALL_BAT];

			// calculation for target one square away and bat available.
			if (range <= Weapon.MAX_RANGE_BASEBALL_BAT && bats >= 1) {

				// multiply bat damage times die roll, shotgun damage times
				// the number of shots, then return the sum of the two values.
				return Weapon.BASE_DAMAGE_BAT * roll + Weapon.BASE_DAMAGE_SHOTGUN * numShots;

				// Calculation assuming only shotguns usable
			} else {

				// return shotgun damage times the number of shots to be fired.
				return Weapon.BASE_DAMAGE_SHOTGUN * numShots;
			}

			// stealth team calculation
		} else {

			// return crossbow damage times the number of shots to be fired.
			return Weapon.BASE_DAMAGE_CROSSBOW * numShots;

		}

	}

	/**
	 * Applies the passed in damage to a zombie horde. Zombies are removed from
	 * the horde if the damage they receive is greater than or equal to their
	 * current health. Returns true if the entire horde is destroyed, else
	 * false.
	 * 
	 * Damage is applied to each zombie one at a time. The zombie's health is
	 * adjusted and remaining damage is calculated. Any remaining damage will be
	 * carried over to the next zombie in the horde. This process repeats until
	 * all remaining damage has been used up or all zombies in the horde are
	 * killed.
	 * 
	 * This method uses a boolean value to determine if all zombies in the horde
	 * have been killed; damage is applied regardless of return value.
	 * 
	 * @param horde
	 *            The horde to receive damage.
	 * @param damage
	 *            The damage value to be used in calculation.
	 * 
	 * @return Uses boolean value to determine whether the horde has been
	 *         completely destroyed by the applied damage. Returns true if
	 *         damage kills all current zombies in horde, else returns false for
	 *         a horde that still has remaining zombies after damage has been
	 *         applied.
	 */
	public boolean applyDamage(ZombieHorde horde, int damage) {
		int index = 0;

		// loop while horde still has zombies in it and damage to be applied is
		// greater than zero.
		while (horde.getZombies().size() > 0 && damage > 0 && index < horde.getZombies().size()) {

			int health = horde.getZombies().get(index).getHealth();

			// if damage application would kill the zombie, remove it from the
			// horde zombie list, else lower its health by the damage value.
			if (health - damage <= 0) {
				horde.getZombies().remove(index);
			} else {
				horde.getZombies().get(index).setHealth(health - damage);
				index++;
			}

			// decrease damage value by the amount used to lower the health of
			// the current zombie.
			damage -= health;
		}
		// return true if all zombies killed, else false.
		return horde.getZombies().size() == 0;
	}

}
