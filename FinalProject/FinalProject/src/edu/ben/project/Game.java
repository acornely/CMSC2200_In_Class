package edu.ben.project;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Game {

	private GameLog log = new GameLog();
	private int roundIndex = 0;
	private GUI gui;
	private Player player;
	private ZombieLogic logic;
	private Board board;
	private Round[] rounds = new Round[4];

	public static void main(String[] args) {

		initializeGame();
	}

	/**
	 * Swing runner and thread, sets up instances of rounds and starts round one
	 * 
	 * @param g
	 *            game instance
	 */
	public void playGame(final Game g) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				player = new Player();
				board = new Board();
				logic = new ZombieLogic(g);
				gui = new GUI(g);
				roundIndex = 0;
				RoundOne one = new RoundOne(g);
				RoundTwo two = new RoundTwo(g);
				rounds[roundIndex] = one;
				rounds[1] = two;
				rounds[roundIndex].initializeRound();
				gui.setVisible(true);
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream audioInputStream;
					audioInputStream = AudioSystem.getAudioInputStream(new File("music/titanic.wav"));
					clip.open(audioInputStream);
					clip.start();
					JOptionPane.showMessageDialog(null,
							"Welcome to Nerds vs Zombies!\nThis game consists of four rounds! Follow the rules, accomplish the tasks, and win!\n");
					clip.stop();

					rounds[0].displayRules();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private static void initializeGame() {
		Game g = new Game();
		g.playGame(g);
	}

	public int getRoundIndex() {
		return roundIndex;
	}

	public void setRoundIndex(int roundIndex) {
		this.roundIndex = roundIndex;
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ZombieLogic getLogic() {
		return logic;
	}

	public void setLogic(ZombieLogic logic) {
		this.logic = logic;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Round[] getRounds() {
		return rounds;
	}

	public void setRounds(Round[] rounds) {
		this.rounds = rounds;
	}

	public GameLog getLog() {
		return log;
	}

	public void setLog(GameLog log) {
		this.log = log;
	}

}
