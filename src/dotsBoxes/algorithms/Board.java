package dotsBoxes.algorithms;

import java.util.Arrays;

public class Board {
	private int  board[][];
	private int size;
	private Position lastChange;
	
	public Board(){
		lastChange = new Position();
		setBordSize(10);
	}


	public void setBordSize(int s){
		size = s;
		board = new int[2 * s + 1][2 * s + 1];
	}

	public boolean checkLineAvailable(int x, int y){
		return (board[x][y] == 0);
	}
	public int getMatrixLength(){
		return board.length;
	}
	public int getBoardLength(){
		return size;
	}
	public void setBoardCell(int x, int y, int value){
		lastChange.x = x;
		lastChange.y = y;
		board[x][y] = value;
	}
	public int getBoxSidesCount(int x, int y){
		return (board[x + 1][y] + board[x - 1][y] + board[x][y + 1] + board[x][y - 1]);
	}
	public void resetBoard(){
		for(int i = 0; i < board.length; i++)
			Arrays.fill(board[i], 0);
	}
	public Position getLastChange(){
		return lastChange;
	}
}
