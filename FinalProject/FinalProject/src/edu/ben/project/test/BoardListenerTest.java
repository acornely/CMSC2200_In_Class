package edu.ben.project.test;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.junit.Test;

import edu.ben.project.Board;
import edu.ben.project.BoardListener;
import edu.ben.project.Team;

public class BoardListenerTest {

	@Test
	public void testMovement() {
//		Board b = new Board();
//		while (b.getDie().getCurrentRoll() < 4) {
//			b.getDie().roll();
//		}
//		int row = 2;
//		int col = 2;
//		int moveRow = 4;
//		int moveCol = 4;
//
//		b.getBoard()[row][col] = new Team(row, col, 0);
//
//		JButton[][] buttons = new JButton[b.getBoard().length][b.getBoard()[0].length];
//		BoardListener[][] listeners = new BoardListener[buttons.length][buttons[0].length];
//		for (int i = 0; i < buttons.length; i++) {
//			for (int j = 0; j < buttons[i].length; j++) {
//				buttons[i][j] = new JButton("");
//				listeners[i][j] = new BoardListener(b, i, j, buttons, new JLabel(""), new JButton(""), new JButton(""));
//				buttons[i][j].addActionListener(listeners[i][j]);
//			}
//		}
//		b.setMoveEnabled(true);
//		b.setTeamSelected(false);
//		buttons[row][col].doClick();
//		b.setTeamSelected(true);
//		b.setDestinationSelectable(true);
//		buttons[moveRow][moveCol].doClick();
//
//		assertEquals(null, b.getBoard()[row][col]);
	}

}
