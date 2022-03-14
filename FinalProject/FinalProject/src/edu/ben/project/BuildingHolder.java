package edu.ben.project;

import java.util.ArrayList;

/**
 * Holds coordinates of houses, home base, and army base squares. Contains
 * methods to check if passed in coordinates match existing building
 * coordinates.
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class BuildingHolder {

	public static final int NUM_HOME_BASE_SQUARES = 9;
	public static final int NUM_ARMY_BASE_SQUARES = 7;

	private static final int ARMY_BASE_TOP_ROW = 7;
	private static final int ARMY_BASE_COL = 38;

	public static final int HOME_BASE_TOP_ROW = 6;
	public static final int HOME_BASE_COL = 0;

	private ArrayList<Building> houses = new ArrayList<>();
	private ArrayList<Building> homeBase = new ArrayList<>();
	private ArrayList<Building> armyBase = new ArrayList<>();

	/**
	 * Sets coordinate values of houses, army base, and home base.
	 */
	public BuildingHolder() {
		houses.add(new Building(Building.HOUSE, 19, 10));
		houses.add(new Building(Building.HOUSE, 15, 25));
		houses.add(new Building(Building.HOUSE, 7, 20));
		houses.add(new Building(Building.HOUSE, 3, 35));

		// set home base coords
		for (int i = HOME_BASE_TOP_ROW; i < NUM_HOME_BASE_SQUARES + HOME_BASE_TOP_ROW; i++) {
			homeBase.add(new Building(Building.HOME_BASE, i, HOME_BASE_COL));
		}

		// set army base coords
		for (int i = ARMY_BASE_TOP_ROW; i < NUM_ARMY_BASE_SQUARES + ARMY_BASE_TOP_ROW; i++) {
			armyBase.add(new Building(Building.ARMY_BASE, i, ARMY_BASE_COL));
		}
	}

	/**
	 * Determines if passed in coordinates match the coordinates of any house,
	 * army base, or home base square.
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Column value.
	 * @return Returns true if coordinates match a house, army base, or home
	 *         base coordinate, else false.
	 */
	public boolean isBuilding(int row, int col) {
		return isHouse(row, col) || isArmyBase(row, col) || isHomeBase(row, col);
	}

	/**
	 * Determines if passed in coordinates match any house coordinates.
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Col value.
	 * @return Returns true if the passed in coordinates match any house
	 *         coordinates, else false.
	 */
	public boolean isHouse(int row, int col) {
		// check house coordinates.
		for (int i = 0; i < houses.size(); i++) {
			if (houses.get(i).getRow() == row && houses.get(i).getCol() == col) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if passed in coordinates match any army base coordinates.
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Col value.
	 * @return Returns true if the passed in coordinates match any army base
	 *         coordinates, else false.
	 */
	public boolean isArmyBase(int row, int col) {
		// check army base coordinates
		for (int i = 0; i < armyBase.size(); i++) {
			if (armyBase.get(i).getRow() == row && armyBase.get(i).getCol() == col) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if passed in coordinates match any home base coordinates.
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Col value.
	 * @return Returns true if the passed in coordinates match any home base
	 *         coordinates, else false.
	 */
	public boolean isHomeBase(int row, int col) {
		// check home base coordinates.
		for (int i = 0; i < homeBase.size(); i++) {
			if (homeBase.get(i).getRow() == row && homeBase.get(i).getCol() == col) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns house array.
	 */
	public ArrayList<Building> getHouses() {
		return houses;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns home base array.
	 */
	public ArrayList<Building> getHomeBase() {
		return homeBase;
	}

	/**
	 * Getter.
	 * 
	 * @return Returns army base array.
	 */
	public ArrayList<Building> getArmyBase() {
		return armyBase;
	}

	/**
	 * Returns formatted list of building coordinates.
	 */
	@Override
	public String toString() {
		return "House Coords: " + houses.toString() + ", Home Base Coords: " + homeBase.toString()
				+ ", Army Base Coords: " + armyBase.toString();
	}
}
