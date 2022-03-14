package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Player;
import edu.ben.project.Team;
import edu.ben.project.Weapon;

public class SalaryTest {

	@Test
	public void testSalary() {
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

}
