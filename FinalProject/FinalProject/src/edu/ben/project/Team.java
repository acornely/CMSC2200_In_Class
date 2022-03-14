package edu.ben.project;

public class Team extends Group {

	public static final int TYPE_WARRIOR = 0;
	public static final int TYPE_STEALTH = 1;

	public static final int SHOT_CAP = 4;

	private QuestItem item = null;
	private static String itemName;

	private int type;
	private int ammoCount;
	private int teamNoise;
	/**
	 * An array list that holds the teammate counts
	 */
	private int[] teammateCount = new int[2];

	/**
	 * an array to keep track of the count of each weapon
	 */
	private int[] weaponArray = new int[3];

	/**
	 * Creates a team of the type specified by the type parameter. Use
	 * Team.WARRIOR or Team.STEALTH. Sets speed multiplier, type, and noise
	 * range values.
	 * 
	 * @param type
	 *            The type of team to be created (Team.WARRIOR or Team.STEALTH).
	 */
	public Team(int type) {
		assignInitialValues(type);
	}

	/**
	 * Creates a team of the type specified by the type parameter. Use
	 * Team.WARRIOR or Team.STEALTH. Sets speed multiplier, type, and noise
	 * range values.
	 * 
	 * Sets row and column values.
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Column value.
	 * @param type
	 *            The type of team to be created (Team.WARRIOR or Team.STEALTH).
	 */
	public Team(int row, int col, int type) {
		setRow(row);
		setCol(col);
		assignInitialValues(type);
	}

	/**
	 * To be used in constructor. Assigns initial values of Team variables based
	 * on parameter value.
	 * 
	 * @param type
	 *            The type of team (Team.WARRIOR or Team.STEALTH).
	 */
	private void assignInitialValues(int type) {
		if (type == TYPE_STEALTH) {
			setSpeedMultiplier(2);
			setTeamNoise(3);
			this.type = type;
		} else if (type == TYPE_WARRIOR) {
			setSpeedMultiplier(1);
			setTeamNoise(5);
			this.type = type;
		}

	}

	/**
	 * This method takes in a type int (the int representing the warrior and
	 * stealth type teammates. 0 represents warrior, 1 represents stealth) and
	 * adds them to an arrayList. The arrayList represents the current team
	 * these teammates are on.
	 * 
	 * @param type
	 *            - Takes in a type int. 0 represents type warrior and 1
	 *            represents type stealth.
	 * @return - Returns true if 0 or 1 are entered. False if not
	 */
	public boolean addTeammateToTeam(int type) {
		if (isSameType(type)) {
			teammateCount[type] += 1;
			return true;
		}

		return false;

	}

	/**
	 * Checks to see if the team type matches the type of unit you are adding to
	 * it.
	 * 
	 * @param playerType
	 *            - an int of type warrior or stealth
	 * @return - true if the team and unit match. False if they don't
	 */
	private boolean isSameType(int playerType) {
		return playerType == type;
	}

	/**
	 * This method takes in an integer representing a weapon (Bat = 0, Crossbow
	 * = 1; Shotgun = 2). Then it increments the weaponArray at the index
	 * specified by the weapon.
	 * 
	 * @param weapon
	 *            - an int representing the weapon types
	 * @return - returns true if the weapon was added. False if it wasnt
	 */
	public boolean addWeapon(int weapon) {
		if (weapon == Weapon.BASEBALL_BAT) {
			weaponArray[Weapon.BASEBALL_BAT] += 1;
			return true;
		} else if (weapon == Weapon.CROSSBOW) {
			weaponArray[Weapon.CROSSBOW] += 1;
			return true;
		} else if (weapon == Weapon.SHOTGUN) {
			weaponArray[Weapon.SHOTGUN] += 1;
			return true;
		}
		return false;
	}

