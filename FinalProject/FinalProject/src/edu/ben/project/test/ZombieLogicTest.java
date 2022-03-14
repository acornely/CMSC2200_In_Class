package edu.ben.project.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JButton;

import org.junit.Test;

import edu.ben.project.Board;
import edu.ben.project.Building;
import edu.ben.project.Die;
import edu.ben.project.Game;
import edu.ben.project.Group;
import edu.ben.project.Node;
import edu.ben.project.Team;
import edu.ben.project.ZombieHorde;
import edu.ben.project.ZombieLogic;
import student.TestCase;

public class ZombieLogicTest extends TestCase {

	@Test
	public void randomMoveTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		game.setBoard(b);

		int row = 1;
		int col = 2;
		ZombieHorde z = new ZombieHorde(row, col);
		game.getBoard().getBoardArray()[z.getRow()][z.getCol()] = z;
		zl.randomMove(z);
		assertEquals(null, game.getBoard().getBoardArray()[row][col]);
	}

	@Test
	public void randomMoveTestCorner() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row = 0;
		int col = 0;
		ZombieHorde z = new ZombieHorde(row, col);
		game.getBoard().getBoardArray()[z.getRow()][z.getCol()] = z;
		zl.randomMove(z);
		assertEquals(null, game.getBoard().getBoardArray()[row][col]);
	}

	@Test
	public void randomMoveMergeTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row = 10;
		int col = 10;
		int end = 7;
		int middle = 13;

		for (int i = row; i < row + end; i++) {
			for (int j = col; j < col + end; j++) {
				game.getBoard().getBoardArray()[i][j] = new ZombieHorde(i, j);
			}
		}
		zl.randomMove((ZombieHorde) game.getBoard().getBoardArray()[middle][middle]);
		assertEquals(null, game.getBoard().getBoardArray()[middle][middle]);
	}

	@Test
	public void randomMoveMergedHordeLargerTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row = 10;
		int col = 10;
		int end = 7;
		int middle = 13;
		boolean foundLargeHorde = false;

		for (int i = row; i < row + end; i++) {
			for (int j = col; j < col + end; j++) {
				game.getBoard().getBoardArray()[i][j] = new ZombieHorde(i, j);
			}
		}
		zl.randomMove((ZombieHorde) game.getBoard().getBoardArray()[middle][middle]);
		for (int i = row; i < row + end; i++) {
			for (int j = col; j < col + end; j++) {
				ZombieHorde zh = (ZombieHorde) game.getBoard().getBoardArray()[i][j];
				if (game.getBoard().getBoardArray()[i][j] != null && zh.getZombies().size() > 10) {
					foundLargeHorde = true;
					break;
				}
			}
		}

		assertTrue(foundLargeHorde);
	}

	@Test
	public void randomMoveSurroundedTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row = 10;
		int col = 10;
		int end = 7;
		int middle = 13;
		for (int i = row; i < row + end; i++) {
			for (int j = col; j < col + end; j++) {
				game.getBoard().getBoardArray()[i][j] = new Team(0);
			}
		}
		game.getBoard().getBoardArray()[middle][middle] = new ZombieHorde(middle, middle);
		assertFalse(zl.randomMove((ZombieHorde) game.getBoard().getBoardArray()[middle][middle]));

	}

	@Test
	public void randomMoveSurroundedNearBuildingTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row = 4;
		int col = 0;
		int zomRow = 5;
		int zomCol = 0;
		int endRow = 6;
		int endCol = 1;
		for (int i = row; i <= endRow; i++) {
			for (int j = col; j <= endCol; j++) {
				if (!game.getBoard().getBuildingHolder().isBuilding(i, j)) {
					game.getBoard().getBoardArray()[i][j] = new Team(0);
				}
			}
		}

		game.getBoard().getBoardArray()[zomRow][zomCol] = new ZombieHorde(zomRow, zomCol);

		assertFalse(zl.randomMove((ZombieHorde) game.getBoard().getBoardArray()[zomRow][zomCol]));

	}

	@Test
	public void testMergeOldZombieHordeIsNull() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row1 = 0;
		int col1 = 0;
		int row2 = 5;
		int col2 = 5;
		ZombieHorde z1 = new ZombieHorde(row1, col1);
		ZombieHorde z2 = new ZombieHorde(row2, col2);
		game.getBoard().getBoardArray()[row1][col1] = z1;
		game.getBoard().getBoardArray()[row2][col2] = z2;
		zl.merge(z1, z2);
		assertEquals(null, game.getBoard().getBoardArray()[row2][col2]);
	}

	@Test
	public void testMergeZombiesTransferred() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		int row1 = 0;
		int col1 = 0;
		int row2 = 5;
		int col2 = 5;
		ZombieHorde z1 = new ZombieHorde(row1, col1);
		ZombieHorde z2 = new ZombieHorde(row2, col2);
		game.getBoard().getBoardArray()[row1][col1] = z1;
		game.getBoard().getBoardArray()[row2][col2] = z2;
		zl.merge(z1, z2);
		assertEquals(20, z1.getZombies().size());
	}

	@Test
	public void intializeZombiesTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);

		zl.initializeZombieHordes();
		assertEquals(10, zl.getZombieList().size());
	}

	@Test
	public void initializeZombiesNoHordesInBuildingsTest() {
		for (int i = 0; i < 1000; i++) {
			Game game = new Game();
			Board b = new Board();
			game.setBoard(b);

			ZombieLogic zl = new ZombieLogic(game);
			zl.initializeZombieHordes();
			for (int j = 0; j < game.getBoard().getBoardArray().length; j++) {
				for (int k = 0; k < game.getBoard().getBoardArray()[0].length; k++) {
					if (game.getBoard().getBoardArray()[j][k] instanceof ZombieHorde
							&& game.getBoard().getBuildingHolder().isBuilding(j, k)) {
						fail();
					}
				}
			}
		}
	}

	@Test
	public void isTeamTrueTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		Team test = new Team(0);
		board[1][1] = test;
		assertEquals(zl.isTeam(1, 1), true);

	}

	@Test
	public void isTeamFalseTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(1, 1);
		board[1][1] = test;
		assertEquals(zl.isTeam(1, 1), false);

	}

	@Test
	public void findTargetsTrueTwelveTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(13, 13);
		ArrayList<ZombieHorde> testList = new ArrayList<ZombieHorde>();
		testList.add(test);
		zl.setZombieList(testList);
		Team testTarget = new Team(0);
		board[13][12] = testTarget;
		board[13][13] = test;
		zl.findTargets();
		Integer[] correct = { 13, 12 };
		assertTrue(Arrays.equals(correct, zl.getZombieList().get(0).getAttackTarget()));

	}

	@Test
	public void findTargetsTrueThreeTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(13, 13);
		ArrayList<ZombieHorde> testList = new ArrayList<ZombieHorde>();
		testList.add(test);
		zl.setZombieList(testList);
		Team testTarget = new Team(0);
		board[14][13] = testTarget;
		board[13][13] = test;
		zl.findTargets();
		Integer[] correct = { 14, 13 };
		assertTrue(Arrays.equals(correct, zl.getZombieList().get(0).getAttackTarget()));

	}

	@Test
	public void findTargetsTrueSixTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(13, 13);
		ArrayList<ZombieHorde> testList = new ArrayList<ZombieHorde>();
		testList.add(test);
		zl.setZombieList(testList);
		Team testTarget = new Team(0);
		board[13][14] = testTarget;
		board[13][13] = test;
		zl.findTargets();
		Integer[] correct = { 13, 14 };
		assertTrue(Arrays.equals(correct, zl.getZombieList().get(0).getAttackTarget()));

	}

	@Test
	public void findTargetsTrueNine() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(13, 13);
		ArrayList<ZombieHorde> testList = new ArrayList<ZombieHorde>();
		testList.add(test);
		zl.setZombieList(testList);
		Team testTarget = new Team(0);
		board[12][13] = testTarget;
		board[13][13] = test;
		zl.findTargets();
		Integer[] correct = { 12, 13 };
		assertTrue(Arrays.equals(correct, zl.getZombieList().get(0).getAttackTarget()));

	}

	@Test
	public void findTargetsNoneTest() {
		Game game = new Game();
		Board b = new Board();
		game.setBoard(b);

		ZombieLogic zl = new ZombieLogic(game);
		Group[][] board = game.getBoard().getBoardArray();
		ZombieHorde test = new ZombieHorde(13, 13);
		ArrayList<ZombieHorde> testList = new ArrayList<ZombieHorde>();
		testList.add(test);
		zl.setZombieList(testList);
		board[13][13] = test;
		zl.findTargets();
		Integer[] correct = null;
		assertTrue(Arrays.equals(correct, zl.getZombieList().get(0).getAttackTarget()));
	}

	@Test
	public void testScanForNoiseRadius() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(5, 5);
		g.getBoard().getBoardArray()[5][5] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[4][5] = warrior1;
		warrior1.setRow(4);
		warrior1.setCol(5);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 4, 5 };

		assertArrayEquals(expectedDestination, actualDestination);

	}

	@Test
	public void scanForNoiseRadiusLeftMostValid() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(11, 15);
		g.getBoard().getBoardArray()[11][15] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);

	}

	@Test
	public void testScanForNoiseRadiusLeftMostInvalid() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(10, 15);
		g.getBoard().getBoardArray()[10][15] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getAttackTarget();
		Integer[] expectedDestination = null;

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testScanForNoiseRadiusTopMostValid() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(19, 15);
		g.getBoard().getBoardArray()[19][15] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testScanForSoundRadiusTopMostInvalid() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(20, 15);
		g.getBoard().getBoardArray()[20][15] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getAttackTarget();
		Integer[] expectedDestination = null;

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testNoiseTopLeftDiagonalBottom() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(12, 14);
		g.getBoard().getBoardArray()[12][14] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testNoiseTopLeftDiagonalMiddle() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(13, 13);
		g.getBoard().getBoardArray()[13][13] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testNoiseTopLeftDiagonalTop() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(14, 12);
		g.getBoard().getBoardArray()[14][12] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);

	}

	@Test
	public void testNoiseTopRightDiagonalTop() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(16, 12);
		g.getBoard().getBoardArray()[16][12] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void tesNoiseTopRightDiagonalMiddle() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(17, 13);
		g.getBoard().getBoardArray()[17][13] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testNoiseTopRightDiagonalBottom() {
		Game g = new Game();
		Board b = new Board();
		g.setBoard(b);

		Team warrior1 = new Team(Team.TYPE_WARRIOR);
		ZombieHorde horde1 = new ZombieHorde(18, 14);
		g.getBoard().getBoardArray()[18][14] = horde1;

		ZombieLogic zl = new ZombieLogic(g);

		g.getBoard().getBoardArray()[15][15] = warrior1;
		warrior1.setRow(15);
		warrior1.setCol(15);

		zl.scanForNoiseRadius(warrior1.getRow(), warrior1.getCol(), 5);

		Integer[] actualDestination = horde1.getSoundTarget();
		Integer[] expectedDestination = { 15, 15 };

		assertArrayEquals(expectedDestination, actualDestination);
	}

	@Test
	public void testZombiePursuitPathToHouse() {
		Game game = new Game();
		game.setBoard(new Board());
		game.setLogic(new ZombieLogic(game));
		ArrayList<Building> houses = game.getLogic().getBoard().getBuildingHolder().getHouses();

		int index = 0;

		int houseRow = houses.get(index).getRow();
		int houseCol = houses.get(index).getCol();

		int teamRow = houseRow;
		int teamCol = houseCol;

		int hordeRow = houseRow;
		int hordeCol = houseCol - 2;

		Team team1 = new Team(Team.TYPE_WARRIOR, teamRow, teamCol);
		game.getBoard().getBoardArray()[teamRow][teamCol] = team1;

		ZombieHorde horde = new ZombieHorde(hordeRow, hordeCol);
		horde.setSoundTarget(new Integer[] { teamRow, teamCol });
		game.getBoard().getBoardArray()[hordeRow][hordeCol] = horde;

		// game.logic.zombieSoundPursuit(teamRow, teamCol, horde);
		Stack<Node> zombiePath = game.getBoard().getZombiePath(horde.getRow(), horde.getCol(), teamRow, teamCol);
		Integer[] actuals = { zombiePath.get(0).getRow(), zombiePath.get(0).getCol() };

		assertArrayEquals(new Integer[] { houseRow, houseCol - 1 }, actuals);
	}

	@Test
	public void testZombiePursuitTeamNearEmptyHouse() {
		Game game = new Game();
		Board b = new Board();
		JButton[][] buttons = new JButton[b.NUM_ROWS][b.NUM_COLS];
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j] = new JButton();
			}
		}
		b.setButtonArray(buttons);
		game.setBoard(b);
		game.setLogic(new ZombieLogic(game));
		ArrayList<Building> houses = game.getLogic().getBoard().getBuildingHolder().getHouses();

		ArrayList<ZombieHorde> hordes = new ArrayList<>();

		int index = 0;

		int houseRow = houses.get(index).getRow();
		int houseCol = houses.get(index).getCol();

		int teamRow = houseRow + 1;
		int teamCol = houseCol;

		int horde1Row = houseRow;
		int horde1Col = houseCol + 1;

		int horde2Row = houseRow - 1;
		int horde2Col = houseCol;

		int horde3Row = houseRow;
		int horde3Col = houseCol - 1;

		int horde4Row = houseRow;
		int horde4Col = houseCol - 2;

		ZombieHorde horde1 = new ZombieHorde(horde1Row, horde1Col);
		ZombieHorde horde2 = new ZombieHorde(horde2Row, horde2Col);
		ZombieHorde horde3 = new ZombieHorde(horde3Row, horde3Col);
		ZombieHorde horde4 = new ZombieHorde(horde4Row, horde4Col);

		hordes.add(horde1);
		hordes.add(horde2);
		hordes.add(horde3);
		hordes.add(horde4);

		Team team1 = new Team(Team.TYPE_WARRIOR, teamRow, teamCol);

		game.getBoard().getBoardArray()[teamRow][teamCol] = team1;

		b.getBoardArray()[horde1Row][horde1Col] = horde1;
		b.getBoardArray()[horde2Row][horde2Col] = horde2;
		b.getBoardArray()[horde3Row][horde3Col] = horde3;
		b.getBoardArray()[horde4Row][horde4Col] = horde4;

		b.updateBoard();

		Stack<Node> teamPath = b.getPath(houseRow, houseCol, houseRow + 1, houseCol);

		for (int i = 0; i < hordes.size(); i++) {
			ZombieHorde tempHorde = hordes.get(i);
			tempHorde.setSoundTarget(new Integer[] { teamRow, teamCol });

		}

		game.getLogic().checkMoveNoiseRadius(teamPath, team1);
		for (int i = 0; i < hordes.size(); i++) {
			game.getLogic().zombieSoundPursuit(new Die(), teamRow, teamCol, hordes.get(i));
		}

		assertEquals(null, b.getBoardArray()[houseRow][houseCol]);
	}

}
