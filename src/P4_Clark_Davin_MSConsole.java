import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class P4_Clark_Davin_MSConsole implements  P4_Clark_Davin_MSAI2{
	Scanner in = new Scanner(System.in);
	private int size = 16;
	private int numBombs = 40;
	P4_Clark_Davin_MSModel m;
	BufferedImage[][] board;
	private boolean dead = false;
	int time = 0;
	public final static int C_HIDDEN = -1;
	public final static int C_FLAG = -2;
	int[][] cBoard = new int[size][size];
	boolean cheated = false;

	P4_Clark_Davin_MSView v;
	BufferedImage[] nums = new BufferedImage[9];
	BufferedImage bomb, blank, flag, bombDeath, question, bombRevealed, bombWrong;

	P4_Clark_Davin_MSConsole() {
		m = new P4_Clark_Davin_MSModel(numBombs, size);
		v = new P4_Clark_Davin_MSView();
		board = new BufferedImage[size][size];
		for (int i = 0; i < nums.length; i++) {
			String source = "num_" + i + ".gif";
			try {
				nums[i] = ImageIO.read(new File(source));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			blank = ImageIO.read(new File("blank.gif"));
			flag = ImageIO.read(new File("bomb_flagged.gif"));
			bombDeath = ImageIO.read(new File("bomb_death.gif"));
			bombWrong = ImageIO.read(new File("bomb_wrong.gif"));
			bombRevealed = ImageIO.read(new File("bomb_revealed.gif"));
			question = ImageIO.read(new File("bomb_question.gif"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(board[0][0]);

		update();
		System.out.println(board[0][0]);

		v.drawBoard(board, size);
		v.getBoard().addMouseListener(new drawL());
		timer.start();
		m.flag(0,0);
		m.flag(0,0);
		update();
		v.mntmNewMenuItem.addActionListener(new restartL());
		v.mntmSetNumOf.addActionListener(new setBombL());
		v.mntmExit.addActionListener(new exitL());
		v.mntmHowToPlay.addActionListener(new helpL());
		v.mntmAbout.addActionListener(new aboutL());
		v.window.addKeyListener(new keyL());
	}
	public void show(int row, int col){
		m.show(row,col);
		update();
	}
	public void update() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				int[][] show = m.getShowArray();
				if(show[i][j]==0){
					board[i][j]= blank;
				}
				else if(show[i][j]==P4_Clark_Davin_MSModel.FLAG){
					board[i][j]= flag;
				}
				else {
					if(m.getBoard()[i][j]==P4_Clark_Davin_MSModel.BOMB){
						board[i][j]= bombDeath;
						dead = true;
					}
					else{
						board[i][j]= nums[m.getBoard()[i][j]];
					}
				}
				
			}
		}
	}
	//RUN BEFORE SHOWING ALL TILES
	public void deathUpdate(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				int[][] show = m.getShowArray();
				if(show[i][j]!=1){
					if(m.getBoard()[i][j]==P4_Clark_Davin_MSModel.BOMB){
						
						board[i][j]= bombRevealed;
						show[i][j]=1;
					}
				}
				if(show[i][j]==P4_Clark_Davin_MSModel.FLAG){
					board[i][j]= bombWrong;
				}
			}
		}
	}
	public void restart(){
		String message;
		if(dead){
			message="You DIED!!! Play Again?";
			
		}
		else{
			String c = "";
			if(cheated){
				c = "(with help)";
			}
			message = "You WON!!!"+c+" Play Again?";
		}
		int answer = v.restartPopup(message);
		if(answer == JOptionPane.YES_OPTION){
			m = new P4_Clark_Davin_MSModel(numBombs,size);
			dead = false;
			update();
			time = 0;
			timer.start();
		}
		cheated = true;
		
	}
	Timer timer = new Timer(1000,new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			time++;
			v.timeText.setText(time+"");
			if(time == 1){
				v.drawBoard(board, size);
			}
		}
		
	});

	class drawL extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (!dead) {
				int col = e.getY() / (v.getBoardSize() / size);
				int row = e.getX() / (v.getBoardSize() / size);
				System.out.println(row + "," + col);
				if (e.getButton() == MouseEvent.BUTTON3) {
					m.flag(row, col);
					
					
					v.flagText.setText((m.getNumBombs()-m.getNumFlags())+"");
					update();
				} else {
					show(row, col);
				}
				v.drawBoard(board, size);
				if (m.clear() || dead) {
					deathUpdate();
					v.drawBoard(board, size);
					timer.stop();
					
					restart();
				}
				v.drawBoard(board, size);
			}
		}
		
	}
	public int[][] combinedBoard(){
		int[][] combined = new int[size][size];
		for (int i = 0; i < combined.length; i++) {
			for (int j = 0; j < combined.length; j++) {
				if(m.getShowArray()[i][j]==1){
					combined[i][j]= m.getBoard()[i][j];
				}
				else{
					if(m.getShowArray()[i][j]==P4_Clark_Davin_MSModel.FLAG){
						combined[i][j]=C_FLAG;
					}
					if(m.getShowArray()[i][j]==0){
						combined[i][j]=C_HIDDEN;
					}
				}
			}
		}
		return combined;
	}
	class restartL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			m = new P4_Clark_Davin_MSModel(numBombs,size);
			dead = false;
			update();
			time = 0;
			timer.start();		
			v.drawBoard(board, size);
		}
		
	}
	class setBombL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			numBombs= Integer.parseInt(v.bombPopup());
			m = new P4_Clark_Davin_MSModel(numBombs,size);
			dead = false;
			update();
			time = 0;
			timer.start();		
			v.drawBoard(board, size);
		}
		
	}
	class exitL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			v.window.dispose();
			System.exit(0);
		}
		
	}
	class helpL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JEditorPane helpContent = null;
			try {
				helpContent = new JEditorPane(new URL("file:help.html"));
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			JScrollPane helpPane = new JScrollPane(helpContent);
			helpPane.setPreferredSize(new Dimension(400,600));
			JOptionPane.showMessageDialog(null, helpPane, "How To Play", JOptionPane.PLAIN_MESSAGE, null);
		}
		
	}
	class aboutL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JEditorPane helpContent = null;
			try {
				helpContent = new JEditorPane(new URL("file:about.html"));
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			JScrollPane helpPane = new JScrollPane(helpContent);
			helpPane.setPreferredSize(new Dimension(400,200));
			JOptionPane.showMessageDialog(null, helpPane, "About", JOptionPane.PLAIN_MESSAGE, null);
		}
		
	}
	class keyL implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyChar()=='a'){
				cheated = true;
				cBoard = combinedBoard();
				int state = getState();
				Point p;
				switch(state){
					case P4_Clark_Davin_MSAI.MODE_GUESS:
						p =randomPoint();
						m.show((int)p.getX(),(int)p.getY());
					
					case P4_Clark_Davin_MSAI.MODE_FLAG:
						ArrayList<Point> showlist = flagPoints();
						for (int i = 0; i < showlist.size(); i++) {
							p = showlist.get(i);
							m.flag((int)p.getX(),(int)p.getY());
							System.out.println("o");
						}
					
					case P4_Clark_Davin_MSAI.MODE_SHOW:
						ArrayList<Point> list = showPoints();
						for (int i = 0; i < list.size(); i++) {
							p = list.get(i);
							m.show((int)p.getX(),(int)p.getY());
							System.out.println("p");
						}
				
				}
				update();
				v.drawBoard(board, size);
				if (m.clear() || dead) {
					deathUpdate();
					v.drawBoard(board, size);
					timer.stop();
					
					restart();
					v.drawBoard(board, size);
				}
			}
			
		}

	
	}
	public int getState(){
		int state = 0;
		ArrayList<Point> flags = flagPoints();
		ArrayList<Point> shows = showPoints();
		if(shows.size()>flags.size()){
			state = MODE_SHOW;
		}
		else if(flags.size()>0){
			state = MODE_FLAG;
			
		}
		else {
			state = MODE_GUESS;
		}
		return state;
	}
	public ArrayList<Point> flagPoints(){
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < cBoard.length; i++) {
			for (int j = 0; j < cBoard.length; j++) {
				ArrayList<Point> flags = flagFromPoint(i,j);
				for (int k = 0; k < flags.size(); k++) {
					if(!list.contains(flags.get(k))){
						list.add(flags.get(k));
					}
				}
			}
		}
		return list;
	}
	public ArrayList<Point> flagFromPoint(int row, int col) {
		int bombs = 0;
		ArrayList<Point> list = new ArrayList<Point>();
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < cBoard.length && c > -1 && c < cBoard[1].length) {
				if (cBoard[r][c]==P4_Clark_Davin_MSConsole.C_FLAG){
					bombs++;
				}
				else if(cBoard[r][c]==P4_Clark_Davin_MSConsole.C_HIDDEN){
					list.add(new Point(r,c));
				}
			}
		}
		if (list.size()+bombs != cBoard[row][col]){
			list.clear();
		}
		return list;
	}
	public ArrayList<Point> showPoints(){
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < cBoard.length; i++) {
			for (int j = 0; j < cBoard.length; j++) {
				ArrayList<Point> flags = showFromPoint(i,j);
				for (int k = 0; k < flags.size(); k++) {
					if(!list.contains(flags.get(k))){
						list.add(flags.get(k));
					}
				}
			}
		}
		return list;
	}
	public ArrayList<Point> showFromPoint(int row, int col) {
		int bombs = 0;
		ArrayList<Point> list = new ArrayList<Point>();
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < cBoard.length && c > -1 && c < cBoard[1].length) {
				if (cBoard[r][c]==P4_Clark_Davin_MSConsole.C_FLAG){
					bombs++;
				}
				else if(cBoard[r][c]==P4_Clark_Davin_MSConsole.C_HIDDEN){
					list.add(new Point(r,c));
				}
			}
		}
		if (bombs != cBoard[row][col]){
			list.clear();
		}
		return list;
	}
	public Point randomPoint(){
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < cBoard.length; i++) {
			for (int j = 0; j < cBoard.length; j++) {
				if(cBoard[i][j]== P4_Clark_Davin_MSConsole.C_HIDDEN){
					list.add(new Point(i,j));
				}
			}
		}
		int random = (int)(Math.random()*list.size());
		return list.get(random);
		
		
	}
}
