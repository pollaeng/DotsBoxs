package dotsBoxes.algorithms;

public class BruteForce extends Player {

	public BruteForce(Board board) {
		super(board);

	}

	@Override
	public synchronized Position play() {

		int cost = -1;
		Position position = new Position(-1, -1);
		int size = board.getMatrixLength();
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if ((x + y) % 2 == 0) continue;
				
				if (board.checkLineAvailable(x, y)) {

					int temp = getCost(x, y);
					if(cost < temp){
						cost = temp;
						position.setPosition(x, y);
					}
				}
			}
		}
		return position;
	}
	private int getCost(int x, int y)  {
		int cost = 0;
		if (y % 2 == 0 && x % 2 == 1) { // Horizontal line
			if (y > 0) 
				cost += board.getBoxSidesCount(x, y - 1);
			
			if (y < (board.getMatrixLength() - 1)) {
				cost += board.getBoxSidesCount(x, y + 1);
			}
		} else { //Vertical line
			if (x > 0) {
				cost += board.getBoxSidesCount(x - 1, y);
			}
			if (x < board.getMatrixLength() - 1) {
				cost += board.getBoxSidesCount(x + 1,y);
			}
		}
		return cost;
	}
}
