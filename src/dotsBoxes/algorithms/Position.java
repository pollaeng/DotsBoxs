package dotsBoxes.algorithms;

public class Position {
	public int x, y;
	public Position(){
		
	}
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Position(Position pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	public void setPosition(Position pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString(){
		return "{" + x + ", " + y + "}";
	}
	@Override
	public Position clone(){
		return new Position(x, y);
	}
}
