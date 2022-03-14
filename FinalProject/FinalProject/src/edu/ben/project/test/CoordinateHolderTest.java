package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.CoordinateHolder;
import edu.ben.project.Team;

public class CoordinateHolderTest {

	@Test
	public void testReset() {
		CoordinateHolder holder = new CoordinateHolder();
		Team team = new Team(Team.TYPE_WARRIOR);
		holder.setCurrentGroup(team);
		holder.reset();
		assertEquals(null, holder.getCurrentGroup());
	}

}
