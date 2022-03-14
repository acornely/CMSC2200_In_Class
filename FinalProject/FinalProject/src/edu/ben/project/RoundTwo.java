package edu.ben.project;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class RoundTwo extends Round {

	public RoundTwo(Game g) {
		super(g);
	}

	/**
	 * determines if complete
	 */
	@Override
	public boolean isComplete() {
		for (int i = BuildingHolder.HOME_BASE_TOP_ROW; i < BuildingHolder.HOME_BASE_TOP_ROW
				+ BuildingHolder.NUM_HOME_BASE_SQUARES; i++) {
			if (getLogic().getBoard().getBoardArray()[i][BuildingHolder.HOME_BASE_COL] instanceof Team) {
				Team team = (Team) getLogic().getBoard().getBoardArray()[i][BuildingHolder.HOME_BASE_COL];
				if (team.getItem() != null) {
					getGame().getBoard().setAirStrikeClickCount(0);
					return true;
					// begin round 2
				}
			}
		}
		getGame().getBoard().setAirStrikeClickCount(0);
		return false;

	}

	/**
	 * advances round
	 */
	@Override
	public void advanceRound() {
		JOptionPane.showMessageDialog(null,
				"Round won! You have been awarded an additional 50 dollars! Advancing to Round Does Not Exist Because We A Bunch of Scrubs Who Couldn't Make It To Checkpoint 5!\nAir strike is now enabled for your convenience in testing. Have a nice day! :)");
		getGame().setRoundIndex(0);
		getGame().getGui().getAirStrikeButton().setEnabled(true);
		getGame().getLogic().initializeZombieHordes();
		getGame().getGui().airstrikeForMirsky = true;

	}
/*
 * (non-Javadoc)
 * @see edu.ben.project.Round#initializeRound()
 */
	@Override
	public void initializeRound() {

		getGame().getGui().getMoveButton().setEnabled(true);
		generateQuestItem();
		getLogic().initializeZombieHordes();
		getLogic().getBoard().updateBoard();

	}
/*
 * (non-Javadoc)
 * @see edu.ben.project.Round#generateQuestItem()
 */
	@Override
	public void generateQuestItem() {
		Team.setItemName("Super Duper Secret Squirrel High Speed Communicator for Communicating");
		Random r = new Random();

		Building b = getLogic().getBoard().getBuildingHolder().getHouses().get(r.nextInt(4));
		QuestItem survivor = new QuestItem(b.getRow(), b.getCol());
		b.setItem(survivor);

	}
/*
 * (non-Javadoc)
 * @see edu.ben.project.Round#isRoundLost()
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
/*
 * (non-Javadoc)
 * @see edu.ben.project.Round#displayRules()
 */
	@Override
	public void displayRules() {
		JOptionPane.showMessageDialog(null,
				"Welcome to Round 2!\nIn this round, you and your crew have received news that there is a radio in a house.\nHowever, the zombies are attracted to the static from the radio, so the house is surrounded by the zombies!\nYour task? Find the radio and escort it safely back to your home base!\nShould the escorting team die, the radio will reappear in a random house, thus requiring another team to find them again.\nGood luck!");

	}

}
