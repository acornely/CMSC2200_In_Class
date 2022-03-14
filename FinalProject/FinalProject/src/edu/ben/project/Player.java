package edu.ben.project;

import java.util.ArrayList;

/**
 * The Player class in in charge of keeping track of the player salary, buying
 * weapons and assigning weapons to teams
 *
 * 
 * @author Steve Schultz
 *
 */

public class Player {

	private boolean isEndTurn = false;
	private int salary;

	/**
	 * A team variables that gets created when the player wants to create a
	 * warrior or stealth team
	 */
	private Team currentTeam;

	/**
	 * An arraylist that stores all the deployable teams
	 */
	private ArrayList<Team> deployedTeamInventory = new ArrayList<>();

	/**
	 * The type of tean
	 */
	private int typeOfTeam;

	/**
	 * Holds a count for the total amount of teams
	 */
	private int[] globalTeamArray = new int[2];

	/**
	 * Holds a count for the total amount of teammates
	 */
	private int[] globalTeammateArray = new int[2];

	/**
	 * All the weapons the player buys, not set to any specific team yet
	 */
	private int[] globalWeaponArray = new int[3];

	/**
	 * All of the ammo buys, not set to a specific team net
	 */
	private int[] globalAmmoArray = new int[2];

	private boolean attackEnabled = false;

	/**
	 * This constructor automatially sets the player salary to $400
	 */
	public Player() {
		setSalary(400);
	}

	/**
	 * Called in the TeamInventory class. Adds a team to the currentTeam Array
	 * 
	 * @param typeOfTeam
	 *            - an int value of either Teammate.TYPE_WARRIOR or
	 *            Teammate.TYPE_STEALTH
	 */
	public void addTeam(int typeOfTeam) {
		globalTeamArray[typeOfTeam] += 1;
		currentTeam = new Team(typeOfTeam);
	}

