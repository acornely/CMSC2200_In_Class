package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Player;
import edu.ben.project.Team;
import edu.ben.project.Weapon;

public class PlayerTest {

	@Test
	public void testBuySupplesWarrior() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_WARRIOR);
		p.createTeammate(Team.TYPE_WARRIOR);
		p.createTeammate(Team.TYPE_WARRIOR);

		p.createWeapon(Weapon.BASEBALL_BAT);
		p.createWeapon(Weapon.SHOTGUN);
		p.assignTeammateToTeam(t);
		p.assignTeammateToTeam(t);
		p.createTeammate(Team.TYPE_WARRIOR);

		p.createAmmo(Weapon.SET_OF_BULLETS);
		p.createAmmo(Weapon.SET_OF_BULLETS);

		assertEquals(315, p.getSalary());

	}

	@Test
	public void testBuySuppliesStealth() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);

		p.createTeammate(Team.TYPE_STEALTH);
		p.createTeammate(Team.TYPE_STEALTH);
		p.createTeammate(Team.TYPE_STEALTH);

		p.createWeapon(Weapon.CROSSBOW);
		p.createWeapon(Weapon.CROSSBOW);

		p.createAmmo(Weapon.PACK_OF_ARROWS);
		p.createAmmo(Weapon.PACK_OF_ARROWS);

		assertEquals(330, p.getSalary());

	}

	@Test
	public void testGlobalWeaponArrayCrossbow() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);

		p.createWeapon(Weapon.CROSSBOW);
		p.assignWeaponToTeam(t, Weapon.CROSSBOW);

		p.createWeapon(Weapon.CROSSBOW);

		assertEquals(1, p.getGlobalWeaponArray()[Weapon.CROSSBOW]);
	}

	@Test
	public void testGlobalWeaponArrayShotgun() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);

		p.createWeapon(Weapon.SHOTGUN);
		p.assignWeaponToTeam(t, Weapon.SHOTGUN);

		p.createWeapon(Weapon.SHOTGUN);

		assertEquals(1, p.getGlobalWeaponArray()[Weapon.SHOTGUN]);
	}
	
	@Test
	public void testGlobalWeaponArrayBat() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);

		p.createWeapon(Weapon.BASEBALL_BAT);
		p.assignWeaponToTeam(t, Weapon.BASEBALL_BAT);

		p.createWeapon(Weapon.BASEBALL_BAT);

		assertEquals(1, p.getGlobalWeaponArray()[Weapon.BASEBALL_BAT]);
	}
	
	@Test
	public void testGlobalTeammateArrayStealth() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_STEALTH);
		p.createTeammate(Team.TYPE_STEALTH);
		p.assignTeammateToTeam(t);
		p.assignTeammateToTeam(t);
		
		p.createTeammate(Team.TYPE_STEALTH);

		assertEquals(1, p.getGlobalTeammateArray()[Team.TYPE_STEALTH]);
	}
	
	@Test
	public void testGlobalTeammateArrayWarrior() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();

		p.createTeammate(Team.TYPE_WARRIOR);
		p.createTeammate(Team.TYPE_WARRIOR);
		p.assignTeammateToTeam(t);
		p.assignTeammateToTeam(t);
		
		p.createTeammate(Team.TYPE_WARRIOR);

		assertEquals(1, p.getGlobalTeammateArray()[Team.TYPE_WARRIOR]);
	}
	
	@Test
	public void testGobalAmmoArrayBullets() {
		Player p = new Player();

		p.addTeam(Team.TYPE_WARRIOR);
		Team t = p.getCurrentTeam();
		
		p.createAmmo(Weapon.SET_OF_BULLETS);
		p.assignAmmunitionToTeam(t, Weapon.SET_OF_BULLETS);
		
		p.createAmmo(Weapon.SET_OF_BULLETS);
		

		assertEquals(5, p.getGlobalAmmoArray()[Weapon.SET_OF_BULLETS]);
	}
	
	@Test
	public void testGlobalAmmoArrayArrows() {
		Player p = new Player();

		p.addTeam(Team.TYPE_STEALTH);
		Team t = p.getCurrentTeam();
		
		p.createAmmo(Weapon.PACK_OF_ARROWS);
		p.assignAmmunitionToTeam(t, Weapon.PACK_OF_ARROWS);
		
		p.createAmmo(Weapon.PACK_OF_ARROWS);
		

		assertEquals(10, p.getGlobalAmmoArray()[Weapon.PACK_OF_ARROWS]);
	}
		
}