	/**
	 * This method takes in an int (0 representing a pack of bullets and 1
	 * representing arrows). After the ammo is added it increments the count of
	 * the ammoArray
	 * 
	 * 
	 * @param ammoType
	 *            - either takes in 0 (bullets) or 1 (arrows).
	 * @return - true if the ammo was added, false if not
	 */
	public boolean addAmmo(int ammoType) {
		if (ammoType == Weapon.PACK_OF_ARROWS) {
			ammoCount += 10;
			return true;
		} else if (ammoType == Weapon.SET_OF_BULLETS) {
			ammoCount += 5;
		}
		return false;
	}

	/**
	 * Returns the max attack range possible based on the current team's type
	 * and weapons.
	 * 
	 * @return Returns shotgun max range if a warrior team has shotguns, returns
	 *         the bat max range if the team only has a bat, else returns the
	 *         stealth team's max crossbow range.
	 */
	public int getRange() {
		// warrior team with at least one gun and has ammo
		if (isWarriorTeam() && weaponArray[Weapon.SHOTGUN] >= 1 && ammoCount > 0) {
			return Weapon.MAX_RANGE_SHOTGUN;
			// warrior team, no guns or ammo, one bat
		} else if (isWarriorTeam() && (weaponArray[Weapon.SHOTGUN] == 0 || ammoCount == 0) && hasBat()) {
			return Weapon.MAX_RANGE_BASEBALL_BAT;
			// stealth team
		} else {
			return Weapon.MAX_RANGE_CROSSBOW;
		}
	}

	/**
	 * Is deployable if the teammate count and weapon count are greater than 0
	 * and the team has the same amount of weapons and teammates
	 * 
	 * @return - true if there are more teammtes than weapons. false if there
	 *         are more weapons then weapons
	 */
	public boolean isTeamDeployable() {
		if (getTeammateCount()[getType()] > 0 && getTotalNumberOfWeapons() >= 0
				&& getTeammateCount()[getType()] >= getTotalNumberOfWeapons()) {
			return true;
		}

		return false;
	}

	/**
	 * This method is called in zombie logic to generate the sound given off
	 * when a team attacks a weapon
	 * 
	 * @return
	 */
	public int generateSoundOffWeapon() {
		int soundGivenOff = 0;
		if (isWarriorTeam()) {
			if (ammoCount > 0) {
				soundGivenOff = Weapon.SHOTGUN_NOISE;

			} else {
				soundGivenOff = Weapon.BAT_NOISE;

			}
		} else if (isStealthTeam()) {
			if (ammoCount > 0) {
				soundGivenOff = Weapon.CROSSBOW_NOISE;
			}
		}
		return soundGivenOff;
	}

	public int getTotalNumberOfWeapons() {
		return weaponArray[Weapon.BASEBALL_BAT] + weaponArray[Weapon.CROSSBOW] + weaponArray[Weapon.SHOTGUN];
	}

	/**
	 * Determines if the team is a warrior team based on team type value.
	 * 
	 * @return Returns true if the team is a warrior team, else false.
	 */
	public boolean isWarriorTeam() {
		return type == TYPE_WARRIOR;
	}

	/**
	 * Determines if the team is a stealth team based on team type value.
	 * 
	 * @return Returns true if the team is a stealth team, else false.
	 */
	public boolean isStealthTeam() {
		return type == TYPE_STEALTH;
	}

	/**
	 * Checks if the team is currently holding a bat.
	 * 
	 * @return Returns true if the team is holding at least one bat, else false.
	 */
	public boolean hasBat() {
		return weaponArray[Weapon.BASEBALL_BAT] >= 1;
	}

	/**
	 * Getter for weapon array. Use Weapon.BASEBALL_BAT, Weapon.SHOTGUN, or
	 * Weapon.CROSSBOW to access a particular element.
	 * 
	 * @return Returns the team's weapon int array.
	 */
	public int[] getWeaponArray() {
		return weaponArray;
	}

	/**
	 * Returns the team's current ammunition count.
	 * 
	 * @return An int value representing the team's current ammo count.
	 */
	public int getAmmoCount() {
		return ammoCount;
	}