	/**
	 * This method adds a teammte from the global teammate array to a speific
	 * team
	 * 
	 * @param team
	 *            - th current team
	 * @return - false if the globalTeammateArray is empty
	 */
	public boolean assignTeammateToTeam(Team team) {
		if (team.getType() == Team.TYPE_STEALTH || team.getType() == Team.TYPE_WARRIOR) {
			if (globalTeammateArray[team.getType()] > 0) {
				team.addTeammateToTeam(team.getType());
				globalTeammateArray[team.getType()] -= 1;
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a teammate to the globalTeammate Array
	 * 
	 * @param teammateType
	 * @return false if the salary is less than 10
	 */
	public boolean createTeammate(int teammateType) {
		if (salary >= 10) {
			if (teammateType == Team.TYPE_WARRIOR) {
				globalTeammateArray[Team.TYPE_WARRIOR] += 1;
				setSalary(getSalary() - 10);
				return true;
			} else if (teammateType == Team.TYPE_STEALTH) {
				globalTeammateArray[Team.TYPE_STEALTH] += 1;
				setSalary(getSalary() - 10);
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a baseball bat(0), crossbow(1), or shotgun(2) weapon and
	 * increments the count in the global weapon array. Also lowered the player
	 * salary depending on what they buy. Bat = $5, crossbow = $10 and shotgun =
	 * $20.
	 * 
	 * 
	 * @param typeOfWeapon
	 *            - an int type of baseball bat, shotgun, or crossow from the
	 *            weapon class
	 * @return - returns false if not a baseball bat, shotgun or crossbow
	 */
	public boolean createWeapon(int typeOfWeapon) {
		if (typeOfWeapon == Weapon.BASEBALL_BAT && salary >= 5) {
			setSalary(getSalary() - 5);
			globalWeaponArray[Weapon.BASEBALL_BAT] += 1;
			return true;
		} else if (typeOfWeapon == Weapon.CROSSBOW && salary >= 10) {
			setSalary(getSalary() - 10);
			globalWeaponArray[Weapon.CROSSBOW] += 1;
			return true;

		} else if (typeOfWeapon == Weapon.SHOTGUN && salary >= 20) {
			setSalary(getSalary() - 20);
			globalWeaponArray[Weapon.SHOTGUN] += 1;
			return true;
		}
		return false;
	}

	/**
	 * Takes in an int value representing the ammo from the weapon class. If the
	 * user passes in the arrows than the globalAmmoArray in the arrow elements
	 * increases by 10. When bullet is passed in than the value of the bullet
	 * element in the globalAmmoArray gets incremented by 5. It then adjusts the
	 * total salary. Arrows = $10 and bullets = $10.
	 * 
	 * @param typeOfAmmo
	 *            - and int of arrows or bullets accessed from the weapon class
	 * @return - false passed in is not bullets or ammo.
	 */
	public boolean createAmmo(int typeOfAmmo) {
		if (typeOfAmmo == Weapon.PACK_OF_ARROWS && salary >= 10) {
			setSalary(getSalary() - 10);
			globalAmmoArray[Weapon.PACK_OF_ARROWS] += 10;
			return true;
		} else if (typeOfAmmo == Weapon.SET_OF_BULLETS && salary >= 10) {
			setSalary(getSalary() - 10);
			globalAmmoArray[Weapon.SET_OF_BULLETS] += 5;
			return false;
		}
		return false;
	}

	/**
	 * Assigns a weapon for a specific team as long as the WeaponArray in the
	 * specific element hasa cont greater than 0.
	 * 
	 * @param team
	 *            - passes in an instance of Team
	 * 
	 * @param typeOfWeapon
	 *            - takes in a baseball bat, crossbow, or shotgun from the
	 *            weapon class.
	 * @return - Return false - 1) A Warrior team tries buying more than one
	 *         baseball bat 2) A Warrior tries buying a crossbow 3) A Stealth
	 *         tries buying a baseball bat or shotgun 4) When the number of team
	 *         members is below the weapon count
	 */
	public boolean assignWeaponToTeam(Team team, int typeOfWeapon) {
		// checks to see if there are any weapons available in he
		// globalWeaponArray
		if (globalWeaponArray[typeOfWeapon] > 0) {

			// If there are, then it then checks to see if there are enough
			// teammates to pass weapons to.

			if (typeOfWeapon == Weapon.BASEBALL_BAT) {
				// call the weapon array to see how mnay baseball bats there
				// are
				int[] weaponArray = team.getWeaponArray();

				if ((weaponArray[Weapon.BASEBALL_BAT] < 1) && (team.getType() != Team.TYPE_STEALTH)) {
					team.addWeapon(typeOfWeapon);
					globalWeaponArray[Weapon.BASEBALL_BAT] -= 1;
					return true;
				}

			} else if (typeOfWeapon == Weapon.CROSSBOW) {
				if (team.getType() == Team.TYPE_STEALTH) {
					team.addWeapon(typeOfWeapon);
					globalWeaponArray[Weapon.CROSSBOW] -= 1;
					return true;
				}

			} else if (typeOfWeapon == Weapon.SHOTGUN) {
				if (team.getType() != Team.TYPE_STEALTH) {
					team.addWeapon(typeOfWeapon);
					globalWeaponArray[Weapon.SHOTGUN] -= 1;
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Creates ammo for a specific team. If returned true than the weaponArray
	 * in the Team class with be incremented approprately for the specific
	 * element.
	 * 
	 * @param team
	 *            - passes in an instance of Team
	 * @param typeOfAmmunition
	 *            - Takes in bullets or arrows from the weapons class.
	 * 
	 * @return - return false if the user tries buying ammo for a weapon that
	 *         they currently do not have.
	 */
	public boolean assignAmmunitionToTeam(Team team, int typeOfAmmunition) {
		// checks to make sure that there is ammo to add
		if (globalAmmoArray[typeOfAmmunition] > 0) {

			// int[] ammoArray = team.getAmmoArray();

			if (typeOfAmmunition == Weapon.PACK_OF_ARROWS) {
				if (globalAmmoArray[Weapon.PACK_OF_ARROWS] >= 10) {
					team.addAmmo(typeOfAmmunition);
					globalAmmoArray[Weapon.PACK_OF_ARROWS] -= 10;
					return true;
				}
			} else if (typeOfAmmunition == Weapon.SET_OF_BULLETS) {
				if (globalAmmoArray[Weapon.SET_OF_BULLETS] >= 5) {
					team.addAmmo(typeOfAmmunition);
					globalAmmoArray[Weapon.SET_OF_BULLETS] -= 5;
					return true;
				}
			}
		}
		return false;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getTypeOfTeam() {
		return typeOfTeam;
	}

	public void setTypeOfTeam(int typeOfTeam) {
		this.typeOfTeam = typeOfTeam;
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void setCurrentTeam(Team currentTeam) {
		this.currentTeam = currentTeam;
	}

	public int[] getGlobalWeaponArray() {
		return globalWeaponArray;
	}

	public void setGlobalWeaponArray(int[] globalWeaponArray) {
		this.globalWeaponArray = globalWeaponArray;
	}

	public int[] getGlobalAmmoArray() {
		return globalAmmoArray;
	}

	public void setGlobalAmmoArray(int[] globalAmmoArray) {
		this.globalAmmoArray = globalAmmoArray;
	}

	public int[] getGlobalTeamArray() {
		return globalTeamArray;
	}

	public void setGlobalTeamArray(int[] globalTeamArray) {
		this.globalTeamArray = globalTeamArray;
	}

	public int[] getGlobalTeammateArray() {
		return globalTeammateArray;
	}

	public void setGlobalTeammateArray(int[] globalTeammateArray) {
		this.globalTeammateArray = globalTeammateArray;
	}

	public boolean isAttackEnabled() {
		return attackEnabled;
	}

	public void setAttackEnabled(boolean attackEnabled) {
		this.attackEnabled = attackEnabled;
	}

	public ArrayList<Team> getDeployedTeamInventory() {
		return deployedTeamInventory;
	}

	public void setDeployedTeamInventory(ArrayList<Team> deployedTeamInventory) {
		this.deployedTeamInventory = deployedTeamInventory;
	}

	public boolean isEndTurn() {
		return isEndTurn;
	}

	public void setEndTurn(boolean isEndTurn) {
		this.isEndTurn = isEndTurn;
	}

}
