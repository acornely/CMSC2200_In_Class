package edu.ben.project;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class RoundOne extends Round {

	public RoundOne(Game g) {
		super(g);

	}

	/**
	 * determines if round one is complete
	 */
	@Override
	public boolean isComplete() {
		for (int i = BuildingHolder.HOME_BASE_TOP_ROW; i < BuildingHolder.HOME_BASE_TOP_ROW
				+ BuildingHolder.NUM_HOME_BASE_SQUARES; i++) {
			if (getLogic().getBoard().getBoardArray()[i][BuildingHolder.HOME_BASE_COL] instanceof Team) {
				Team team = (Team) getLogic().getBoard().getBoardArray()[i][BuildingHolder.HOME_BASE_COL];
				if (team.getItem() != null) {
					getGame().getBoard().setAirStrikeClickCount(0);
					getGame().getGui().getAirStrikeButton().setEnabled(false);
					return true;

				}
			}
		}
		getGame().getBoard().setAirStrikeClickCount(0);
		getGame().getGui().getAirStrikeButton().setEnabled(false);
		return false;

	}

	/**
	 * initializes round 1
	 */
	@Override
	public void initializeRound() {
		generateQuestItem();
		getLogic().initializeZombieHordes();
		getLogic().getBoard().updateBoard();
		getGame().getGui().getAirStrikeButton().setEnabled(false);

	}

	/**
	 * generates survivor
	 */
	@Override
	public void generateQuestItem() {
		Team.setItemName("Survivor");
		Random r = new Random();

		Building b = getLogic().getBoard().getBuildingHolder().getHouses().get(r.nextInt(4));
		QuestItem survivor = new QuestItem(b.getRow(), b.getCol());
		b.setItem(survivor);
	}

	/**
	 * determines if round is lost
	 */
	@Override
	public boolean isRoundLost() {
		// if no deployed teams exist, no teammates exist in inventory and
		// salary <10
		Board b = getLogic().getBoard();
		Group[][] units = b.getBoardArray();
		ArrayList<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < b.NUM_ROWS; i++) {
			for (int j = 0; j < b.NUM_COLS; j++) {
				if (units[i][j] instanceof Team) {
					teams.add((Team) units[i][j]);

				}
			}
		}
		if (getPlayer().getGlobalTeammateArray()[0] == 0 && getPlayer().getGlobalTeammateArray()[1] == 0
				&& getPlayer().getSalary() < 10 && teams.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * displays rules
	 */
	@Override
	public void displayRules() {
		JOptionPane.showMessageDialog(null,
				"Welcome to Round 1!\nIn this round, you and your crew have received news that there is a survivor in a house.\nYour task? Find the survivor and escort them safely back to your home base!\nShould the escorting team die, the survivor will flee back to a random house, thus requiring another team to find them again.\nGood luck!");

	}

	/**
	 * advances round
	 */
	@Override
	public void advanceRound() {
		JOptionPane.showMessageDialog(null,
				"Round won! You have been awarded an additional 50 dollars! Advancing to Round 2!");
		getGame().setRoundIndex(getGame().getRoundIndex() + 1);
		getGame().getRounds()[getGame().getRoundIndex()].initializeRound();
		getGame().getRounds()[getGame().getRoundIndex()].displayRules();

	}

}
