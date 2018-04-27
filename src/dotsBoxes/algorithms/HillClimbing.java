package dotsBoxes.algorithms;


public class HillClimbing extends Player {
	java.util.Random rand;

	public HillClimbing(Board board) {
		super(board);
		rand = new java.util.Random();
	}

	@Override
	public synchronized Position play() {
		Position position = new Position(-1, -1);
		
		for(int i = 0; i < 40;){
			
			int x = rand.nextInt(board.getMatrixLength());
			int y = rand.nextInt(board.getMatrixLength());
			
			if ((x + y) % 2 == 0)
				continue;

			i++;
			if (board.checkLineAvailable(x, y)) {
				position.setPosition(x, y);
				break;
			}
		}
		// if there is no enough place to be found by random then degrade to sequential
		if(position.x == -1){
			boolean found = false;
			for (int y = 0; y < board.getMatrixLength(); y++) {
				for (int x = 0; x < board.getMatrixLength(); x++) {
					if ((x + y) % 2 == 0) // deflect none-Line places
						continue;
					if (board.checkLineAvailable(x, y)) {
						position.setPosition(x, y);
						found = true;
						 break;
					}
				}
				if(found) break;
			}
		}
                                                                                                                                                                
		if(position.x != -1){
			Position p = null;
			int originalCost = getCost(position.x, position.y);
			int newCost = originalCost;
			do{
				originalCost = newCost;
				p = position.clone();
				int limit = board.getMatrixLength() - 1;
				for(int xx = p.x - 3; xx <= p.x + 3; xx++){
					for(int yy = p.x -3; yy <= p.y + 3; yy++){
						
						if(xx < 0 || yy < 0 || xx > limit || yy > limit ||  
								(xx + yy) % 2 == 0 || ! board.checkLineAvailable(xx, yy)) 
							continue;
						newCost = getCost(xx, yy);
						if(newCost > originalCost)
							position = p;
					}
				}
			}while(newCost != originalCost);
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
