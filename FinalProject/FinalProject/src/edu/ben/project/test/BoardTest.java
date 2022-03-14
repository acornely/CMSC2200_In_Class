package edu.ben.project.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import edu.ben.project.Board;
import edu.ben.project.Building;
import edu.ben.project.BuildingHolder;
import edu.ben.project.Node;
import edu.ben.project.Team;
import edu.ben.project.ZombieHorde;

public class BoardTest {

	private static final double DELTA = 0.0001;

	@Test
	public void testGetDistance() {
		Board b = new Board();
		int row1 = 2;
		int col1 = 3;
		int row2 = 7;
		int col2 = 10;
		assertEquals(12, b.getDistance(row1, col1, row2, col2));
	}

	@Test
	public void testIsMoveValid() {
		Board b = new Board();
		int distance = 3;

		int currentRow = 3;
		int currentCol = 3;

		int moveRow = 3;
		int moveCol = 6;
		assertTrue(b.isMoveValid(distance, currentRow, currentCol, moveRow, moveCol));
	}

	@Test
	public void testIsMoveValidAroundObstacles() {
		Board b = new Board();
		for (int i = 1; i < 4; i++) {
			b.getBoardArray()[i][3] = new Team(0);

		}
		for (int i = 1; i < 3; i++) {
			b.getBoardArray()[1][i] = new Team(0);
		}
		b.getBoardArray()[4][2] = new Team(0);
		assertTrue(b.isMoveValid(10, 2, 1, 2, 5));
	}

	@Test
	public void testIsMoveValidAroundObstaclePattern() {
		Board b = new Board();
		for (int i = 2; i < 13; i++) {
			b.getBoardArray()[2][i] = new Team(0);
			b.getBoardArray()[12][i] = new Team(0);
		}
		for (int i = 3; i < 12; i++) {
			b.getBoardArray()[i][12] = new Team(0);
		}
		boolean isMoveValid = b.isMoveValid(28, 7, 10, 1, 14);
		assertTrue(isMoveValid);
	}

	@Test
	public void testIsMoveValidOddObstacleShape() {
		Board b = new Board();
		int row = 12;
		int col = 0;
		for (int i = 0; i < 8; i++) {
			b.getBoardArray()[2][i + 5] = new ZombieHorde(2, i + 5);
		}

		for (int i = 0; i < 11; i++) {
			b.getBoardArray()[i + 2][12] = new ZombieHorde(i + 2, 12);
		}
		assertTrue(b.isMoveValid(25, row, col, 1, 14));
	}

	@Test
	public void testIsMoveValidNoDiagonalsOneMove() {
		Board b = new Board();
		assertFalse(b.isMoveValid(1, 2, 2, 3, 3));
	}

	@Test
	public void testIsMoveValidSurrounded() {
		Board b = new Board();
		int row = 4;
		int col = 4;
		int size = 3;
		for (int i = col; i < col + size; i++) {
			b.getBoardArray()[row][i] = new Team(0);
			b.getBoardArray()[row + size - 1][i] = new Team(0);
		}
		b.getBoardArray()[row + 1][col] = new Team(0);
		b.getBoardArray()[row + 1][col + size - 1] = new Team(0);
		assertFalse(b.isMoveValid(6, row + 1, col + 1, 11, col + 1));
	}

	@Test
	public void testGetPathOutOfBounds() {
		Board b = new Board();
		assertEquals(null, b.getPath(1, 1, -1, -1));
	}

	@Test
	public void testGetPathOutOfBounds2() {
		Board b = new Board();
		assertEquals(null, b.getPath(-1, -1, 1, 1));
	}

	@Test
	public void testGetPathOutOfBoundsStartOut() {
		Board b = new Board();
		assertEquals(null, b.getPath(-1, -1, -1, -1));
	}

	@Test
	public void testGetZombiePathTeamDestination() {
		Board b = new Board();

		int teamRow = 0;
		int teamCol = 0;

		int hordeRow = 3;
		int hordeCol = 0;

		int expected = 2;

		Team team = new Team(teamRow, teamCol, Team.TYPE_WARRIOR);
		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);

		b.getBoardArray()[teamRow][teamCol] = team;
		b.getBoardArray()[hordeRow][hordeCol] = horde;

		Stack<Node> zombiePath = b.getZombiePath(hordeRow, hordeCol, teamRow, teamCol);

