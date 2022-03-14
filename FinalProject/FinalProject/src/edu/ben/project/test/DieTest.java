package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Die;

public class DieTest {

	@Test
	public void testRollNumberLimitsValid() {
		Die d = new Die();
		for (int i = 0; i < 1000; i++) {
			int roll = d.roll();
			if (roll < 1 || roll > 6) {
				fail();
			}
		}
	}

	@Test
	public void testHalfRollLimitsValid() {
		Die d = new Die();
		for (int i = 0; i < 1000; i++) {
			int roll = d.halfRoll();
			if (roll < 1 || roll > 3) {
				fail();
			}
		}
	}

	@Test
	public void testGetCurrentRollMatchesLastRoll() {
		Die d = new Die();
		int expected = 2;
		int roll = d.roll();
		while (roll != expected) {
			roll = d.roll();
		}
		int actual = d.getCurrentRoll();
		assertEquals(expected, actual);
	}

}
