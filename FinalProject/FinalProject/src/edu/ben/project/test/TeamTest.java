package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Player;
import edu.ben.project.Team;
import edu.ben.project.Weapon;

public class TeamTest {

	@Test
	public void testAddTeammateToTeamWarrior() {
		Team team = new Team(Team.TYPE_WARRIOR);
		int expected = team.getTeammateCount()[Team.TYPE_WARRIOR] + 1;
		team.addTeammateToTeam(Team.TYPE_WARRIOR);
		int actual = team.getTeammateCount()[Team.TYPE_WARRIOR];
		assertEquals(expected, actual);
	}

	@Test
	public void testAddTeammateToTeamStealth() {
		Team team = new Team(Team.TYPE_STEALTH);
		int expected = team.getTeammateCount()[Team.TYPE_STEALTH] + 1;
		team.addTeammateToTeam(Team.TYPE_STEALTH);
		int actual = team.getTeammateCount()[Team.TYPE_STEALTH];
		assertEquals(expected, actual);
	}

	@Test
	public void testAddTeammateToTeamWarriorReturnTrue() {
		Team team = new Team(Team.TYPE_WARRIOR);
		assertEquals(true, team.addTeammateToTeam(Team.TYPE_WARRIOR));
	}

	@Test
	public void testAddTeammateToTeamWrongTypeWarrior() {
		Team team = new Team(Team.TYPE_WARRIOR);
		assertEquals(false, team.addTeammateToTeam(Team.TYPE_STEALTH));
	}

	@Test
	public void testAddTeammateToTeamWrongTypeStealth() {
		Team team = new Team(Team.TYPE_STEALTH);
		assertEquals(false, team.addTeammateToTeam(Team.TYPE_WARRIOR));
	}

	@Test
	public void testAddTeammateToTeamStealthReturnTrue() {
		Team team = new Team(Team.TYPE_STEALTH);
		assertEquals(true, team.addTeammateToTeam(Team.TYPE_STEALTH));
	}

	@Test
	public void testAddWeaponBat() {
		Team team = new Team(Team.TYPE_WARRIOR);
		int expected = team.getWeaponArray()[Weapon.BASEBALL_BAT] + 1;
		team.addWeapon(Weapon.BASEBALL_BAT);
		int actual = team.getWeaponArray()[Weapon.BASEBALL_BAT];
		assertEquals(expected, actual);
	}

	@Test
	public void testAddWeaponShotgun() {
		Team team = new Team(Team.TYPE_WARRIOR);
		int expected = team.getWeaponArray()[Weapon.SHOTGUN] + 1;
		team.addWeapon(Weapon.SHOTGUN);
		int actual = team.getWeaponArray()[Weapon.SHOTGUN];
		assertEquals(expected, actual);
	}

	@Test
	public void testAddWeaponCrossbow() {
		Team team = new Team(Team.TYPE_STEALTH);
		int expected = team.getWeaponArray()[Weapon.CROSSBOW] + 1;
		team.addWeapon(Weapon.CROSSBOW);
		int actual = team.getWeaponArray()[Weapon.CROSSBOW];
		assertEquals(expected, actual);
	}

	@Test
	public void testAddAmmoBullets() {
		Team team = new Team(Team.TYPE_WARRIOR);
		int expected = team.getAmmoCount() + 5;
		team.addAmmo(Weapon.SET_OF_BULLETS);
		int actual = team.getAmmoCount();
		assertEquals(expected, actual);

	}

	@Test
	public void testAddAmmoArrows() {
		Team team = new Team(Team.TYPE_STEALTH);
		int expected = team.getAmmoCount() + 10;
		team.addAmmo(Weapon.PACK_OF_ARROWS);
		int actual = team.getAmmoCount();
		assertEquals(expected, actual);
	}

	@Test
	public void testCalculatingRangeWarriorsShotGun() {

		// Warrior team
		Team t = new Team(0);

		// Adding five warriors
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);

		// adding 1 bat
		t.addWeapon(0);

		// adding 2 shotguns
		t.addWeapon(2);
		t.addWeapon(2);
		t.addWeapon(2);
		t.addWeapon(2);

		// add ammo
		t.addAmmo(1); // sets of 5 bullets
		t.addAmmo(1);
		t.addAmmo(1);
		t.addAmmo(1);

		int expected = 3; // since shot gun is range 3
		int actual = t.getRange();