		assertEquals(expected, zombiePath.size());
	}

	@Test
	public void testGetZombiePathEmptyHouseNoPath() {
		Board b = new Board();

		Building house = b.getBuildingHolder().getHouses().get(0);

		int houseRow = house.getRow();
		int houseCol = house.getCol();

		int hordeRow = houseRow;
		int hordeCol = houseCol - 3;

		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);

		b.getBoardArray()[hordeRow][hordeCol] = horde;

		Stack<Node> zombiePath = b.getZombiePath(hordeRow, hordeCol, houseRow, houseCol);

		assertEquals(null, zombiePath);
	}

	@Test
	public void testGetZombiePathEmptyDestination() {
		Board b = new Board();

		int destinationRow = 0;
		int destinationCol = 0;

		int hordeRow = 3;
		int hordeCol = 0;

		int expected = 3;

		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);

		b.getBoardArray()[hordeRow][hordeCol] = horde;

		Stack<Node> zombiePath = b.getZombiePath(hordeRow, hordeCol, destinationRow, destinationCol);

		assertEquals(expected, zombiePath.size());
	}

	@Test
	public void testGetZombiePathEmptyDestinationWithTeamObstacle() {
		Board b = new Board();

		int destinationRow = 0;
		int destinationCol = 0;

		int hordeRow = 3;
		int hordeCol = 0;

		int obstacleRow = 2;
		int obstacleCol = 0;

		int expected = 5;

		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);
		Team obstacleTeam = new Team(obstacleRow, obstacleCol, Team.TYPE_WARRIOR);

		b.getBoardArray()[hordeRow][hordeCol] = horde;
		b.getBoardArray()[obstacleRow][obstacleCol] = obstacleTeam;

		Stack<Node> zombiePath = b.getZombiePath(hordeRow, hordeCol, destinationRow, destinationCol);

		assertEquals(expected, zombiePath.size());
	}

	@Test
	public void testGetPathDestinationSurrounded() {
		Board b = new Board();

		int destinationRow = 10;
		int destinationCol = 15;

		int horde1Row = destinationRow - 1;
		int horde1Col = destinationCol;

		int horde2Row = destinationRow + 1;
		int horde2Col = destinationCol;

		int horde3Row = destinationRow;
		int horde3Col = destinationCol - 1;

		int horde4Row = destinationRow;
		int horde4Col = destinationCol + 1;

		int teamRow = 10;
		int teamCol = 11;

		Team team = new Team(Team.TYPE_WARRIOR, teamRow, teamCol);
		ZombieHorde horde1 = new ZombieHorde(horde1Row, horde1Col);
		ZombieHorde horde2 = new ZombieHorde(horde2Row, horde2Col);
		ZombieHorde horde3 = new ZombieHorde(horde3Row, horde3Col);
		ZombieHorde horde4 = new ZombieHorde(horde4Row, horde4Col);

		b.getBoardArray()[teamRow][teamCol] = team;
		b.getBoardArray()[horde1Row][horde1Col] = horde1;
		b.getBoardArray()[horde2Row][horde2Col] = horde2;
		b.getBoardArray()[horde3Row][horde3Col] = horde3;
		b.getBoardArray()[horde4Row][horde4Col] = horde4;

		Stack<Node> path = b.getPath(teamRow, teamCol, destinationRow, destinationCol);
		assertEquals(null, path);
	}

	@Test
	public void testGetZombiePathEmptyDestinationWithHordeObstacle() {
		Board b = new Board();

		int destinationRow = 0;
		int destinationCol = 0;

		int hordeRow = 3;
		int hordeCol = 0;

		int obstacleRow = 2;
		int obstacleCol = 0;

		int expected = 5;

		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);
		ZombieHorde obstacleHorde = new ZombieHorde(obstacleRow, obstacleCol);

		b.getBoardArray()[hordeRow][hordeCol] = horde;
		b.getBoardArray()[obstacleRow][obstacleCol] = obstacleHorde;

		Stack<Node> zombiePath = b.getZombiePath(hordeRow, hordeCol, destinationRow, destinationCol);

		assertEquals(expected, zombiePath.size());
	}

	@Test
	public void testGetZombiePathSurroundedDestination() {
		Board b = new Board();

		int teamRow = 10;
		int teamCol = 15;

		int horde1Row = teamRow - 1;
		int horde1Col = teamCol;

		int horde2Row = teamRow + 1;
		int horde2Col = teamCol;

		int horde3Row = teamRow;
		int horde3Col = teamCol - 1;

		int horde4Row = teamRow;
		int horde4Col = teamCol + 1;

		int moveHordeRow = 10;
		int moveHordeCol = 11;

		Team team = new Team(Team.TYPE_WARRIOR, teamRow, teamCol);
		ZombieHorde horde1 = new ZombieHorde(horde1Row, horde1Col);
		ZombieHorde horde2 = new ZombieHorde(horde2Row, horde2Col);
		ZombieHorde horde3 = new ZombieHorde(horde3Row, horde3Col);
		ZombieHorde horde4 = new ZombieHorde(horde4Row, horde4Col);
		ZombieHorde moveHorde = new ZombieHorde(moveHordeRow, moveHordeCol);

		b.getBoardArray()[teamRow][teamCol] = team;
		b.getBoardArray()[horde1Row][horde1Col] = horde1;
		b.getBoardArray()[horde2Row][horde2Col] = horde2;
		b.getBoardArray()[horde3Row][horde3Col] = horde3;
		b.getBoardArray()[horde4Row][horde4Col] = horde4;
		b.getBoardArray()[moveHordeRow][moveHordeCol] = moveHorde;

		b.getZombiePath(moveHordeRow, moveHordeCol, teamRow, teamCol);
	}

	@Test
	public void testGetEuclid() {
		Board b = new Board();
		int row1 = 5;
		int col1 = 8;
		int row2 = 6;
		int col2 = 10;
		double actual = Math.sqrt(Math.pow(Math.abs(row1 - row2), 2) + Math.pow(Math.abs(col1 - col2), 2));
		assertEquals(b.getEuclid(row1, col1, row2, col2), actual, DELTA);
	}

	@Test
	public void testHasLineOfSight() {
		Board b = new Board();
		int teamRow = 5;
		int teamCol = 5;
		int zombieRow = 10;
		int zombieCol = 10;
		b.getBoardArray()[teamRow][teamCol] = new Team(0);
		b.getBoardArray()[zombieRow][zombieCol] = new ZombieHorde(zombieRow, zombieCol);
		assertTrue(b.hasLineOfSight(teamRow, teamCol, zombieRow, zombieCol));
	}

	@Test
	public void testHasLineOfSightHordeObstacle() {
		Board b = new Board();
		int teamRow = 5;
		int teamCol = 10;

		int hordeObstacleRow = 5;
		int hordeObstacleCol = 12;

		int hordeTargetRow = 5;
		int hordeTargetCol = 13;

		b.getBoardArray()[teamRow][teamCol] = new Team(teamRow, teamCol, Team.TYPE_WARRIOR);
		b.getBoardArray()[hordeObstacleRow][hordeObstacleCol] = new ZombieHorde(hordeObstacleRow, hordeObstacleCol);
		b.getBoardArray()[hordeTargetRow][hordeTargetCol] = new ZombieHorde(hordeTargetRow, hordeTargetCol);

		assertEquals(false, b.hasLineOfSight(teamRow, teamCol, hordeTargetRow, hordeTargetCol));
	}

	@Test
	public void testHasLineOfSightTeamObstacle() {
		Board b = new Board();
		int teamRow = 5;
		int teamCol = 10;

		int teamObstacleRow = 5;
		int teamObstacleCol = 12;

		int hordeTargetRow = 5;
		int hordeTargetCol = 13;

		b.getBoardArray()[teamRow][teamCol] = new Team(teamRow, teamCol, Team.TYPE_WARRIOR);
		b.getBoardArray()[teamObstacleRow][teamObstacleCol] = new Team(teamObstacleRow, teamObstacleCol,
				Team.TYPE_WARRIOR);
		b.getBoardArray()[hordeTargetRow][hordeTargetCol] = new ZombieHorde(hordeTargetRow, hordeTargetCol);

		assertEquals(false, b.hasLineOfSight(teamRow, teamCol, hordeTargetRow, hordeTargetCol));
	}

	@Test
	public void testHasLineOfSightHouseObstacle() {
		Board b = new Board();

		int index = 0;

		Building house = b.getBuildingHolder().getHouses().get(index);

		int houseRow = house.getRow();
		int houseCol = house.getCol();

		int teamRow = houseRow;
		int teamCol = houseCol - 2;

		int hordeTargetRow = houseRow;
		int hordeTargetCol = houseCol + 1;

		b.getBoardArray()[teamRow][teamCol] = new Team(teamRow, teamCol, Team.TYPE_WARRIOR);
		b.getBoardArray()[hordeTargetRow][hordeTargetCol] = new ZombieHorde(hordeTargetRow, hordeTargetCol);

		assertEquals(false, b.hasLineOfSight(teamRow, teamCol, hordeTargetRow, hordeTargetCol));
	}
	
	@Test
	public void testIsInsideBoundsValidValues() {
		Board b = new Board();
		assertTrue(b.isInsideBounds(5, 5));
	}

	@Test
	public void testIsInsideBoundsNegativeRowIndex() {
		Board b = new Board();
		assertFalse(b.isInsideBounds(-1, 0));
	}

	@Test
	public void testIsInsideBoundsNegativeColIndex() {
		Board b = new Board();
		assertFalse(b.isInsideBounds(0, -1));
	}

	@Test
	public void testIsInsideBoundsRowIndexTooLarge() {
		Board b = new Board();
		assertFalse(b.isInsideBounds(b.getBoardArray().length, 0));
	}

	@Test
	public void testIsInsideBoundsColIndexTooLarge() {
		Board b = new Board();
		assertFalse(b.isInsideBounds(0, b.getBoardArray()[0].length));
	}

	@Test
	public void testIsValidDeployAdjacentRight() {
		Board b = new Board();
		for (int i = BuildingHolder.HOME_BASE_TOP_ROW - 1; i < BuildingHolder.HOME_BASE_TOP_ROW
				+ BuildingHolder.NUM_HOME_BASE_SQUARES + 1; i++) {
			if (!b.isValidDeployCoordinate(i, BuildingHolder.HOME_BASE_COL + 1)) {
				fail();
			}
		}
	}

	@Test
	public void testIsValidDeployNoDeployInBase() {
		Board b = new Board();
		for (int i = BuildingHolder.HOME_BASE_TOP_ROW; i < BuildingHolder.HOME_BASE_TOP_ROW
				+ BuildingHolder.NUM_HOME_BASE_SQUARES; i++) {
			if (b.isValidDeployCoordinate(i, BuildingHolder.HOME_BASE_COL)) {
				fail();
			}
		}
	}

	@Test
	public void testIsValidDeployTopSquare() {
		Board b = new Board();
		assertTrue(b.isValidDeployCoordinate(BuildingHolder.HOME_BASE_TOP_ROW - 1, BuildingHolder.HOME_BASE_COL));
	}

	@Test
	public void testIsValidDeployBottomSquare() {
		Board b = new Board();
		boolean result = b.isValidDeployCoordinate(
				BuildingHolder.HOME_BASE_TOP_ROW + BuildingHolder.NUM_HOME_BASE_SQUARES, BuildingHolder.HOME_BASE_COL);
		assertTrue(result);
	}
	
	@Test
	public void testAirStrikeZombieColFail() {
		Board b = new Board();

		ZombieHorde horde1 = new ZombieHorde(8, 5);
		b.getBoardArray()[8][5] = horde1;

		b.airStrikeAttack(0, 10, 5);

		assertEquals(horde1, b.getBoardArray()[8][5]);
	}

	@Test
	public void testAirStrikeZombieRowFail() {
		Board b = new Board();
		
		ZombieHorde horde1 = new ZombieHorde(16, 7);
		b.getBoardArray()[16][7] = horde1;

		b.airStrikeAttack(1, 16, 4);

		assertEquals(horde1, b.getBoardArray()[16][7]);
	}

	@Test
	public void testAirStrikeTeammateColFail() {
		Board b = new Board();

		Team team1 = new Team(Team.TYPE_WARRIOR);
		b.getBoardArray()[8][5] = team1;

		b.airStrikeAttack(0, 10, 5);

		assertEquals(team1, b.getBoardArray()[8][5]);
	}

	@Test
	public void testAirStrikeTeammateRowFail() {
		Board b = new Board();

		Team team1 = new Team(Team.TYPE_WARRIOR);
		b.getBoardArray()[16][9] = team1;

		b.airStrikeAttack(1, 16, 3);

		assertEquals(team1, b.getBoardArray()[16][9]);
	}

	@Test
	public void testAirStrikeZombieAndTeammateRow() {
		Board b = new Board();

		ZombieHorde horde1 = new ZombieHorde(8, 5);
		Team team1 = new Team(Team.TYPE_STEALTH);
		b.getBoardArray()[8][5] = horde1;
		b.getBoardArray()[8][18] = team1;

		b.airStrikeAttack(0, 8, 9);

		assertEquals(null, b.getBoardArray()[8][5]);
		assertEquals(null, b.getBoardArray()[8][18]);
	}

	@Test
	public void testAirStrikeZombieAndTeammateCol() {
		Board b = new Board();

		ZombieHorde horde1 = new ZombieHorde(8, 5);
		Team team1 = new Team(Team.TYPE_STEALTH);
		b.getBoardArray()[8][5] = horde1;
		b.getBoardArray()[16][5] = team1;

		b.airStrikeAttack(1, 10, 5);

		assertEquals(null, b.getBoardArray()[13][5]);
		assertEquals(null, b.getBoardArray()[16][5]);

	}

	@Test
	public void testAirStrikeTeammateCol() {
		Board b = new Board();

		Team team1 = new Team(Team.TYPE_STEALTH);
		b.getBoardArray()[16][5] = team1;

		b.airStrikeAttack(0, 16, 7);

		assertEquals(null, b.getBoardArray()[16][5]);
	}

	@Test
	public void testAirStrikeZombieRow() {
		
		Board b = new Board();

		ZombieHorde horde1 = new ZombieHorde(8, 5);
		b.getBoardArray()[15][7] = horde1;

		b.airStrikeAttack(1, 8, 7);

		assertEquals(null, b.getBoardArray()[15][7]);
	}

}
