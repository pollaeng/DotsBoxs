package dotsBoxes.algorithms;

public class Sequential extends Player {

	public Sequential(Board board) {
		super(board);

	}

	@Override
	public synchronized Position play() {
		Position position = new Position(-1, -1);
		for (int y = 0; y < board.getMatrixLength(); y++) {
			for (int x = 0; x < board.getMatrixLength(); x++) {
				if ((x + y) % 2 == 0) // deflect none-Line places
					continue;
				if (board.checkLineAvailable(x, y)) {
					position.setPosition(x, y);
					return position;
				}
			}

		}
		return position;
	}

}
