package edu.ben.project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import javax.swing.JButton;

/**
 * Holds data necessary to play zombie game. Game board is 21x39 Group array.
 * May contain either Team or ZombieHorde objects.
 * 
 * Contains methods to interact with and obtain data from the array, such as
 * distance calculation, pathfinding, and board updates.
 * 
 * 
 * @author Colom Boyle
 * @version 1
 *
 */
public class Board {

	public final int NUM_INITIAL_HORDES = 10;
	public final int NUM_ROWS = 21;
	public final int NUM_COLS = 39;

	private Group[][] boardArray;
	private Die die;
	private CoordinateHolder coordinateHolder;
	private BuildingHolder buildingHolder;

	// Button booleans
	private boolean moveEnabled = false;
	private boolean moveCompleted = true;
	private boolean destinationSelectable = false;
	private boolean teamSelected = false;
	private boolean isDeployable = true;
	private boolean isAirStrikeAvailable = false;
	private int airStrikeClickCount = 5;

	/* Used for attacking a zombie horde */
	private boolean targetSelectable = false;
	private boolean attackEnabled = false;

	private boolean isTeamMoved = false;
	private boolean isTeamAttacked = false;

	/**
	 * Set in the air strike listener to see if the user chose a row or column
	 * strike
	 */
	private int airStrikeChosenOption;

	private JButton[][] buttonArray = new JButton[NUM_ROWS][NUM_COLS];

	/**
	 * Constructor. Initializes array, Die, CoordinateHolder, and
	 * BuildingHolder.
	 */
	public Board() {
		boardArray = new Group[NUM_ROWS][NUM_COLS];
		die = new Die();
		coordinateHolder = new CoordinateHolder();
		buildingHolder = new BuildingHolder();

	}

	/**
	 * Determines if clear line-of-sight exists between the starting coordinates
	 * and end coordinates.
	 * 
	 * Does not include starting or ending points in checks as these locations
	 * may already be filled, which would result in an incorrect false value
	 * being returned.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param currentRow
	 *            Starting row.
	 * @param currentCol
	 *            Starting column.
	 * @param destRow
	 *            End row.
	 * @param destCol
	 *            End column.
	 * @return Returns true if no obstacles are found on any of the coordinates
	 *         along the shot path between staring and ending points, otherwise
	 *         returns false.
	 */
	public boolean hasLineOfSight(int currentRow, int currentCol, int destRow, int destCol) {
		int dx = Math.abs(destCol - currentCol);
		int dy = Math.abs(destRow - currentRow);
		int n = 1 + dx + dy;
		int xInc;
		int yInc;
		int error = dx - dy;
		dx *= 2;
		dy *= 2;

		if (destCol > currentCol) {
			xInc = 1;
		} else {
			xInc = -1;
		}

		if (destRow > currentRow) {
			yInc = 1;
		} else {
			yInc = -1;
		}

		for (int i = n; i > 0; i--) {
			// check current coordinate is not starting or end coordinate and
			// current coordinate is empty
			if (i != n && i != 1 && (boardArray[currentRow][currentCol] != null
					|| buildingHolder.isBuilding(currentRow, currentCol))) {
				return false;
			}

			if (error > 0) {
				currentCol += xInc;
				error -= dy;
			} else if (error < 0) {
				currentRow += yInc;
				error += dx;
			} else {
				currentCol += xInc;
				error -= dy;
				currentRow += yInc;
				error += dx;
				i--;
			}
		}
		return true;
	}

