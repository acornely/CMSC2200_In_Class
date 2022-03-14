package edu.ben.project.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ben.project.Board;
import edu.ben.project.Game;
import edu.ben.project.ZombieHorde;
import edu.ben.project.ZombieLogic;

public class ZombieHordeTest {

	@Test
	public void test() {
		Game game = new Game();
		ZombieLogic zl = new ZombieLogic(game);
		Board board = new Board();
		game.setBoard(board);
		game.setLogic(zl);
		game.getLogic().setB(board);
		ZombieHorde zh1 = new ZombieHorde(0, 0);
		ZombieHorde zh2 = new ZombieHorde(1, 1);
		zh2.getZombies().get(0).setHealth(10);
		game.getBoard().getBoardArray()[0][0] = zh1;
		game.getBoard().getBoardArray()[1][1] = zh2;
		zl.merge(zh1, zh2);
		assertEquals(10, zh1.getZombies().get(0).getHealth());
	}

}
