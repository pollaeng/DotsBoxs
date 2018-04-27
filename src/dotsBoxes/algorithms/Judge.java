package dotsBoxes.algorithms;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.IntStream;

import dotsBoxes.gui.GameUI;
import dotsBoxes.gui.StatisticsPanel;

public class Judge {
	private Board board;
	private Player[] players;
	private int[] playerScore = new int[4];
	private int[] playerWins = new int[4];
	private long[] playerTimer = new long[4];
	private int speed = 5;
	private GameUI gameUI;
	private int playerIndex, looping = 1;
	private Color[] lineColor ;
	private boolean stopped = false, paused = false, animate = true;
	private StatisticsPanel statisticsPanel;
	public Judge(GameUI gameUI, Board board, StatisticsPanel statisticsPanel, Color[] lineColor) {
		this.board = board;
		players = new Player[4];
		this.gameUI = gameUI;
		this.statisticsPanel = statisticsPanel;
		this.lineColor = lineColor;
	}

	public void setPlayer(int index, String strPayer) {
		System.out.println("setPlayer");
		if (index < 0 || index > 3)
			throw new IllegalArgumentException("Index is out of range");
		if (strPayer.equals("Not Playing")) {
			players[index] = null;
		} else if (strPayer.equals("Random")) {
			 players[index] = new Random(board);

		} else if (strPayer.equals("Sequential")) {
			players[index] = new Sequential(board);

		} else if (strPayer.equals("BruteForce")) {
			 players[index] = new BruteForce(board);

		} else if (strPayer.equals("Hill Climbing")) {
			 players[index] = new HillClimbing(board);

		} else if (strPayer.equals("Strategic 1")) {
			// players[index] = new BruteForce();

		} else if (strPayer.equals("Strategic 2")) {
			// players[index] = new BruteForce();

		}

	}

	public void play(int dim) {
		board.setBordSize(dim);
		statisticsPanel.resetStats();

		stopped = false;
		paused = false;
		final Thread thr = new Thread() {

			@Override
			public void run() {
				int test = 0;
				Arrays.fill(playerScore, 0);
				Arrays.fill(playerWins, 0);
				Arrays.fill(playerTimer, 0);
				statisticsPanel.resetscores();

				for(int i = 0; i < looping; i++){
					gameUI.reset(board.getBoardLength());
					board.resetBoard();

					while (true) {
						try {
							test = oneRound(this);
							for(int t = 0; t < playerTimer.length; t++ )
								statisticsPanel.setTime(t, playerTimer[t]/1000);
							if (test == 1)	break;
							if (test == 2)	return;
							
						} catch (Exception iae) {
							iae.printStackTrace();
							return;
						}
						
					} // while (true)
					
					int winner = findWinner();
					playerWins[winner]++;
					statisticsPanel.setWin(winner, playerWins[winner]);

				}// for(int i ...
			}
		};
		thr.setPriority(Thread.MIN_PRIORITY);
		thr.run();
	}
	private int oneRound(Thread thr) throws Exception{
		Position p = null;
		int temp = 0;
		for (playerIndex = 0; playerIndex < 4; playerIndex++) {
			if (players[playerIndex] == null) continue;
			do{
				while (paused) thr.sleep(1000);
				long startTime = System.nanoTime();
				p = players[playerIndex].play();
				playerTimer[playerIndex] += System.nanoTime() - startTime;
				if (stopped) return 2;
	
				if (p.x == -1 ) return 1;
				
				board.setBoardCell(p.x, p.y, 1);
				if (animate){ 
					gameUI.drawLine(p.x, p.y, lineColor[playerIndex]);
					thr.sleep(10 * (speed * speed + 1) / 2 );
				}
			} while (boxCreated(p, thr));
		}
		return temp;
	}
	public void pause(boolean pause) {
		paused  = pause;
	}

	public void stop() {
		gameUI.reset(board.getBoardLength());
		board.resetBoard();
		Arrays.fill(playerScore, 0);
		Arrays.fill(playerWins, 0);
		Arrays.fill(playerTimer, 0);
		statisticsPanel.resetStats();
		stopped = true;
	}
	private int findWinner(){
		int max = -1, winer = 0;
		for(int i = 0; i < playerScore.length; i++)
			if(playerScore[i] > max){
				max = playerScore[i] ;
				winer = i;
			}
		return winer;
	}
	private boolean boxCreated(Position p, Thread thr) throws InterruptedException {
		boolean result = false;
		if (p.y % 2 == 0 && p.x % 2 == 1) { // Horizontal line
			if (p.y > 0 && board.getBoxSidesCount(p.x, p.y - 1) == 4) {
				if (animate) gameUI.drawBox(p.x, p.y - 1, lineColor[playerIndex]);
				board.setBoardCell(p.x, p.y - 1, 1);
				playerScore[playerIndex]++;
				result = true;
				
			}
			if (p.y < (board.getMatrixLength() - 1) && board.getBoxSidesCount(p.x, p.y + 1) == 4) {
				if (animate) gameUI.drawBox(p.x, p.y + 1, lineColor[playerIndex]);
				board.setBoardCell(p.x, p.y + 1, 1);
				playerScore[playerIndex]++;
				result = true;
			}
		} else { //Vertical line
			if (p.x > 0 && board.getBoxSidesCount(p.x - 1, p.y) == 4) {
				if (animate) gameUI.drawBox(p.x - 1, p.y, lineColor[playerIndex]);
				board.setBoardCell(p.x - 1, p.y, 1);
				playerScore[playerIndex]++;
				result = true;
			}
			if (p.x < board.getMatrixLength() - 1 && board.getBoxSidesCount(p.x + 1, p.y) == 4) {
				if (animate) gameUI.drawBox(p.x + 1, p.y, lineColor[playerIndex]);
				board.setBoardCell(p.x + 1, p.y, 1);
				playerScore[playerIndex]++;
				result = true;
			}
		}
		if(result){
			statisticsPanel.setScore(playerIndex, playerScore[playerIndex]);
			thr.sleep(10);
		}

		return result;
	}
	public void setSpeed(int sp){
		speed = sp;
	}
	public void setAnimate(boolean a){
		animate = a;
	}
	public void setLoop(int l){
		looping = l;
	}
}