	/**
	 * Sets the team's ammunition count variable to a new value.
	 * 
	 * @param ammoCount
	 *            The new amount of ammunition.
	 */
	public void setAmmoCount(int ammoCount) {
		this.ammoCount = ammoCount;
	}

	/**
	 * Returns an int value representing the current type of Team. Use
	 * Team.TYPE_WARRIOR or Team.TYPE_STEALTH for comparison.
	 * 
	 * @return The current type of team.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Returns the number of teammates within the team.
	 * 
	 * @return returns the size of the team.
	 */
	public int getSizeOfTeam() {
		return teammateCount[type];
	}

	/**
	 * Getter for teammate array. Use Team.TYPE_WARRIOR or Team.TYPE_STEALTH to
	 * access correct element.
	 * 
	 * @return Returns the current teammate array.
	 */
	public int[] getTeammateCount() {
		return teammateCount;
	}

	/**
	 * Getter for team noise radius value.
	 * 
	 * @return Returns an int representing the team's noise radius.
	 */
	public int getTeamNoise() {
		return teamNoise;
	}

	/**
	 * Setter for team noise radius.
	 * 
	 * @param teamNoise
	 *            A new noise radius value.
	 */
	public void setTeamNoise(int teamNoise) {
		this.teamNoise = teamNoise;
	}

	/**
	 * Getter for item name.
	 * 
	 * @return Returns the current item name String.
	 */
	public static String getItemName() {
		return itemName;
	}

	/**
	 * Setter for item name.
	 * 
	 * @param itemName
	 *            A new item name String.
	 */
	public static void setItemName(String itemName) {
		Team.itemName = itemName;
	}

	/**
	 * Getter for quest item.
	 * 
	 * @return Returns the current quest item. Value may be null.
	 */
	public QuestItem getItem() {
		return item;
	}

	/**
	 * Setter for quest item.
	 * 
	 * @param item
	 *            A new quest item.
	 */
	public void setItem(QuestItem item) {
		this.item = item;
	}

	/**
	 * Creates a String representing type, number of teammates, weapon count,
	 * and ammo count.
	 * 
	 * @return A formatted representation of the current attributes of the Team
	 *         instance.
	 */
	@Override
	public String toString() {
		String classType;
		String weaponData;
		String hasItem;

		// assign data depending on team type
		if (type == TYPE_WARRIOR) {
			classType = "WARRIOR";
			weaponData = "SHOTGUNS: " + weaponArray[Weapon.SHOTGUN] + "<br>BATS: " + weaponArray[Weapon.BASEBALL_BAT];
		} else {
			classType = "STEALTH";
			weaponData = "CROSSBOWS: " + weaponArray[Weapon.CROSSBOW];
		}
		if (item != null) {
			hasItem = "YES";
		} else {
			hasItem = "NO";
		}

		return "<html>TYPE: " + classType + "<br>ROW: " + getRow() + "<br>COL: " + getCol() + "<br>TEAMMATES: "
				+ teammateCount[type] + "<br>" + weaponData + "<br>AMMO COUNT: " + ammoCount + "<br>"
				+ Team.getItemName() + ": " + hasItem + "</html>";
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
		if (o instanceof Team) {
			Team team = (Team) o;

			// check weapon array
			for (int i = 0; i < weaponArray.length; i++) {
				if (weaponArray[i] != team.getWeaponArray()[i]) {
					return false;
				}
			}

			// check teammate array
			for (int i = 0; i < teammateCount.length; i++) {
				if (teammateCount[i] != team.getTeammateCount()[i]) {
					return false;
				}
			}

			return type == team.getType() && getRow() == team.getRow() && getCol() == team.getCol()
					&& ammoCount == team.getAmmoCount() && teamNoise == team.getTeamNoise()
					&& getSpeedMultiplier() == team.getSpeedMultiplier();
		} else {
			return false;
		}
	}

}
