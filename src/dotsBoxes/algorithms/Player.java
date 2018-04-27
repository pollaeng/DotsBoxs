package dotsBoxes.algorithms;

public abstract class Player{
	//protected Position position = new Position();
	protected Board board;
	
	public Player(Board board){
		this.board = board;
	}
	public abstract Position play();

	
	
}
