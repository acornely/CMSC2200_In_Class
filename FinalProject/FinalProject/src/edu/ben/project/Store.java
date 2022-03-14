package edu.ben.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Steve Schultz
 * 
 * This is the class that handes all of the store buttons
 *
 */
@SuppressWarnings("serial")
public class Store extends JPanel {

	private JPanel storeButtons;
	private JPanel storeLabels;
	private JPanel salaryLabel;

	private JButton buyWarrior;
	private JButton buyStealth;
	private JButton buyBat;
	private JButton buyShotgun;
	private JButton buyCrossbow;
	private JButton buyBullets;
	private JButton buyArrows;

	private JLabel salary;
	private JLabel stealthInventory;
	private JLabel warriorInventory;
	private JLabel batInventory;

	private JLabel shotgunInventory;
	private JLabel crossbowInventory;
	private JLabel bulletInventory;
	private JLabel arrowInventory;

	public static final int COST_WARRIOR_TEAM = 10;
	public static final int COST_STEALTH_TEAM = 10;
	public static final int COST_BAT = 5;
	public static final int COST_SHOTGUN = 20;
	public static final int COST_CROSSBOW = 10;
	public static final int COST_PACK_OF_BULLETS = 10;
	public static final int COST_PACK_OF_ARROWS = 10;

	Player p;
	GameLog log;

	public Store(Player p, GameLog log) {
		this.p = p;
		this.log = log;

		setVisible(false);

		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		// total running salary panel
		salaryLabel = new JPanel();
		salaryLabel.setLayout(new GridLayout(1, 1));
		salaryLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(salaryLabel, BorderLayout.NORTH);
		
		// panel that holds the buttons for buying items
		storeButtons = new JPanel();
		storeButtons.setLayout(new GridLayout(7, 1));
		storeButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(storeButtons, BorderLayout.WEST);
		
		// panel that holds the JLabels for the corresponding buttons
		storeLabels = new JPanel();
		storeLabels.setLayout(new GridLayout(7, 1));
		add(storeLabels, BorderLayout.EAST);
		
		// sets the salary to 400 dollars
		salary = new JLabel();
		salary.setBorder(new EmptyBorder(0, 20, 0, 20));
		salary.setOpaque(true);
		salary.setText("Salary: 400");
		salary.setFont(new Font("Serif", Font.PLAIN, 25));
		salaryLabel.add(salary);
		
		// buttons to buy warriors
		buyWarrior = new JButton("Buy a Warrior Teammate ($" + String.valueOf(COST_WARRIOR_TEAM + ")"));
		buyWarrior.addActionListener(new buyWarriorListener());
		storeButtons.add(buyWarrior);
		
		// button to buy stealth teammates
		buyStealth = new JButton("Buy a Stealth Teammate ($" + String.valueOf(COST_STEALTH_TEAM + ")"));
		buyStealth.addActionListener(new buyStealthListener());
		storeButtons.add(buyStealth);
		
		// button to buy a bat
		buyBat = new JButton("Buy a Bat ($5)");
		buyBat.addActionListener(new buyBatListener());
		storeButtons.add(buyBat);
		
		// button to buy a shotgun
		buyShotgun = new JButton("Buy a Shotgun ($20)");
		buyShotgun.addActionListener(new buyShotgunListener());
		storeButtons.add(buyShotgun);
		
		// button to buya crossbow
		buyCrossbow = new JButton("Buy a Crossbow ($10)");
		buyCrossbow.addActionListener(new buyCrossbowListener());
		storeButtons.add(buyCrossbow);
		
		// button to buy 5 bullets
		buyBullets = new JButton("Buy Bullets(5) ($" + String.valueOf(COST_PACK_OF_BULLETS + ")"));
		buyBullets.addActionListener(new buyBulletListener());
		storeButtons.add(buyBullets);
		
		// button to buy 10 arrows
		buyArrows = new JButton("Buy Arrows(10) ($10)");
		buyArrows.addActionListener(new buyArrowListener());
		storeButtons.add(buyArrows);
		
		// label for warrior teammates
		warriorInventory = new JLabel();
		warriorInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		warriorInventory.setOpaque(true);
		warriorInventory.setText("0");
		storeLabels.add(warriorInventory);
		
		// label for stealth teammates
		stealthInventory = new JLabel();
		stealthInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		stealthInventory.setOpaque(true);
		stealthInventory.setText("0");
		storeLabels.add(stealthInventory);
		
		// label for bat inventory
		batInventory = new JLabel();
		batInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		batInventory.setOpaque(true);
		batInventory.setText("0");
		storeLabels.add(batInventory);
		
		// label for shotgun inventory
		shotgunInventory = new JLabel();
		shotgunInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		shotgunInventory.setOpaque(true);
		shotgunInventory.setText("0");
		storeLabels.add(shotgunInventory);
		
		// label for crossbow inventory
		crossbowInventory = new JLabel();
		crossbowInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		crossbowInventory.setOpaque(true);
		crossbowInventory.setText("0");
		storeLabels.add(crossbowInventory);
		
		// label for bullet inventory
		bulletInventory = new JLabel();
		bulletInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		bulletInventory.setOpaque(true);
		bulletInventory.setText("0");
		storeLabels.add(bulletInventory);
		
		// label for arrow inventory
		arrowInventory = new JLabel();
		arrowInventory.setBorder(new EmptyBorder(0, 20, 0, 20));
		arrowInventory.setOpaque(true);
		arrowInventory.setText("0");
		storeLabels.add(arrowInventory);

	}
	
	
	private class buyWarriorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			p.createTeammate(Team.TYPE_WARRIOR);
			int[] teammateArray = p.getGlobalTeammateArray();
			String warriorCount = String.valueOf(teammateArray[Team.TYPE_WARRIOR]);

