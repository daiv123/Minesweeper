import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class P4_Clark_Davin_MSView  {
	private final int boardSize = 400;
	JPanel panel = new JPanel();
	PlayBoard board = new PlayBoard();
	JTextField timeText;
	JTextField flagText;
	JMenuItem mntmAbout;
	JMenuItem mntmHowToPlay;
	JMenuItem mntmSetNumOf;
	JMenuItem mntmNewMenuItem;
	JMenuItem mntmExit;
	JFrame window;
	public PlayBoard getBoard() {
		return board;
	}
	
	P4_Clark_Davin_MSView() {
		// Create a basic Java window frame
		window = new JFrame("Davin Clark");

		// Decide what to do when the user closes the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		// Set the window size (see API)
		window.setBounds(200, 200, 510, 1080);

		// Prevent users from resizing the window
		window.setResizable(true);
		
		panel.setLayout(new FlowLayout());
		window.add(panel);
		panel.add(board);
		JPanel bottom = new JPanel(new FlowLayout());

		board.setPreferredSize(new Dimension(boardSize,boardSize));
		bottom.setPreferredSize(new Dimension(boardSize,boardSize/2));
		JPanel timePan = new JPanel();
		JPanel flagPan = new JPanel();
		timeText = new JTextField("000");
		flagText = new JTextField("20");
		flagText.setEnabled(false);
		timeText.setEnabled(false);
		timePan.setBorder(new TitledBorder("Time"));
		flagPan.setBorder(new TitledBorder("Bombs"));
		timePan.setPreferredSize(new Dimension(155,75));
		flagPan.setPreferredSize(new Dimension(155,75));

		timePan.add(timeText);
		flagPan.add(flagText);
		bottom.add(timePan);
		bottom.add(flagPan);
		panel.add(bottom);
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Game");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("New Game");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		mntmExit = new JMenuItem("Exit");
		mnOptions.add(mntmExit);
		
		mntmSetNumOf = new JMenuItem("Set Num Of Mines");
		mnOptions.add(mntmSetNumOf);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		mntmHowToPlay = new JMenuItem("How to Play");
		mnHelp.add(mntmHowToPlay);
		
		window.setVisible(true);
	}
	public int getBoardSize() {
		return boardSize;
	}
	
	public void drawBoard(BufferedImage[][] board,int size){
		Graphics2D g = (Graphics2D) getBoard().getGraphics();
		double cellSize = boardSize/board.length;
		System.out.println(cellSize);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				g.drawImage(board[i][j], (int)(i*cellSize), (int)(j*cellSize),
						(int)(cellSize),(int)(cellSize), null);
			}
		}
	}
	public int restartPopup(String message){
		
		return JOptionPane.showConfirmDialog(getBoard(), message, "Restart", JOptionPane.YES_NO_OPTION);
		
	}
	public String bombPopup(){
		return JOptionPane.showInputDialog( "Enter Number Of Bombs", JOptionPane.QUESTION_MESSAGE);
	}
	@SuppressWarnings("serial")
	class PlayBoard extends JPanel {
		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			// Draw stuff
			Graphics2D g2 = (Graphics2D)g;
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, boardSize, boardSize);
			 
			
		}
	}
}
