package dotsBoxes.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
	protected int x, y;
	protected Color color;

	protected static int boxLength;
	protected static Graphics2D graphics;

	public Shape(int x, int y, Color c){
		this.x = x;
		this.y = y;
		this.color = c;
	}
	public static void setBoxLength(int l){
		boxLength = l;
	}
	public static void setGraphics(Graphics2D g){
		graphics = g;
	}
	public abstract void draw();
}