			warriorInventory.setText(warriorCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a warrior teammate.");
		}

	}

	private class buyStealthListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createTeammate(Team.TYPE_STEALTH);

			int[] teammateArray = p.getGlobalTeammateArray();
			String stealthCount = String.valueOf(teammateArray[Team.TYPE_STEALTH]);

			stealthInventory.setText(stealthCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a steath teammate.");

		}

	}

	private class buyBatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createWeapon(Weapon.BASEBALL_BAT);

			int[] weaponArray = p.getGlobalWeaponArray();
			String batCount = String.valueOf(weaponArray[Weapon.BASEBALL_BAT]);

			batInventory.setText(batCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a bat.");

		}

	}

	private class buyShotgunListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createWeapon(Weapon.SHOTGUN);

			int[] weaponArray = p.getGlobalWeaponArray();
			String shotgunCount = String.valueOf(weaponArray[Weapon.SHOTGUN]);

			shotgunInventory.setText(shotgunCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a shotgun");

		}

	}

	private class buyCrossbowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createWeapon(Weapon.CROSSBOW);

			int[] weaponArray = p.getGlobalWeaponArray();
			String crossbowCount = String.valueOf(weaponArray[Weapon.CROSSBOW]);

			crossbowInventory.setText(crossbowCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a crossbow");

		}

	}

	private class buyBulletListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createAmmo(Weapon.SET_OF_BULLETS);

			int[] ammoArray = p.getGlobalAmmoArray();
			String bulletCount = String.valueOf(ammoArray[Weapon.SET_OF_BULLETS]);

			bulletInventory.setText(bulletCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys a pack of bullets");

		}

	}

	/**
	 * This arrow action listener adds 10 arrows to the globalAmmoArray and then
	 * updates the text appropriately
	 * 
	 * @author Steve Schultz
	 *
	 */
	private class buyArrowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p.createAmmo(Weapon.PACK_OF_ARROWS);

			int[] ammoArray = p.getGlobalAmmoArray();
			String arrowCount = String.valueOf(ammoArray[Weapon.PACK_OF_ARROWS]);

			arrowInventory.setText(arrowCount);

			displayUpdatedSalary();
			
			log.gameLogs("Player buys arrows");

		}

	}

	/**
	 * This method is called in all of the button listeners in the store class
	 * and it sets the text of current salary
	 * 
	 */
	public void displayUpdatedSalary() {
		String playerSalary = "Salary: " + String.valueOf(p.getSalary());
		salary.setText(playerSalary);
	}

	public JLabel getBatInventory() {
		return batInventory;
	}

	public void setBatInventory(JLabel batInventory) {
		this.batInventory = batInventory;
	}

	public JLabel getStealthInventory() {
		return stealthInventory;
	}

	public void setStealthInventory(JLabel stealthInventory) {
		this.stealthInventory = stealthInventory;
	}

	public JLabel getWarriorInventory() {
		return warriorInventory;
	}

	public void setWarriorInventory(JLabel warriorInventory) {
		this.warriorInventory = warriorInventory;
	}

	public JLabel getShotgunInventory() {
		return shotgunInventory;
	}

	public void setShotgunInventory(JLabel shotgunInventory) {
		this.shotgunInventory = shotgunInventory;
	}

	public JLabel getCrossbowInventory() {
		return crossbowInventory;
	}

	public void setCrossbowInventory(JLabel crossbowInventory) {
		this.crossbowInventory = crossbowInventory;
	}

	public JLabel getBulletInventory() {
		return bulletInventory;
	}

	public void setBulletInventory(JLabel bulletInventory) {
		this.bulletInventory = bulletInventory;
	}

	public JLabel getArrowInventory() {
		return arrowInventory;
	}

	public void setArrowInventory(JLabel arrowInventory) {
		this.arrowInventory = arrowInventory;
	}

	public JPanel getSalaryLabel() {
		return salaryLabel;
	}

	public void setSalaryLabel(JPanel salaryLabel) {
		this.salaryLabel = salaryLabel;
	}

	public JLabel getSalary() {
		return salary;
	}

	public void setSalary(JLabel salary) {
		this.salary = salary;
	}

}
