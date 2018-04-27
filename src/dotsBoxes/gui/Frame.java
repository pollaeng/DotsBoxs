package dotsBoxes.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import dotsBoxes.algorithms.Board;

@SuppressWarnings("serial")
public class Frame extends JFrame{
	public Frame(Board board){
		super("Dots & Boxes");
		setSize(950,700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2 - this.getSize().width/2, 10);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameUI gameUI = new GameUI(board);
		Controls controls = new Controls(gameUI, board);

		
		Container container = getContentPane();
		
		container.setLayout(null);
        container.add(gameUI);
        container.add(controls);
		setContentPane(container);
	}
}
