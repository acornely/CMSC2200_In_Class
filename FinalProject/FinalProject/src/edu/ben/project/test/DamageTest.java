package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Damage;
import edu.ben.project.Team;
import edu.ben.project.Weapon;
import edu.ben.project.Zombie;
import edu.ben.project.ZombieHorde;

public class DamageTest {

	@Test
	public void testShotgunMinDamage() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 0;
		int numShots = 1;
		int range = 1;
		int roll = 1;
		int expected = 16;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testCrossbowMinDamage() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_STEALTH);
		int numShots = 1;
		int range = 1;
		int roll = 6;
		int expected = 8;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testBatMinDamage() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 1;
		int numShots = 0;
		int range = 1;
		int roll = 1;
		int expected = 16;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testBatMaxDamage() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 1;
		int numShots = 0;
		int range = 1;
		int roll = 6;
		int expected = 96;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateDamageWarriorBatInRangeNoShots() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 1;
		int numShots = 0;
		int range = 1;
		int roll = 1;
		int expected = Weapon.BASE_DAMAGE_BAT * roll + Weapon.BASE_DAMAGE_SHOTGUN * numShots;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateDamageWarriorAllWeaponsInRange() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 1;
		int numShots = 1;
		int range = 1;
		int roll = 1;
		int expected = Weapon.BASE_DAMAGE_BAT * roll + Weapon.BASE_DAMAGE_SHOTGUN * numShots;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateDamageBatOutOfRange() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 1;
		int numShots = 1;
		int range = 2;
		int roll = 6;
		int expected = Weapon.BASE_DAMAGE_SHOTGUN * numShots;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculateDamageShotgunsOnlyBatInRange() {
		Damage dm = new Damage();
		Team t = new Team(Team.TYPE_WARRIOR);
		t.getWeaponArray()[Weapon.BASEBALL_BAT] = 0;
		int numShots = 3;
		int range = 1;
		int roll = 1;
		int expected = Weapon.BASE_DAMAGE_SHOTGUN * numShots;
		int actual = dm.calculateDamage(t, numShots, range, roll);
		assertEquals(expected, actual);
	}

	@Test
	public void testApplyDamageKillsOnly() {
		Damage dm = new Damage();
		ZombieHorde horde = new ZombieHorde(0, 0);
		int numKills = 3;
		int damage = Zombie.BASE_HEALTH * numKills;
		dm.applyDamage(horde, damage);
		int expected = ZombieHorde.INITIAL_ZOMBIE_COUNT - numKills;
		int actual = horde.getZombies().size();
		assertEquals(expected, actual);
	}

	@Test
	public void testApplyDamagePartialDamage() {
		Damage dm = new Damage();
		ZombieHorde horde = new ZombieHorde(0, 0);
		int numKills = 3;
		int extraDamage = 4;
		int damage = Zombie.BASE_HEALTH * numKills + extraDamage;
		dm.applyDamage(horde, damage);
		int expected = Zombie.BASE_HEALTH - extraDamage;
		int actual = horde.getZombies().get(0).getHealth();
		assertEquals(expected, actual);
	}

	@Test
	public void testApplyDamagePreDamagedHorde() {
		Damage dm = new Damage();
		ZombieHorde horde = new ZombieHorde(0, 0);
		int size = horde.getZombies().size();
		int partialDamage = 0;
		int numKills = 3;
		int healthOffest = 3;
		for (int i = 0; i < numKills; i++) {
			int health = horde.getZombies().get(i).getHealth();
			horde.getZombies().get(i).setHealth(health - healthOffest);
			partialDamage += healthOffest;
			healthOffest--;
		}

		int damage = Zombie.BASE_HEALTH * numKills + partialDamage;

		dm.applyDamage(horde, damage);
		int expected = size - (damage / Zombie.BASE_HEALTH);
		int actual = horde.getZombies().size();
		assertEquals(expected, actual);
	}

	@Test
	public void testApplyDamageHordeDestroyed() {
		Damage dm = new Damage();
		ZombieHorde horde = new ZombieHorde(0, 0);
		int damage = 9001;
		assertTrue(dm.applyDamage(horde, damage));
	}

	@Test
	public void testApplyDamageHordeSurvives() {
		Damage dm = new Damage();
		ZombieHorde horde = new ZombieHorde(0, 0);
		int damage = 1;
		assertFalse(dm.applyDamage(horde, damage));
	}

}
