package dotsBoxes.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dotsBoxes.algorithms.Board;
import dotsBoxes.algorithms.Judge;

@SuppressWarnings("serial")
public class Controls  extends JPanel{
	private Board board;
	private ImageIcon play, pause, stop1, stop2;
	private char playingStatus = 's';
	private JButton playPauseBtn, stopBtn;
	private JSlider speedSldr, dimSldr;
	private Color[] lineColor = new Color[] { Color.PINK, new Color(160, 32, 240), Color.BLUE, Color.MAGENTA };
	private RoundsCombo loopCombo = new RoundsCombo();
	private JToggleButton animate;
	private static final String[] ALGORITHMS = {"Not Playing", "Random", "Sequential", "BruteForce", 
												"Hill Climbing", "GA", "Stratygic 1", "Stratygic 2"};
	private PlayerBox playerBx1, playerBx2, playerBx3, playerBx4; 
	private GameUI gameUI;
	private Judge judge;
	private StatisticsPanel statisticsPanel;
	
	public Controls(GameUI gameUI, Board board){
		this.board = board;
		setSize(250, 700);
		setLocation(700, 0);
		loadImages();
		this.gameUI = gameUI;
		this.board.setBordSize(10);
		statisticsPanel = new StatisticsPanel(lineColor);
		judge = new Judge(gameUI, this.board, statisticsPanel, lineColor);
		
		dimSldr = new DimentionSlider();
		add(new DimentionPanel());
		
		playerBx1 = new PlayerBox(0, Arrays.copyOfRange(ALGORITHMS, 1, ALGORITHMS.length));
		judge.setPlayer(0, "Random");
		add(new PlayerPanel(0, playerBx1, lineColor[0]));
		
		playerBx2 = new PlayerBox(1, Arrays.copyOfRange(ALGORITHMS, 1, ALGORITHMS.length));
		judge.setPlayer(1, "Random");
		add(new PlayerPanel(1, playerBx2, lineColor[1]));

		
		playerBx3 = new PlayerBox(2, ALGORITHMS);
		add(new PlayerPanel(2, playerBx3, lineColor[2]));

		playerBx4 = new PlayerBox(3, ALGORITHMS);
		add(new PlayerPanel(3, playerBx4, lineColor[3]));
		
		add(getPlayingControls());
		add(statisticsPanel);
		
	}
	private void loadImages(){
		try {
			play = new ImageIcon(ImageIO.read(new File("resource/play.png")));
			pause = new ImageIcon(ImageIO.read(new File("resource/pause.png")));
			stop1 = new ImageIcon(ImageIO.read(new File("resource/stop1.png")));
			stop2 = new ImageIcon(ImageIO.read(new File("resource/stop2.png")));
			play = new ImageIcon(ImageIO.read(new File("resource/play.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private JPanel getPlayingControls(){
		JPanel playingControls = new JPanel(new FlowLayout());
		playingControls.setLayout(new BoxLayout(playingControls, BoxLayout.Y_AXIS));

		JPanel row1 = new JPanel();

		row1.add(new JLabel("Repitition(s) : "));
		
		row1.add(loopCombo);
		animate = new AnimateToggle();
		row1.add(animate);
		
		playPauseBtn = new PlayPauseButton(play);
		stopBtn = new StopButton(stop1);
		JPanel row2 = new JPanel();
		row2.add(playPauseBtn);
		row2.add(stopBtn);
		row2.add(new SpeedPanel());
		

		
		playingControls.add(row1);
		playingControls.add(row2);
		return playingControls;
	}

	private class PlayerBox extends JComboBox<String>{
		public PlayerBox(final int index, String []elements){
			super(elements);
			setPreferredSize(new Dimension(160, 23));
			addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					String algorithmStr = (String) ((JComboBox<String>)e.getSource()).getSelectedItem();
					judge.setPlayer(index, algorithmStr);
				}
			});
		}
	}
	
	private class PlayerPanel extends JPanel{
		
		public PlayerPanel(final int index, PlayerBox box , final Color col){
			JLabel smallColourBox = new JLabel("     ");
			smallColourBox.setBackground(col);
			smallColourBox.setOpaque(true);
			add(smallColourBox);
			add(new JLabel("Player " + (index + 1) + ":"));
			add(box);
		}
	}

	private class DimentionSlider extends JSlider{
		public DimentionSlider(){
			super(JSlider.HORIZONTAL, 5, 50, 10);
			setMinorTickSpacing(5);
			setMajorTickSpacing(10);
			setPaintTicks(true);
			setPaintLabels(true);
			
			addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					gameUI.reset(dimSldr.getValue());
					board.setBordSize(dimSldr.getValue());
				}
			});
		}
	}
	
	private class DimentionPanel extends JPanel {
		public DimentionPanel(){
			setLayout(new FlowLayout());
			
			JLabel lab = new JLabel("Size");
			lab.setFont(new Font("", Font.BOLD, 15));
			add(lab);
			add(dimSldr);
		}
	}

	private class PlayPauseButton extends JButton{
		public PlayPauseButton(Icon play){
			setIcon(play);
			setBorderPainted(false);
			setMargin(new Insets(0, 0, 0, 0));
			setFocusPainted(false);
			
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					playerBx1.setEnabled(false);
					playerBx2.setEnabled(false);
					playerBx3.setEnabled(false);
					playerBx4.setEnabled(false);
					dimSldr.setEnabled(false);
					animate.setEnabled(false);
					loopCombo.setEnabled(false);
					if(playingStatus == 's'){
						playingStatus = 'u';
						playPauseBtn.setIcon(pause);
						stopBtn.setIcon(stop2);				
						
						Thread runnable = new Thread(){ //Graphics thread
							public void run() {
								judge.play(dimSldr.getValue());
	
							}
						};
						runnable.setPriority(Thread.MIN_PRIORITY);
						runnable.start();
					}
					else if(playingStatus == 'p'){
						playingStatus = 'u';
						playPauseBtn.setIcon(pause);
						stopBtn.setIcon(stop2);				
						judge.pause(false);
	
					}
					else if(playingStatus == 'u' ){
						playingStatus = 'p';
						playPauseBtn.setIcon(play);
						judge.pause(true);
					}
				}
			});		
		}

	}
	private class StopButton extends JButton{
		public StopButton(Icon stop1){
			setIcon(stop1);
			setBorderPainted(false);
			setMargin(new Insets(0, 0, 0, 0));
			setFocusPainted(false);
					
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					playingStatus = 's';
					playPauseBtn.setIcon(play);	
					stopBtn.setIcon(stop1);
					playerBx1.setEnabled(true);
					playerBx2.setEnabled(true);
					playerBx3.setEnabled(true);
					playerBx4.setEnabled(true);
					dimSldr.setEnabled(true);
					animate.setEnabled(true);
					loopCombo.setEnabled(true);
					judge.stop();
				}
			});	
		}

	}
	private class SpeedPanel extends JPanel {
		public SpeedPanel(){
			setLayout(new FlowLayout());
			setSize(150, 50);
			speedSldr = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
			speedSldr.setPreferredSize(new Dimension(100, 50));
			speedSldr.setMinorTickSpacing(1);
			speedSldr.setMajorTickSpacing(5);
			speedSldr.setPaintTicks(true);
			speedSldr.setPaintLabels(true);
			
			speedSldr.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					judge.setSpeed(11 - ((JSlider)e.getSource()).getValue());
				}
			});
			add(new JLabel("Speed"));
			add(speedSldr);
		}
	}
	private class AnimateToggle extends JToggleButton{
		public AnimateToggle(){
			 super("Animate", true);
			 addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					judge.setAnimate(e.getStateChange() == ItemEvent.SELECTED);
					
				}
			});

		}
	}

	private class RoundsCombo extends JComboBox<Integer>{
		public RoundsCombo(){
			super( new Integer[]{1, 5, 10, 25, 50, 100});
			addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<Integer> item = (JComboBox<Integer>)e.getSource();
					judge.setLoop(((Integer)item.getSelectedItem()));
				}
			});
		}
	}


}
