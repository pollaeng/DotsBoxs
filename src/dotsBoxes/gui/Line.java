package dotsBoxes.gui;

import java.awt.Color;

public class Line extends Shape {
	public static final char HORIZONTAL = 'h';
	public static final char VERTICAL = 'v';
	
	private char type; 
	public Line(int x, int y, Color c) {
		super(x/2, y/2, c);// Adjustment between graphical dimensions and the model matrix
		
		if (y % 2 == 0 && x % 2 == 1) //Horizontal line
			type = HORIZONTAL;
		else if(y % 2 == 1 && x % 2 == 0)  //Vertical line
			type = VERTICAL;
		else
			throw new IllegalArgumentException("The provided (x, y) value for drwaing a line is wrong.");
	}

	@Override
	public void draw() {
		graphics.setColor(color);
		switch (type){
		case HORIZONTAL://Horizontal line
			graphics.drawLine(x * boxLength + 10, y * boxLength + 10, 
					(x + 1) * boxLength + 10, y * boxLength + 10);
			break;
		case VERTICAL://Vertical line
			graphics.drawLine(x * boxLength + 10, y * boxLength + 10, 
					x * boxLength + 10, (y+1)  * boxLength + 10);
		}


	}
	@Override 
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
	public char getType(){
		return type;
	}
}
