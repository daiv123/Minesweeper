import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class P4_Clark_Davin_MSModel {
	private int numBombs;
	private int size;
	private int[][] showArray;
	private int[][] board;
	final static int FLAG =2;
	final static int BOMB = -1;
	private int numFlags=0;
	
	P4_Clark_Davin_MSModel(int num, int size){
		this.size = size;
		numBombs = num;
		showArray = new int[size][size];
		board = new int[size][size];
		addBombs();
		calcBoard();
	}
	
	public int getNumBombs() {
		return numBombs;
	}
	public void setNumBombs(int numBombs) {
		this.numBombs = numBombs;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int[][] getShowArray() {
		return showArray;
	}
	public void setShowArray(int[][] showArray) {
		this.showArray = showArray;
	}
	public int[][] getBoard() {
		return board;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	public void addBombs(){
		ArrayList <Point> allPoints = new ArrayList<Point>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				allPoints.add(new Point(i,j));
			}
		}
		for (int i = 0; i < numBombs; i++) {
			int random = (int)((allPoints.size())*Math.random());
			Point p = allPoints.get(random);
			allPoints.remove(random);
			board[(int) p.getX()][(int) p.getY()] = BOMB;
		}
	}
	public void calcBoard(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j]!=-1)
					board[i][j]= friend(i,j);
			}
		}
	}
	public void showAll(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				showArray[i][j]=1;
			}
		}
	}
	public int friend(int row, int col) {
		int count = 0;
		int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
		for (int i = 0; i < 8; i++) {
			int r = row + rowM[i];
			int c = col + colM[i];
			if (r > -1 && r < board.length && c > -1 && c < board[1].length) {
				if (board[r][c] == BOMB)
					count++;
			}
		}
		return count;
	}
	public boolean inBounds(int a){
		return a<size&&a>=0;
	}
	public void show(int row, int col){
		showArray[row][col]= 1;
		if(board[row][col]==0){

			int rowM[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
			int colM[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
			for (int i = 0; i < 8; i++) {
				int r = row + rowM[i];
				int c = col + colM[i];
				if (r > -1 && r < board.length && c > -1 && c < board[1].length) {
					if (showArray[r][c] ==0)
						show(r,c);
				}
			}
		}
		
		
	}
	public void flag(int row, int col){
		if(showArray[row][col]!=1){
			if(showArray[row][col]==FLAG){
				showArray[row][col]=0;
				setNumFlags(getNumFlags()-1);
			}
			
			else {
				showArray[row][col]=FLAG;
				setNumFlags(getNumFlags()+1);
			}
			
			
		}
		
	}
	public int getNumFlags() {
		return numFlags;
	}

	public void setNumFlags(int numFlags) {
		this.numFlags = numFlags;
	}

	public boolean clear(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(showArray[i][j]==0&&board[i][j]!=BOMB) return false;
			}
		}
		return true;
	}
	
	
}
