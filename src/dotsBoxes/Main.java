package dotsBoxes;

import dotsBoxes.algorithms.Board;
import dotsBoxes.gui.Frame;

public class Main {
	public static void main(String str[]){

		Board board = new Board();
		Frame frame = new Frame(board);
		frame.setVisible(true);
	}
}
