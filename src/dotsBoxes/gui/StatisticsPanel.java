package dotsBoxes.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatisticsPanel extends JPanel {
	private Color[] colours;
	private JLabel[] scoresLbl = new JLabel[4];
	private JLabel[] winsLbl = new JLabel[4];
	private JLabel[] timesLbl = new JLabel[4];
	private JLabel round;
	
	public StatisticsPanel(Color[] colours){
		this.colours = colours;
		setSize(250, 350);
		setPreferredSize(new Dimension(250, 350));
		setBackground(new Color(220, 220, 220));
		JLabel statLbl = new JLabel("Statistics : ");
		statLbl.setFont(new Font("", Font.BOLD, 17));
		
		round= new JLabel("round (0) ");
		round.setFont(new Font("", Font.ITALIC, 11));
		add(statLbl);
		add(round);
		add(getPlayerStat(0));
		add(getPlayerStat(1));
		add(getPlayerStat(2));
		add(getPlayerStat(3));

	}
	private JPanel getPlayerStat(int index) {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(220, 220, 220));
		mainPanel.setPreferredSize(new Dimension(250, 70));
		mainPanel.setSize(250, 70);
		//Coloured Box
		JLabel smallColourBox = new JLabel("     ");
		smallColourBox.setBackground(colours[index]);
		smallColourBox.setOpaque(true);	
		mainPanel.add(smallColourBox);

		//Player# and elapsed time
		JLabel plaerLbl = new JLabel("Player " + (index + 1) + " : ");
		plaerLbl.setPreferredSize(new Dimension(170, 15));
		plaerLbl.setFont(new Font("", Font.PLAIN, 15));
		mainPanel.add(plaerLbl); 
		
		//Score		
		JLabel currentScorTag = new JLabel("Game Score :");
		currentScorTag.setPreferredSize(new Dimension(90, 10));
		mainPanel.add(currentScorTag);

		scoresLbl[index] = new JLabel("0");
		scoresLbl[index].setPreferredSize(new Dimension(70, 10));
		mainPanel.add(scoresLbl[index]);

		//Wins
		JLabel totalWinsTag = new JLabel("Total Wins :");
		totalWinsTag.setPreferredSize(new Dimension(90, 10));
		mainPanel.add(totalWinsTag);
		
		winsLbl[index] = new JLabel("0");
		winsLbl[index].setPreferredSize(new Dimension(70, 10));
		mainPanel.add(winsLbl[index]);
		
		//Timer
		JLabel timerTag = new JLabel("Total Time :");
		timerTag.setPreferredSize(new Dimension(90, 10));
		mainPanel.add(timerTag);
		
		timesLbl[index] = new JLabel("0");
		timesLbl[index].setPreferredSize(new Dimension(70, 10));
		mainPanel.add(timesLbl[index]);
		
		return mainPanel;
	}
	public void setScore(int index, int score){
		scoresLbl[index].setText("" + score);
	}
	public void setWin(int index, int wins){
		winsLbl[index].setText("" + wins);
	}
	public void setTime(int index, long time){
		timesLbl[index].setText("" + time);
	}
	public void resetStats(){
		resetscores();
		resetWins();
		resetTimes();
	}
	public void resetscores(){
		for(int i = 0; i < 4; i++)
			scoresLbl[i].setText("0");
	}
	public void resetWins(){
		for(int i = 0; i < 4; i++)
			winsLbl[i].setText("0");
	}
	public void resetTimes(){
		for(int i = 0; i < 4; i++)
			timesLbl[i].setText("0");
	}
}
