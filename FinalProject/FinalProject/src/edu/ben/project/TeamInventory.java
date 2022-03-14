package edu.ben.project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class TeamInventory extends JPanel {

	private JPanel teamButtons;
	private JPanel teamInventory;
	private JPanel addTeam;
	private JPanel teamInventoryCount;

	private JButton warrior;
	private JButton stealth;
	private JButton teammate;
	private JButton addBat;
	private JButton addShotgun;
	private JButton addCrossbow;
	private JButton addBullets;
	private JButton addArrows;
	private JButton deployTroops;
	private JButton resetTeam;

	private JLabel teammateInventory;
	private JLabel batInventory;
	private JLabel shotgunInventory;
	private JLabel crossbowInventory;
	private JLabel bulletInventory;
	private JLabel arrowInventory;

	Player player;
	Store store;
	Board b;
	JButton[][] buttonArray;
	Game game;

	public TeamInventory(Player p, Store s, Board b, JButton[][] buttonArray, Game game) {
		this.player = p;
		this.store = s;
		this.b = b;
		this.buttonArray = buttonArray;
		this.game = game;

		setVisible(false);

		setLayout(new BorderLayout());

		teamButtons = new JPanel();
		teamButtons.setLayout(new GridLayout(1, 1));
		teamButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(teamButtons, BorderLayout.NORTH);

		teamInventory = new JPanel();
		teamInventory.setLayout(new GridLayout(6, 1));
		teamInventory.setBorder(new EmptyBorder(15, 15, 15, 15));
		add(teamInventory, BorderLayout.WEST);

		addTeam = new JPanel();
		addTeam.setLayout(new GridLayout(1, 1));
		addTeam.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(addTeam, BorderLayout.SOUTH);

		teamInventoryCount = new JPanel();
		teamInventoryCount.setLayout(new GridLayout(6, 1));
		add(teamInventoryCount, BorderLayout.EAST);

		warrior = new JButton("Create a Warrior Team");
		warrior.addActionListener(new warriorListener());
		game.getLog().gameLogs("Player created a warrior team.");
		teamButtons.add(warrior);

		stealth = new JButton("Create a Stealth Team");
		stealth.addActionListener(new stealthListener());
		game.getLog().gameLogs("Player created a stealth team.");
		teamButtons.add(stealth);

		teammate = new JButton("Add a Teammate");
		teammate.setEnabled(false);
		teammate.addActionListener(new addTeammateListener());
		game.getLog().gameLogs("Player added a teammate to a team.");
		teamInventory.add(teammate);

		addBat = new JButton("Add a Bat");
		addBat.setEnabled(false);
		addBat.addActionListener(new addBatListener());
		game.getLog().gameLogs("Player added a bat to a team.");
		teamInventory.add(addBat);

		addShotgun = new JButton("Add a Shotgun");
		addShotgun.setEnabled(false);
		addShotgun.addActionListener(new addShotgunListener());
		game.getLog().gameLogs("Player added a shotgun to a team.");
		teamInventory.add(addShotgun);

		addCrossbow = new JButton("Add a Crossbow");
		addCrossbow.setEnabled(false);
		addCrossbow.addActionListener(new addCrossbowListener());
		game.getLog().gameLogs("Player added a crossbow to a team.");
		teamInventory.add(addCrossbow);

		addBullets = new JButton("Add Bullets");
		addBullets.setEnabled(false);
		addBullets.addActionListener(new addBulletsListener());
		game.getLog().gameLogs("Player added a pack of 10 bullets to a team.");
		teamInventory.add(addBullets);

		addArrows = new JButton("Add Arrows");
		addArrows.setEnabled(false);
		addArrows.addActionListener(new addArrowListener());
		game.getLog().gameLogs("Player added 10 arrows to a team.");
		teamInventory.add(addArrows);

		teammateInventory = new JLabel();
		teammateInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		teammateInventory.setOpaque(true);
		teammateInventory.setText("0");
		teamInventoryCount.add(teammateInventory);

		batInventory = new JLabel();
		batInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		batInventory.setOpaque(true);
		batInventory.setText("0");
		teamInventoryCount.add(batInventory);

		shotgunInventory = new JLabel();
		shotgunInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		shotgunInventory.setOpaque(true);
		shotgunInventory.setText("0");
		teamInventoryCount.add(shotgunInventory);

		crossbowInventory = new JLabel();
		crossbowInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		crossbowInventory.setOpaque(true);
		crossbowInventory.setText("0");
		teamInventoryCount.add(crossbowInventory);

		bulletInventory = new JLabel();
		bulletInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		bulletInventory.setOpaque(true);
		bulletInventory.setText("0");
		teamInventoryCount.add(bulletInventory);

		arrowInventory = new JLabel();
		arrowInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		arrowInventory.setOpaque(true);
		arrowInventory.setText("0");
		teamInventoryCount.add(arrowInventory);

		deployTroops = new JButton("Deploy Team");
		deployTroops.setEnabled(false);
		deployTroops.addActionListener(new deployTeamListener());
		addTeam.add(deployTroops);

		resetTeam = new JButton("Reset Team");
		resetTeam.setEnabled(false);
		resetTeam.addActionListener(new resetTeamListener());
		addTeam.add(resetTeam);

	}

	private class warriorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addBat.setEnabled(true);
			addShotgun.setEnabled(true);
			teammate.setEnabled(true);
			addBullets.setEnabled(true);
			resetTeam.setEnabled(true);
			addCrossbow.setEnabled(false);
			addArrows.setEnabled(false);
			stealth.setEnabled(false);
			warrior.setEnabled(false);

			player.addTeam(Team.TYPE_WARRIOR);

			displayNewTeamInventory();

			b.setDeployable(true);

		}
	}

	private class stealthListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addBat.setEnabled(false);
			addShotgun.setEnabled(false);
			teammate.setEnabled(true);
			addBullets.setEnabled(false);
			addCrossbow.setEnabled(true);
			addArrows.setEnabled(true);
			warrior.setEnabled(false);
			resetTeam.setEnabled(true);

			player.addTeam(Team.TYPE_STEALTH);

			displayNewTeamInventory();

			b.setDeployable(true);
		}

	}

	private class addTeammateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Team t = player.getCurrentTeam();
			player.assignTeammateToTeam(t);

			int[] globalTeammateCount = player.getGlobalTeammateArray();
			int[] teammateList = t.getTeammateCount();

			String teamCountString = String.valueOf(teammateList[player.getCurrentTeam().getType()]);
			String globalTeamCountString = String.valueOf(globalTeammateCount[player.getCurrentTeam().getType()]);

			teammateInventory.setText(teamCountString);
			if (player.getCurrentTeam().getType() == Team.TYPE_STEALTH) {
				store.getStealthInventory().setText(globalTeamCountString);
			} else {
				store.getWarriorInventory().setText(globalTeamCountString);
			}

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}

		}

	}

	private class addBatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Team t = player.getCurrentTeam();
			player.assignWeaponToTeam(t, Weapon.BASEBALL_BAT);

			int[] globalWeaponArray = player.getGlobalWeaponArray();
			int[] teamWeaponArray = t.getWeaponArray();

			String teamBatCount = String.valueOf(teamWeaponArray[Weapon.BASEBALL_BAT]);
			String globalWeaponCount = String.valueOf(globalWeaponArray[Weapon.BASEBALL_BAT]);

			batInventory.setText(teamBatCount);
			store.getBatInventory().setText(globalWeaponCount);

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}
		}

	}

	private class addShotgunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			Team t = player.getCurrentTeam();
			player.assignWeaponToTeam(t, Weapon.SHOTGUN);

			int[] globalWeaponArray = player.getGlobalWeaponArray();
			int[] teamWeaponArray = t.getWeaponArray();

			String teamShotgunCount = String.valueOf(teamWeaponArray[Weapon.SHOTGUN]);
			String globalShotgunCount = String.valueOf(globalWeaponArray[Weapon.SHOTGUN]);

			shotgunInventory.setText(teamShotgunCount);
			store.getShotgunInventory().setText(globalShotgunCount);

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}

		}

	}

	private class addCrossbowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			Team t = player.getCurrentTeam();
			player.assignWeaponToTeam(t, Weapon.CROSSBOW);

			int[] globalWeaponArray = player.getGlobalWeaponArray();
			int[] teamWeaponArray = t.getWeaponArray();

			String teamCrossbowCount = String.valueOf(teamWeaponArray[Weapon.CROSSBOW]);
			String globalCrossbowCount = String.valueOf(globalWeaponArray[Weapon.CROSSBOW]);

			crossbowInventory.setText(teamCrossbowCount);
			store.getCrossbowInventory().setText(globalCrossbowCount);

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}

		}

	}

	private class addBulletsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Team t = player.getCurrentTeam();
			player.assignAmmunitionToTeam(t, Weapon.SET_OF_BULLETS);

			int[] globalAmmoArray = player.getGlobalAmmoArray();

			String teamBulletCount = String.valueOf(t.getAmmoCount());
			String globalBulletCount = String.valueOf(globalAmmoArray[Weapon.SET_OF_BULLETS]);

			bulletInventory.setText(teamBulletCount);
			store.getBulletInventory().setText(globalBulletCount);

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}

		}

	}

	private class addArrowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Team t = player.getCurrentTeam();
			player.assignAmmunitionToTeam(t, Weapon.PACK_OF_ARROWS);

			int[] globalAmmoArray = player.getGlobalAmmoArray();

			String teamAmmoCount = String.valueOf(t.getAmmoCount());
			String globalAmmoCount = String.valueOf(globalAmmoArray[Weapon.PACK_OF_ARROWS]);

			arrowInventory.setText(teamAmmoCount);
			store.getArrowInventory().setText(globalAmmoCount);

			if (t.isTeamDeployable()) {
				deployTroops.setEnabled(true);
			} else if (!t.isTeamDeployable()) {
				deployTroops.setEnabled(false);
			}

		}

	}

	private class deployTeamListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addBat.setEnabled(false);
			addShotgun.setEnabled(false);
			teammate.setEnabled(false);
			addBullets.setEnabled(false);
			addCrossbow.setEnabled(false);
			addArrows.setEnabled(false);
			warrior.setEnabled(true);
			stealth.setEnabled(true);
			deployTroops.setEnabled(false);
			resetTeam.setEnabled(false);

			player.getDeployedTeamInventory().add(player.getCurrentTeam());
			b.highlightDeployArea(GUI.getDeployColor());
		}

	}

	private class resetTeamListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addBat.setEnabled(false);
			addShotgun.setEnabled(false);
			teammate.setEnabled(false);
			addBullets.setEnabled(false);
			addCrossbow.setEnabled(false);
			addArrows.setEnabled(false);
			warrior.setEnabled(true);
			stealth.setEnabled(true);
			deployTroops.setEnabled(false);
			resetTeam.setEnabled(false);

			Team t = player.getCurrentTeam();

			int[] globalTeammateArray = player.getGlobalTeammateArray();
			int[] globalAmmoArray = player.getGlobalAmmoArray();
			int[] globalWeaponArray = player.getGlobalWeaponArray();

			int[] teammateArray = t.getTeammateCount();

			int[] weaponArray = t.getWeaponArray();

			// take all the values in the team inventory and set them to the
			// global inventory
			globalWeaponArray[Weapon.BASEBALL_BAT] += weaponArray[Weapon.BASEBALL_BAT];
			globalWeaponArray[Weapon.CROSSBOW] += weaponArray[Weapon.CROSSBOW];
			globalWeaponArray[Weapon.SHOTGUN] += weaponArray[Weapon.SHOTGUN];

			// add bullets or arrows depending on team type
			if (t.getType() == Team.TYPE_WARRIOR) {
				globalAmmoArray[Weapon.SET_OF_BULLETS] += t.getAmmoCount();
			} else {
				globalAmmoArray[Weapon.PACK_OF_ARROWS] += t.getAmmoCount();
			}

			globalTeammateArray[player.getCurrentTeam().getType()] += teammateArray[player.getCurrentTeam().getType()];

			// set everything in the team inventory and set it to 0
			weaponArray[Weapon.BASEBALL_BAT] = 0;
			weaponArray[Weapon.CROSSBOW] = 0;
			weaponArray[Weapon.SHOTGUN] = 0;

			t.setAmmoCount(0);

			teammateArray[player.getCurrentTeam().getType()] = 0;

			// convert all of the values to strings
			String globalBat = String.valueOf(globalWeaponArray[Weapon.BASEBALL_BAT]);
			String teamBat = String.valueOf(weaponArray[Weapon.BASEBALL_BAT]);

			String globalCrossbow = String.valueOf(globalWeaponArray[Weapon.CROSSBOW]);
			String teamCrossbow = String.valueOf(weaponArray[Weapon.CROSSBOW]);

			String globalShotgun = String.valueOf(globalWeaponArray[Weapon.SHOTGUN]);
			String teamShotgun = String.valueOf(weaponArray[Weapon.SHOTGUN]);

			String globalArrows = String.valueOf(globalAmmoArray[Weapon.PACK_OF_ARROWS]);
			String teamArrows = String.valueOf(t.getAmmoCount());

			String globalBullets = String.valueOf(globalAmmoArray[Weapon.SET_OF_BULLETS]);
			String teamBullets = String.valueOf(t.getAmmoCount());

			String globalTeammate = String.valueOf(globalTeammateArray[player.getCurrentTeam().getType()]);
			String teamTeammate = String.valueOf(teammateArray[player.getCurrentTeam().getType()]);

			store.getBatInventory().setText(globalBat);
			batInventory.setText(teamBat);

			store.getCrossbowInventory().setText(globalCrossbow);
			crossbowInventory.setText(teamCrossbow);

			store.getShotgunInventory().setText(globalShotgun);
			shotgunInventory.setText(teamShotgun);

			store.getArrowInventory().setText(globalArrows);
			arrowInventory.setText(teamArrows);

			store.getBulletInventory().setText(globalBullets);
			bulletInventory.setText(teamBullets);

			teammateInventory.setText(teamTeammate);
			if (player.getCurrentTeam().getType() == Team.TYPE_STEALTH) {
				store.getStealthInventory().setText(globalTeammate);
			} else {
				store.getWarriorInventory().setText(globalTeammate);
			}

			player.setCurrentTeam(null);

		}
	}

	private void displayNewTeamInventory() {
		Team t = player.getCurrentTeam();

		int[] teammateArray = t.getTeammateCount();
		int[] weaponArray = t.getWeaponArray();
		int ammoCount = t.getAmmoCount();

		// convert all of the values to strings
		String teamBat = String.valueOf(weaponArray[Weapon.BASEBALL_BAT]);
		String teamCrossbow = String.valueOf(weaponArray[Weapon.CROSSBOW]);
		String teamShotgun = String.valueOf(weaponArray[Weapon.SHOTGUN]);

		String teamArrows = String.valueOf(ammoCount);
		String teamBullets = String.valueOf(ammoCount);

		String teamTeammate = String.valueOf(teammateArray[t.getType()]);

		batInventory.setText(teamBat);
		crossbowInventory.setText(teamCrossbow);
		shotgunInventory.setText(teamShotgun);
		arrowInventory.setText(teamArrows);
		bulletInventory.setText(teamBullets);
		teammateInventory.setText(teamTeammate);

	}

	public void disableDeployButton() {
		deployTroops.setEnabled(false);
	}

	public void enableDeployButton() {
		deployTroops.setEnabled(true);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public JButton getWarrior() {
		return warrior;
	}

	public void setWarrior(JButton warrior) {
		this.warrior = warrior;
	}

	public JButton getStealth() {
		return stealth;
	}

	public void setStealth(JButton stealth) {
		this.stealth = stealth;
	}
}
