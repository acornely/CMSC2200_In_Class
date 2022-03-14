package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Board;
import edu.ben.project.BuildingHolder;

public class BuildingHolderTest {

	@Test
	public void testIsHouse1() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isHouse(7, 20));
	}

	@Test
	public void testIsHouse2() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isHouse(3, 35));
	}

	@Test
	public void testIsHouse3() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isHouse(19, 10));
	}

	@Test
	public void testIsHouse4() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isHouse(15, 25));
	}

	@Test
	public void testIsArmyBase() {
		BuildingHolder bh = new BuildingHolder();
		for (int i = 7; i < 14; i++) {
			if (!bh.isArmyBase(i, 38)) {
				fail();
			}
		}
	}

	@Test
	public void testIsHomeBase() {
		BuildingHolder bh = new BuildingHolder();
		for (int i = 6; i < BuildingHolder.NUM_HOME_BASE_SQUARES; i++) {
			if (!bh.isHomeBase(i, 0)) {
				fail();
			}
		}
	}

	@Test
	public void testIsBuildingForHouse() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isBuilding(7, 20));
	}

	@Test
	public void testIsBuildingNoHouse() {
		BuildingHolder bh = new BuildingHolder();
		assertFalse(bh.isBuilding(15, 20));
	}

	@Test
	public void testIsBuildingArmyBase() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isBuilding(13, 38));
	}

	@Test
	public void testIsBuildingHomeBase() {
		BuildingHolder bh = new BuildingHolder();
		assertTrue(bh.isBuilding(14, 0));
	}

	@Test
	public void testHouseArraySize() {
		BuildingHolder bh = new BuildingHolder();
		assertEquals(4, bh.getHouses().size());
	}

	@Test
	public void testHomeBaseArraySize() {
		BuildingHolder bh = new BuildingHolder();
		assertEquals(BuildingHolder.NUM_HOME_BASE_SQUARES, bh.getHomeBase().size());
	}

	@Test
	public void testArmyBaseArraySize() {
		BuildingHolder bh = new BuildingHolder();
		assertEquals(BuildingHolder.NUM_ARMY_BASE_SQUARES, bh.getArmyBase().size());
	}

	@Test
	public void testIsBuildingAll() {
		BuildingHolder bh = new BuildingHolder();
		Board b = new Board();
		assertTrue(bh.isBuilding(7, 20));
		assertTrue(bh.isBuilding(3, 35));
		assertTrue(bh.isBuilding(19, 10));
		assertTrue(bh.isBuilding(15, 25));

		for (int i = 6; i < 15; i++) {
			if (!bh.isBuilding(i, 0)) {
				fail();
			}
		}

		for (int i = 7; i < 14; i++) {
			if (!bh.isBuilding(i, b.getBoardArray()[0].length - 1)) {
				fail();
			}
		}
	}

}