		assertEquals(expected, actual);

	}

	/* Test range by warrior with just a bat */
	@Test
	public void testCalculatingRangeWarriorBat() {

		// Warrior team
		Team t = new Team(0);

		// Adding five warriors
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);
		t.addTeammateToTeam(0);

		// adding 1 bat
		t.addWeapon(0);

		int expected = 1; // since bat is range 1
		int actual = t.getRange();

		assertEquals(expected, actual);

	}

	/* Test range by stealth */
	@Test
	public void testCalculatingRangeStealth() {

		// Stealth team
		Team s = new Team(1);

		// Adding five warriors
		s.addTeammateToTeam(1);
		s.addTeammateToTeam(1);

		// adding cross bow
		s.addWeapon(1);

		int expected = 5; // cross bow range is 5
		int actual = s.getRange();

		assertEquals(expected, actual);

	}
		
	@Test
	public void testDeployTeamWithNoTeammates() {
		Player p = new Player();
		
		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		assertEquals(false, t.isTeamDeployable());
	}

	@Test
	public void testDeployTeamWithOneTeammate() {
		Player p = new Player();
		
		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);
		
		assertEquals(true, t.isTeamDeployable());
	}
	
	@Test
	public void testDeployWithSameWeapons() {
		Player p = new Player();
		
		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);
		
		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);
		
		assertEquals(true, t.isTeamDeployable());
	}
	
	@Test
	public void testDeployWithMoreWeapons() {
		Player p = new Player();
		
		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);
		
		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);
		
		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);
		
		assertEquals(false, t.isTeamDeployable());
	}
	
	@Test
	public void testTeamWeaponArrayCrossbow() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();

		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);
		
		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);

		p.createWeapon(Weapon.CROSSBOW);

		assertEquals(2, t.getWeaponArray()[Weapon.CROSSBOW]);
	}

	@Test
	public void testTeamWeaponArrayShotgun() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createWeapon(Weapon.SHOTGUN);
		p.assignWeaponToTeam(t, Weapon.SHOTGUN);
		
		p.createWeapon(Weapon.SHOTGUN);
		p.assignWeaponToTeam(t, Weapon.SHOTGUN);

		p.createWeapon(Weapon.SHOTGUN);

		assertEquals(2, t.getWeaponArray()[Weapon.SHOTGUN]);
	}
	
	@Test
	public void testTeamWeaponArrayBat() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createWeapon(Weapon.BASEBALL_BAT);
		p.assignWeaponToTeam(t, Weapon.BASEBALL_BAT);
		
		p.createWeapon(Weapon.BASEBALL_BAT);
		// return false, only one bat per team
		p.assignWeaponToTeam(t, Weapon.BASEBALL_BAT);
		

		p.createWeapon(Weapon.BASEBALL_BAT);

		assertEquals(1, t.getWeaponArray()[Weapon.BASEBALL_BAT]);
	}
	
	@Test
	public void testTeamTeammateArrayStealth() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_STEALTH);
		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);
		p.assignTeammateToTeam(t);
		
		p.createTeammate(Team.TYPE_STEALTH);

		assertEquals(2, t.getTeammateCount()[Team.TYPE_STEALTH]);
	}
	
	@Test
	public void testTeamTeammateArrayWarrior() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_WARRIOR);
		p.createTeammate(Team.TYPE_WARRIOR);
		p.assignTeammateToTeam(t);
		p.assignTeammateToTeam(t);
		
		p.createTeammate(Team.TYPE_WARRIOR);

		assertEquals(2, t.getTeammateCount()[Team.TYPE_WARRIOR]);
	}
	
	@Test
	public void testTeamAmmoArrayBullets() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();
		
		p.createAmmo(Weapon.SET_OF_BULLETS);
		p.assignAmmunitionToTeam(t, Weapon.SET_OF_BULLETS);
		
		p.createAmmo(Weapon.SET_OF_BULLETS);
		p.assignAmmunitionToTeam(t, Weapon.SET_OF_BULLETS);
		
		p.createAmmo(Weapon.SET_OF_BULLETS);
		

		assertEquals(10, t.getAmmoCount());
	}
	
	@Test
	public void testGlobalAmmoArrayArrows() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		p.createAmmo(Weapon.PACK_OF_ARROWS);
		p.assignAmmunitionToTeam(t, Weapon.PACK_OF_ARROWS);
		
		p.createAmmo(Weapon.PACK_OF_ARROWS);
		

		assertEquals(10, t.getAmmoCount());
	}

}