	/**
	 * Returns the validity of the passed in potential move. A move that has a
	 * reachable destination and is within the distance limit will return true.
	 * End goal must not be occupied.
	 * 
	 * A path is generated around obstacles, then examined. A distance value
	 * less than or equal to the path length indicates a valid move. Paths are
	 * not generated for moves with occupied end destinations or distance values
	 * that greatly exceed a unit's maximum movement capability, as this can be
	 * immediately returned false.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param distance
	 *            Distance allowed for current move.
	 * @param currentRow
	 *            Starting row.
	 * @param currentCol
	 *            Starting column.
	 * @param destinationRow
	 *            Goal row.
	 * @param destinationCol
	 *            Goal column.
	 * @return Returns true if a valid move is found, else false.
	 */
	public boolean isMoveValid(int distance, int currentRow, int currentCol, int destinationRow, int destinationCol) {
		// find optimal path, else return false if destination filled. Does not
		// run path algorithm if excessively large moves are entered.
		if (boardArray[destinationRow][destinationCol] == null
				&& getDistance(currentRow, currentCol, destinationRow, destinationCol) <= distance * 2) {

			// get path
			Stack<Node> path = getPath(currentRow, currentCol, destinationRow, destinationCol);

			// return that path is within distance limit, else return false for
			// null path.
			if (path != null) {
				return path.size() <= distance;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Returns the shortest path to the intended destination. Implements the A*
	 * algorithm to create the path. Array elements are visited and stored in
	 * Node objects to generate a path. Each move is evaluated by weight, with
	 * the algorithm choosing the shortest path assuming a path is possible.
	 * 
	 * Path plots around obstacles and does not allow for a move to a board
	 * destination that is not empty (null).
	 * 
	 * Manages an open and closed list of nodes and assigns parent nodes as
	 * needed. Traces parent nodes back to start point to create the path.
	 * 
	 * Returns null if no path can be created to destination or starting/ending
	 * coordinates are not within the bounds of the board array.
	 * 
	 * A* Research Sources: http://www.policyalmanac.org/games/aStarTutorial.htm
	 * http://www.policyalmanac.org/games/heuristics.htm
	 * http://www.redblobgames.com/pathfinding/a-star/introduction.html
	 * http://www.redblobgames.com/pathfinding/grids/graphs.html
	 * http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#
	 * multiple-goals
	 * 
	 * @author Colom Boyle
	 * 
	 * @param distance
	 *            Maximum number of moves allowed to reach destination.
	 * @param currentRow
	 *            Starting row.
	 * @param currentCol
	 *            Starting column.
	 * @param destinationRow
	 *            Goal row.
	 * @param destinationCol
	 *            Goal column.
	 * @return Returns a stack containing Nodes with row and column data for
	 *         each step of the valid intended path.
	 * 
	 *         Will return null if path is unreachable.
	 */
	public Stack<Node> getPath(int currentRow, int currentCol, int destinationRow, int destinationCol) {
		// check true all coordinates inside array bounds before creating
		// path, return null if false
		if (isInsideBounds(currentRow, currentCol) && isInsideBounds(destinationRow, destinationCol)) {
			Stack<Node> path = new Stack<>();

			PriorityQueue<Node> open = new PriorityQueue<>();
			ArrayList<Node> closed = new ArrayList<>();

			Node start = new Node(currentRow, currentCol);
			Node goal = new Node(destinationRow, destinationCol);

			open.add(start);

			boolean found = false;
			int row = currentRow;
			int col = currentCol;

			// loop while queue is not empty
			while (!open.isEmpty()) {
				Node currentNode = open.peek();
				// end loop if destination found
				if (currentNode.getRow() == goal.getRow() && currentNode.getCol() == goal.getCol()) {
					found = true;
					goal = currentNode;
					break;
				}

				// remove first item from queue and place in closed list
				closed.add(open.poll());

				// check surrounding locations
				for (int j = 0; j < 4; j++) {
					// top
					if (j == 0) {
						row = currentNode.getRow() - 1;
						col = currentNode.getCol();
						// left
					} else if (j == 1) {
						row = currentNode.getRow();
						col = currentNode.getCol() - 1;
						// bottom
					} else if (j == 2) {
						row = currentNode.getRow() + 1;
						col = currentNode.getCol();
						// right
					} else {
						row = currentNode.getRow();
						col = currentNode.getCol() + 1;
					}

					Node temp = new Node(row, col);

					// check temp not on closed list, row is inside array
					// bounds, and node is open (not occupied)
					if (!closed.contains(temp) && isInsideBounds(row, col) && boardArray[row][col] == null) {
						double newCost = currentNode.getgScore()
								+ getEuclid(currentNode.getRow(), currentNode.getCol(), temp.getRow(), temp.getCol());

						if (newCost < temp.getgScore() || !open.contains(temp)) {
							temp.setgScore(newCost);
							temp.sethScore(getEuclid(temp.getRow(), temp.getCol(), goal.getRow(), goal.getCol()));
							temp.setfScore(temp.getgScore() + temp.gethScore());
							temp.setParent(currentNode);

							if (!open.contains(temp)) {
								open.add(temp);
							}
						}
					}
				}
			}

			// return null if destination not reachable
			if (!found) {
				return null;
			}

			Node marker = goal;

			// place path in stack, reversing backwards original order.
			// Completed stack will path from start to goal.
			while (!(marker.getRow() == start.getRow() && marker.getCol() == start.getCol())) {
				path.push(marker);
				marker = marker.getParent();
			}
			return path;
		} else {
			return null;
		}
	}

	/**
	 * Pathfinding for zombies. Functions similarly as getPath, but allows for a
	 * ZombieHorde to be a destination.
	 * 
	 * Uses A* algorithm modified to allow for a destination to contain a zombie
	 * horde or team. This allows for hordes to move to other hordes for merging
	 * or for hordes to move to teams.
	 * 
	 * If the destination is a team, the final destination coordinate is not
	 * included in the path as to avoid allowing hordes to move to the same
	 * square that a team currently occupies. Hordes will instead stop at the
	 * adjacent square found on the path. Does not allow paths through or to
	 * buildings.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param currentRow
	 *            Starting row.
	 * @param currentCol
	 *            Starting column.
	 * @param destinationRow
	 *            Goal row.
	 * @param destinationCol
	 *            Goal column.
	 * @return Returns a path to the destination coordinates, else null if no
	 *         path is possible or destination is outside of array bounds.
	 */
	public Stack<Node> getZombiePath(int currentRow, int currentCol, int destinationRow, int destinationCol) {
		// check true all coordinates inside array bounds before creating
		// path, return null if false
		if (isInsideBounds(currentRow, currentCol) && isInsideBounds(destinationRow, destinationCol)
				&& !(buildingHolder.isBuilding(destinationRow, destinationCol)
						&& boardArray[destinationRow][destinationCol] == null)) {

			Stack<Node> path = new Stack<>();

			PriorityQueue<Node> open = new PriorityQueue<>();
			ArrayList<Node> closed = new ArrayList<>();

			Node start = new Node(currentRow, currentCol);
			Node goal = new Node(destinationRow, destinationCol);

			open.add(start);

			boolean found = false;
			boolean destContainsTeam = boardArray[destinationRow][destinationCol] instanceof Team;
			int row = currentRow;
			int col = currentCol;

			// loop while queue is not empty
			while (!open.isEmpty()) {
				Node currentNode = open.peek();

				// end loop if destination found
				if (currentNode.getRow() == goal.getRow() && currentNode.getCol() == goal.getCol()) {
					found = true;
					goal = currentNode;
					break;
				}

				// remove first item from queue and place in closed list
				closed.add(open.poll());

				// check surrounding locations
				for (int j = 0; j < 4; j++) {
					// top
					if (j == 0) {
						row = currentNode.getRow() - 1;
						col = currentNode.getCol();
						// left
					} else if (j == 1) {
						row = currentNode.getRow();
						col = currentNode.getCol() - 1;
						// bottom
					} else if (j == 2) {
						row = currentNode.getRow() + 1;
						col = currentNode.getCol();
						// right
					} else {
						row = currentNode.getRow();
						col = currentNode.getCol() + 1;
					}

					Node temp = new Node(row, col);

					// check node is not on closed list, is inside bounds, is
					// destination or is null, and is not a building.
					if (!closed.contains(temp) && isInsideBounds(row, col)
							&& ((temp.getRow() == destinationRow && temp.getCol() == destinationCol)
									|| boardArray[row][col] == null)
							&& !buildingHolder.isBuilding(row, col)) {

						double newCost = currentNode.getgScore()
								+ getEuclid(currentNode.getRow(), currentNode.getCol(), temp.getRow(), temp.getCol());

						if (newCost < temp.getgScore() || !open.contains(temp)) {
							temp.setgScore(newCost);
							temp.sethScore(getEuclid(temp.getRow(), temp.getCol(), goal.getRow(), goal.getCol()));
							temp.setfScore(temp.getgScore() + temp.gethScore());
							temp.setParent(currentNode);

							if (!open.contains(temp)) {
								open.add(temp);
							}
						}

						// check node is not on closed list, is inside bounds,
						// is destination, and is building, and destination
						// contains a team
					} else if (!closed.contains(temp) && isInsideBounds(row, col)
							&& (temp.getRow() == destinationRow && temp.getCol() == destinationCol)
							&& buildingHolder.isBuilding(row, col) && destContainsTeam) {
						double newCost = currentNode.getgScore()
								+ getEuclid(currentNode.getRow(), currentNode.getCol(), temp.getRow(), temp.getCol());

						if (newCost < temp.getgScore() || !open.contains(temp)) {
							temp.setgScore(newCost);
							temp.sethScore(getEuclid(temp.getRow(), temp.getCol(), goal.getRow(), goal.getCol()));
							temp.setfScore(temp.getgScore() + temp.gethScore());
							temp.setParent(currentNode);

							if (!open.contains(temp)) {
								open.add(temp);
							}
						}
					}
				}
			}

			// return null if destination not reachable
			if (!found) {
				return null;
			}

			Node marker = goal;

			// place path in stack, reversing backwards original order.
			// Completed stack will path from start to goal.
			while (!(marker.getRow() == start.getRow() && marker.getCol() == start.getCol())) {
				// only push marker node if it is not the destination and does
				// not contain a Team
				if (!(marker.getRow() == destinationRow && marker.getCol() == destinationCol && destContainsTeam)) {
					path.push(marker);
				}
				marker = marker.getParent();
			}
			if (path.size() > 0) {
				return path;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Returns the Euclidean distance between two locations on the board.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param row1
	 *            First row.
	 * @param col1
	 *            First column.
	 * @param row2
	 *            Second row.
	 * @param col2
	 *            Second column.
	 * @return Returns a double value representing the Euclidean distance
	 *         between the passed in coordinates.
	 */
	public double getEuclid(int row1, int col1, int row2, int col2) {
		int dx = Math.abs(row1 - row2);
		int dy = Math.abs(col1 - col2);
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Returns the distance between the entered array coordinates.
	 * 
	 * Entered coordinates must be non-negative for valid results!!!
	 * 
	 * @author Colom Boyle
	 * 
	 * @param row1
	 *            First row value.
	 * @param col1
	 *            First column value.
	 * @param row2
	 *            Second row value.
	 * @param col2
	 *            Second column value.
	 * @return Returns an int value representing the distance between entered
	 *         coordinates.
	 */
	public int getDistance(int row1, int col1, int row2, int col2) {
		return Math.abs(col1 - col2) + Math.abs(row1 - row2);
	}

	/**
	 * Determines if the passed in coordinates are within the boundaries of the
	 * board array.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param row
	 *            Row value.
	 * @param col
	 *            Column value.
	 * @return Returns true if coordinates are inside array boundaries, else
	 *         false.
	 */
	public boolean isInsideBounds(int row, int col) {
		return row >= 0 && col >= 0 && row < boardArray.length && col < boardArray[0].length;
	}

	/**
	 * Highlights the buttons of possible move paths generated around the
	 * entered row and column.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param distance
	 *            Distance limit of path.
	 * @param row
	 *            Radius row value.
	 * @param col
	 *            Radius column value.
	 * @param buttonArray
	 *            A JButton array.
	 * @param highlight
	 *            The desired color.
	 */
	public void highlightColors(int distance, int row, int col, Color highlight) {
		for (int i = row - distance; i < (row - distance) + distance * 2 + 1; i++) {
			for (int j = col - distance; j < (col - distance) + distance * 2 + 1; j++) {
				// check index inside array, empty, and within move distance
				if (isInsideBounds(i, j) && boardArray[i][j] == null) {
					Stack<Node> path = getPath(row, col, i, j);
					int count = 0;

					// change color for path
					if (path != null) {
						while (count < distance && !path.isEmpty()) {
							Node temp = path.peek();
							if (getDistance(temp.getRow(), temp.getCol(), i, j) <= distance) {
								buttonArray[temp.getRow()][temp.getCol()].setBackground(highlight);
							}

							path.pop();
							count++;
						}
					}
				}
			}
		}
	}

	/**
	 * Changes the color of the buttons in the team deployment zone to the
	 * passed in color.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param buttonArray
	 *            The board JButtons.
	 * @param highlight
	 *            The desired color.
	 */
	public void highlightDeployArea(Color highlight) {
		// change color of deployment zone
		for (int i = BuildingHolder.HOME_BASE_TOP_ROW - 1; i < BuildingHolder.HOME_BASE_TOP_ROW
				+ BuildingHolder.NUM_HOME_BASE_SQUARES + 1; i++) {

			for (int j = BuildingHolder.HOME_BASE_COL; j < BuildingHolder.HOME_BASE_COL + 2; j++) {
				if (!buildingHolder.isHomeBase(i, j) && boardArray[i][j] == null) {
					buttonArray[i][j].setBackground(highlight);
				}
			}
		}
	}

	/**
	 * Determines if the passed in row and column values are valid locations for
	 * a team to be deployed to.
	 * 
	 * @author Colom Boyle
	 * 
	 * @param row
	 *            Deployment row.
	 * @param col
	 *            Deployment column.
	 * @return Returns true if the passed in coordinates are within the valid
	 *         deploy zone, else false.
	 */
	public boolean isValidDeployCoordinate(int row, int col) {
		return boardArray[row][col] == null
				&& (col == BuildingHolder.HOME_BASE_COL || col == BuildingHolder.HOME_BASE_COL + 1)
				&& row >= BuildingHolder.HOME_BASE_TOP_ROW - 1
				&& row <= BuildingHolder.HOME_BASE_TOP_ROW + BuildingHolder.NUM_HOME_BASE_SQUARES
				&& !buildingHolder.isHomeBase(row, col);
	}

	/**
	 * Sets all team and zombie horde squares to respective colors and icons.
	 * Used to update the JButton array when board data has changed.
	 * 
	 * @author Colom Boyle
	 */
	public void updateBoard() {
		for (int i = 0; i < boardArray.length; i++) {
			for (int j = 0; j < boardArray[i].length; j++) {

				// set background color to building, team, zombie, or default
				if (boardArray[i][j] instanceof Team) {
					Team t = (Team) boardArray[i][j];
					buttonArray[i][j].setToolTipText(String.valueOf(t.toString()));
					// team info
					if (t.getType() == Team.TYPE_WARRIOR) {
						buttonArray[i][j].setBackground(GUI.getWarrior());
						buttonArray[i][j].setIcon(GUI.getIconWarrior());
					} else {
						buttonArray[i][j].setBackground(GUI.getStealth());
						buttonArray[i][j].setIcon(GUI.getIconStealth());
					}

					// horde info
				} else if (boardArray[i][j] instanceof ZombieHorde) {
					buttonArray[i][j].setBackground(GUI.getZombie());
					buttonArray[i][j].setIcon(GUI.getIconZombie());
					buttonArray[i][j].setToolTipText(boardArray[i][j].toString());

					// home base info
				} else if (buildingHolder.isHomeBase(i, j)) {
					buttonArray[i][j].setBackground(GUI.getHomeBaseColor());
					buttonArray[i][j].setIcon(null);
					buttonArray[i][j].setToolTipText("HOME BASE");

					// army base info
				} else if (buildingHolder.isArmyBase(i, j)) {
					buttonArray[i][j].setBackground(GUI.getArmyBaseColor());
					buttonArray[i][j].setIcon(null);
					buttonArray[i][j].setToolTipText("ARMY BASE");

					// house info
				} else if (buildingHolder.isHouse(i, j)) {
					buttonArray[i][j].setBackground(GUI.getHouseColor());
					buttonArray[i][j].setIcon(GUI.getIconHouse());
					buttonArray[i][j].setToolTipText("HOUSE");

					// empty square
				} else {
					buttonArray[i][j].setBackground(GUI.getButtonBackground());
					buttonArray[i][j].setIcon(null);
					buttonArray[i][j].setToolTipText(null);
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param airStrikeCoordinates
	 *            - an int chosen by the user from the airStrikeListener. If the
	 *            user wants to attack a row, it will return 0. If the user
	 *            wants to attack a column, it will return a 1.
	 * @param row
	 *            - the row the
	 * @param col
	 */
	public ArrayList<ZombieHorde> airStrikeAttack(int airStrikeCoordinates, int row, int col) {
		ArrayList<ZombieHorde> hordesKilled = new ArrayList<>();
		// if a row is seleced
		if (airStrikeCoordinates == 0) {
			for (int i = 0; i < boardArray.length; i++) {
				if (boardArray[row][i] instanceof Group) {
					if (!buildingHolder.isBuilding(i, col)) {
						if (boardArray[row][i] instanceof ZombieHorde) {
							// removeZombiesFromBoard((ZombieHorde)
							// boardArray[i][col]);
							hordesKilled.add((ZombieHorde) boardArray[row][i]);
						}
						boardArray[row][i] = null;
					}
				}
			}

			// if a column is selected
		} else {
			for (int j = 0; j < boardArray.length; j++) {
				if (boardArray[j][col] instanceof Group) {
					if (!buildingHolder.isBuilding(j, col)) {

						if (boardArray[j][col] instanceof ZombieHorde) {
							// removeZombiesFromBoard((ZombieHorde)
							// boardArray[row][j]);
							hordesKilled.add((ZombieHorde) boardArray[j][col]);
						}
						boardArray[j][col] = null;
					}
				}

			}
			isAirStrikeAvailable = false;

		}
		
		return hordesKilled;
	}


	/**
	 * Resets all boolean logic variables for move operations to their original
	 * values.
	 */
	public void resetMoveBooleans() {
		moveEnabled = false;
		moveCompleted = true;
		destinationSelectable = false;
		teamSelected = false;
	}

	/**
	 * Resets all boolean logic variables for attack operations to their
	 * original values.
	 */
	public void resetAttackBooleans() {
		teamSelected = false;
		targetSelectable = false;
		attackEnabled = false;
	}

	/**
	 * Resets all attack and move booleans to their original values.s
	 */
	public void resetAttackAndMoveBooleans() {
		resetMoveBooleans();
		resetAttackBooleans();
	}

	/**
	 * Getter for game board.
	 * 
	 * @return Returns a two-dim Group array representing the game board.
	 */
	public Group[][] getBoardArray() {
		return boardArray;
	}

	/**
	 * Getter for Die object.
	 * 
	 * @return Returns a Die object.
	 */
	public Die getDie() {
		return die;
	}

	/**
	 * Get state of moveEnabled boolean.
	 * 
	 * @return Returns the current value of the moveEnabled boolean.
	 */
	public boolean isMoveEnabled() {
		return moveEnabled;
	}

	/**
	 * Sets value of moveEnabled boolean.
	 * 
	 * @param moveEnabled
	 *            The new value.
	 */
	public void setMoveEnabled(boolean moveEnabled) {
		this.moveEnabled = moveEnabled;
	}

	/**
	 * Get state of destinationSelectable boolean.
	 * 
	 * @return Returns the current value of destinationSelectable boolean.
	 */
	public boolean isDestinationSelectable() {
		return destinationSelectable;
	}

	/**
	 * Sets value of destinationSelectable boolean.
	 * 
	 * @param destinationSelectable
	 *            The new value.
	 */
	public void setDestinationSelectable(boolean destinationSelectable) {
		this.destinationSelectable = destinationSelectable;
	}

	/**
	 * Getter for CoordinateHolder
	 * 
	 * @return Returns a CoordinateHolder object.
	 */
	public CoordinateHolder getCoordinateHolder() {
		return coordinateHolder;
	}

	/**
	 * Get state of teamSelected boolean.
	 * 
	 * @return Returns the current value of teamSelected boolean.
	 */
	public boolean isTeamSelected() {
		return teamSelected;
	}

	/**
	 * Sets value of destinationSelectable boolean.
	 * 
	 * @param teamSelected
	 *            The new value.
	 */
	public void setTeamSelected(boolean teamSelected) {
		this.teamSelected = teamSelected;
	}

	/**
	 * Getter for BuildingHolder.
	 * 
	 * @return Returns BuildingHolder.
	 */
	public BuildingHolder getBuildingHolder() {
		return buildingHolder;
	}

	/**
	 * Get state of attackEnabled boolean.
	 * 
	 * @return Returns the current value of the attackEnabled boolean.
	 */
	public boolean isAttackEnabled() {
		return attackEnabled;
	}

	/**
	 * Sets value of attackEnabled boolean.
	 * 
	 * @param attackEnabled
	 *            The new value.
	 */
	public void setAttackEnabled(boolean attackEnabled) {
		this.attackEnabled = attackEnabled;
	}

	/**
	 * Get state of targetSelectable boolean.
	 * 
	 * @return Returns the current value of the targetSelectable boolean.
	 */
	public boolean isTargetSelectable() {
		return targetSelectable;
	}

	/**
	 * Sets value of targetSelectable boolean.
	 * 
	 * @param targetSelectable
	 *            The new value.
	 */
	public void setTargetSelectable(boolean targetSelectable) {
		this.targetSelectable = targetSelectable;
	}

	/**
	 * Get state of moveCompleted boolean.
	 * 
	 * @return Returns the current value of the moveComplete boolean.
	 */
	public boolean isMoveCompleted() {
		return moveCompleted;
	}

	/**
	 * Sets value of moveCompleted boolean.
	 * 
	 * @param moveCompleted
	 *            The new value.
	 */
	public void setMoveCompleted(boolean moveCompleted) {
		this.moveCompleted = moveCompleted;
	}

	/**
	 * Get state of isDeployable boolean.
	 * 
	 * @return Returns the current value of the isDeployable boolean.
	 */
	public boolean isDeployable() {
		return isDeployable;
	}

	/**
	 * Sets value of isDeployable boolean.
	 * 
	 * @param isDeployable
	 *            The new value.
	 */
	public void setDeployable(boolean isDeployable) {
		this.isDeployable = isDeployable;
	}

	public JButton[][] getButtonArray() {
		return buttonArray;
	}

	public void setButtonArray(JButton[][] buttonArray) {
		this.buttonArray = buttonArray;
	}

	public boolean isTeamMoved() {
		return isTeamMoved;
	}

	public void setTeamMoved(boolean isTeamMoved) {
		this.isTeamMoved = isTeamMoved;
	}

	public boolean isTeamAttacked() {
		return isTeamAttacked;
	}

	public void setTeamAttacked(boolean isTeamAttacked) {
		this.isTeamAttacked = isTeamAttacked;
	}

	public boolean isAirStrikeAvailable() {
		return isAirStrikeAvailable;
	}

	public void setAirStrikeAvailable(boolean isAirStrikeAvailable) {
		this.isAirStrikeAvailable = isAirStrikeAvailable;
	}

	public int getAirStrikeChosenOption() {
		return airStrikeChosenOption;
	}

	public void setAirStrikeChosenOption(int airStrikeChosenOption) {
		this.airStrikeChosenOption = airStrikeChosenOption;
	}

	public int getAirStrikeClickCount() {
		return airStrikeClickCount;
	}

	public void setAirStrikeClickCount(int airStrikeClickCount) {
		this.airStrikeClickCount = airStrikeClickCount;
	}

}
