package edu.ben.project;

import java.util.ArrayList;

/**
 * abstract class for round structure
 * 
 * @author Adrian Cornely
 *
 */
public abstract class Round {
	private ZombieLogic logic;
	private Player player;
	private GUI gui;
	private Game game;

	/**
	 * determines if specific round is complete
	 * 
	 * @return if complete
	 */
	public abstract boolean isComplete();

	/**
	 * advances round
	 */
	public abstract void advanceRound();

	/**
	 * initialize round
	 */
	public abstract void initializeRound();

	/**
	 * generates the quest item for a given round
	 */
	public abstract void generateQuestItem();

	/**
	 * clears the round, updates inventory no test because all gui based results
	 */
	public void clearRound() {
		logic.setZombieList(null);
		Board b = logic.getBoard();
		Group[][] units = b.getBoardArray();
		ArrayList<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < b.NUM_ROWS; i++) {
			for (int j = 0; j < b.NUM_COLS; j++) {
				if (units[i][j] instanceof Team) {
					teams.add((Team) units[i][j]);
					units[i][j] = null;
				}
				if (units[i][j] instanceof ZombieHorde) {
					units[i][j] = null;
				}
			}
		}
		b.updateBoard();
		while (!teams.isEmpty()) {
			Team temp = teams.remove(0);
			int[] teammates = temp.getTeammateCount();
			int[] weapons = temp.getWeaponArray();

			if (temp.getType() == Team.TYPE_STEALTH) {
				int ammo = temp.getAmmoCount();
				int numToReplace = teammates[Team.TYPE_STEALTH];
				player.getGlobalTeammateArray()[Team.TYPE_STEALTH] += numToReplace;
				gui.getStore().getStealthInventory()
						.setText(String.valueOf(player.getGlobalTeammateArray()[Team.TYPE_STEALTH]));
				int crossbows = weapons[1];
				player.getGlobalWeaponArray()[Weapon.CROSSBOW] += crossbows;
				gui.getStore().getCrossbowInventory()
						.setText(String.valueOf(player.getGlobalAmmoArray()[Weapon.CROSSBOW]));
				player.getGlobalAmmoArray()[Weapon.PACK_OF_ARROWS] += ammo;
				gui.getStore().getArrowInventory()
						.setText(String.valueOf(player.getGlobalAmmoArray()[Weapon.PACK_OF_ARROWS]));
			} else {
				int ammo = temp.getAmmoCount();
				int numToReplace = teammates[Team.TYPE_WARRIOR];
				player.getGlobalTeammateArray()[Team.TYPE_WARRIOR] += numToReplace;
				gui.getStore().getWarriorInventory()
						.setText(String.valueOf(player.getGlobalTeammateArray()[Team.TYPE_WARRIOR]));
				int bats = weapons[0];
				int shottys = weapons[2];
				player.getGlobalWeaponArray()[Weapon.BASEBALL_BAT] += bats;
				player.getGlobalWeaponArray()[Weapon.SHOTGUN] += shottys;
				gui.getStore().getBatInventory()
						.setText(String.valueOf(player.getGlobalWeaponArray()[Weapon.BASEBALL_BAT]));
				gui.getStore().getShotgunInventory()
						.setText(String.valueOf(player.getGlobalWeaponArray()[Weapon.SHOTGUN]));

				player.getGlobalAmmoArray()[Weapon.SET_OF_BULLETS] += ammo;
				gui.getStore().getBulletInventory()
						.setText(String.valueOf(player.getGlobalAmmoArray()[Weapon.SET_OF_BULLETS]));

			}
		}
	}

	/**
	 * tests if round is lost
	 * 
	 * @return if lost
	 */
	public abstract boolean isRoundLost();

	/**
	 * displays rules for round
	 */
	public abstract void displayRules();

	public Round(Game g) {
		logic = g.getLogic();
		player = g.getPlayer();
		gui = g.getGui();
		game = g;

	}

	public ZombieLogic getLogic() {
		return logic;
	}

	public void setLogic(ZombieLogic logic) {
		this.logic = logic;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
