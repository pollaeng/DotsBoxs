package dotsBoxes.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import javax.swing.JPanel;

import dotsBoxes.algorithms.Board;

@SuppressWarnings("serial")
public class GameUI  extends JPanel {
	private int  dimension;
	private static int radius = 8;
	private int boxLength = 1;
	private Vector<Shape> shapes = new Vector<Shape>();
	private Board board;
	
	public GameUI(Board board){
		this.board = board;
		setSize(700, 700);
		setLocation(0, 0);
		setBackground(Color.lightGray);
		reset(10);
	
		repaint();
	}


	public void reset(int dim) {
		dimension = dim + 1;
		boxLength = 700 / dimension;
		radius = 6;
		radius = (int) Math.ceil((radius * 20) / dimension);
		radius = radius < 2? 2: radius;
		Shape.boxLength = boxLength;
		shapes.removeAllElements();
		repaint();
	}
	
	

	private void drwaDots(Graphics2D g){
		g.setColor(Color.BLACK);
		for (int x = 0; x <= board.getBoardLength(); x++){
			for(int y = 0; y <= board.getBoardLength(); y++){
				drawCenteredCircle(g, x* boxLength + 10, y * boxLength + 10);
			}
		}
		
	}

	private void drawCenteredCircle(Graphics2D g, int x, int y) {
		  x = x-(GameUI.radius/2);
		  y = y-(GameUI.radius/2);
		  g.fillOval(x, y, GameUI.radius, GameUI.radius);
	}
	
	public void drawLine(int x, int y, Color c){
		Thread paintThread = new Thread(){
			@Override
			public void run(){
				Line line = new Line(x, y, c);
				shapes.addElement(line);
				repaint();
			}
		};
		paintThread.setPriority(10);
		paintThread.start();
	}
	public void drawBox(int x, int y, Color c){
		shapes.addElement(new Box(x, y, c));
		repaint();
	}
	
	public void paint(Graphics screen){
		Graphics2D screen2D = (Graphics2D)screen;
		Shape.graphics = screen2D;

		screen2D.setColor(Color.GRAY);
		screen2D.fill(new Rectangle2D.Float(0, 0, 700, 700));
		
		for (Shape sh: shapes){
			sh.draw();
		}
		drwaDots(screen2D);

	}
}
