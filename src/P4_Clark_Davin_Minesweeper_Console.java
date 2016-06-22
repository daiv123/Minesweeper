import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * 2/6/16
 * P4
 * 1.5 hrs
 * 
 * this lab was a lot easier than I expected it to be. The different parts of the
 * code were all something we had done before, and it felt really repetitive, but
 * not too bad. I had some trouble with stack overflow, but i fixed it pretty quickly.
 *
 */
public class P4_Clark_Davin_Minesweeper_Console {
	
	public static void main(String[] args){
		mine a = new mine(10,10);
		a.run();
	}
	
}
class mine{
	private int numBombs;
	int size;
	int[][] showArray;
	int[][] board;
	Scanner in = new Scanner(System.in);
	mine(int size, int numBombs){
		this.size=size;
		this.numBombs=numBombs;
		showArray = new int[size][size];
		board = new int[size][size];
		
		
		
	}
	public void print(){
		System.out.print("  ");
		for (int i = 0; i < board.length; i++) {
			System.out.print(i%10+" ");

		}
		System.out.println();
		for (int i = 0; i < board.length; i++) {
			String line = "";
			for (int j = 0; j < board[0].length; j++) {
				if(showArray[i][j]==1){
					if(board[i][j]==-1){
						line+="*";
					}
					else if(board[i][j]==0){
						line+=" ";
					}
					
					else{
						line+=board[i][j];
					}
				}
				else if(showArray[i][j]==2){
					line+="F";
				}
				else{
					line+="-";
				}
				line+=" ";
			}
			System.out.println(i+" "+line.substring(0, line.length()-1));
		}
		System.out.println();
	}
	public boolean flag(){
		
		System.out.print("Would you like to flag a cell or reveal a cell?\r\nEnter 'f' or 'r' :");
		char a = in.next().charAt(0);
		while(a!='f'&& a!='r'&&a!='F'&&a!='R'){
			System.out.println("\r\ntsk tsk follow directions\r\n");
			System.out.print("Would you like to flag a cell or reveal a cell?\r\nEnter 'f' or 'r' :");
			a = in.next().charAt(0);
		}
		return a=='f'||a=='F';
	}
	public Point input(){
		int x = 0;
		int y = 0;
		System.out.print("\r\nEnter row:");
		y = in.nextInt();
		System.out.print("Enter col:");
		x = in.nextInt();
		return new Point(x,y);
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
			board[(int) p.getX()][(int) p.getY()] = -1;
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
				if (board[r][c] == -1)
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
		showArray[row][col]=2;
	}
	public boolean clear(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(showArray[i][j]!=1) return false;
				if(showArray[i][j]==2&&board[i][j]!=-1) return false;
			}
		}
		return true;
	}
	public void run(){
		System.out.println("┏━┓┏━┓╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏┓\r\n"+
"┃┃┗┛┃┃╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┃┃\r\n"+
"┃┏┓┏┓┣┳━┓┏━━┳━━┳┓┏┓┏┳━━┳━━┳━━┳━━┳━┫┃\r\n"+
"┃┃┃┃┃┣┫┏┓┫┃━┫━━┫┗┛┗┛┃┃━┫┃━┫┏┓┃┃━┫┏┻┛\r\n"+
"┃┃┃┃┃┃┃┃┃┃┃━╋━━┣┓┏┓┏┫┃━┫┃━┫┗┛┃┃━┫┃┏┓\r\n"+
"┗┛┗┛┗┻┻┛┗┻━━┻━━┛┗┛┗┛┗━━┻━━┫┏━┻━━┻┛┗┛\r\n"+
"╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┃┃\r\n"+
"╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗┛\r\n"
);
		boolean alive = true;
		addBombs();
		calcBoard();
		while(alive&&!clear()){
			print();
			boolean flag = flag();
			Point p  = input();
			int row = (int)p.getY();
			int col = (int)p.getX();
			while(!inBounds(row)||!inBounds(col)){
				System.out.println("\r\nThts not in the grid try again");
				p  = input();
				row = (int)p.getY();
				col = (int)p.getX();
			}
			if(flag){
				flag(row,col);
			}
			else{
				if(board[row][col]==-1){
					alive = false;
					break;
				}
				else{
					show(row,col);
				}
			}
			System.out.println("------------------------------------------------\r\n");
		}
		if(clear()){
			System.out.println("wow you won!");
		}
		else{
			System.out.println("You lose :P");
		}
		
		showAll();
		print();
		
	}
}