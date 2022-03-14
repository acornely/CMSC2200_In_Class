package edu.ben.project;

/**
 * Represents a weapon. Types may be assigned to bat, crossbow, or shotgun.
 * Holds type, damage, and range values.
 * 
 *
 */
public class Weapon {

	// weapon type values
	public static final int BASEBALL_BAT = 0;
	public static final int CROSSBOW = 1;
	public static final int SHOTGUN = 2;

	// weapon range values
	public static final int MAX_RANGE_BASEBALL_BAT = 1;
	public static final int MAX_RANGE_CROSSBOW = 5;
	public static final int MAX_RANGE_SHOTGUN = 3;

	// weapon base damage values
	public static final int BASE_DAMAGE_BAT = 16;
	public static final int BASE_DAMAGE_SHOTGUN = 16;
	public static final int BASE_DAMAGE_CROSSBOW = 8;

	public static final int PACK_OF_ARROWS = 0;
	public static final int SET_OF_BULLETS = 1;

	// weapon noise
	public static final int BAT_NOISE = 4;
	public static final int SHOTGUN_NOISE = 7;
	public static final int CROSSBOW_NOISE = 3;

	private int type;
	private int damage;
	private int range;
	// private int weaponNoise;

	/**
	 * Constructor. The desired weapon type is passed in, stats are then
	 * assigned based on the type.
	 * 
	 * @param type
	 *            Use Weapon.BASEBALL_BAT, Weapon.CROSSBOW, or Weapon.SHOTGUN.
	 */
	public Weapon(int type) {
		// set bat stats
		if (type == BASEBALL_BAT) {
			this.type = type;
			range = MAX_RANGE_BASEBALL_BAT;
			// damage = BASE_DAMAGE_BAT;

			// set crossbow stats
		} else if (type == CROSSBOW) {
			this.type = type;
			range = MAX_RANGE_CROSSBOW;
			damage = BASE_DAMAGE_CROSSBOW;
			// weaponNoise = CROSSBOW_NOISE;

			// set shotgun stats
		} else if (type == SHOTGUN) {
			this.type = type;
			range = MAX_RANGE_SHOTGUN;
			damage = BASE_DAMAGE_SHOTGUN;
			// weaponNoise = SHOTGUN_NOISE;
		}
	}

	/**
	 * Getter for type.
	 * 
	 * @return Returns type value.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Getter for range.
	 * 
	 * @return Returns range value.
	 */
	public int getRange() {
		return range;
	}

	/**
	 * Getter for damage.
	 * 
	 * @return Returns damage value.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Displays a formatted representation of the type, damage, and range
	 * values.
	 * 
	 * @return Formatted string with weapon data.
	 */
	@Override
	public String toString() {
		String weaponType = "";

		// create type
		if (type == BASEBALL_BAT) {
			weaponType = "BAT";
		} else if (type == CROSSBOW) {
			weaponType = "CROSSBOW";
		} else if (type == SHOTGUN) {
			weaponType = "SHOTGUN";
		}

		return "[WEAPON TYPE: " + weaponType + ", DAMAGE: " + damage + " , RANGE: " + range + "]";

	}

}
