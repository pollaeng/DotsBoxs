package dotsBoxes.gui;

import java.awt.Color;

public class Box extends Shape {
	
	public Box(int x, int y, Color c) {
		super(x/2, y/2, c);
		
		if (!(y % 2 == 1 && x % 2 == 1))
			throw new IllegalArgumentException("The provided (x, y) value for drwaing a Box is wrong.");
	}

	@Override
	public void draw() {
		graphics.setColor(color);
		graphics.fillRect(x * boxLength + 10, y * boxLength + 10, boxLength , boxLength);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(x * boxLength + 10, y * boxLength + 10, boxLength , boxLength);

	}
	
	@Override 
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}
