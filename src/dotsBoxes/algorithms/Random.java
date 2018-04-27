package dotsBoxes.algorithms;

public class Random extends Player {

	public Random(Board board) {
		super(board);
	}

	@Override
	public synchronized Position play() {
		Position position = new Position(-1, -1);
		java.util.Random rand = new java.util.Random();
		for(int i = 0; i < 20; i++){
			int x = rand.nextInt(board.getMatrixLength());
			int y = rand.nextInt(board.getMatrixLength());
			if ((x + y) % 2 == 0){
				i--;
				continue;
			}
			if (board.checkLineAvailable(x, y)) {
				position.setPosition(x, y);
				return position;
			}
		}
		// if there is no enough place to be found by random then degrade to sequential
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
