import java.awt.Point;
import java.util.ArrayList;

public class P4_Clark_Davin_MSAI {
	int[][] cBoard;
	int state = 0;
	public final static int MODE_FLAG=1;
	public final static int MODE_GUESS = 0;
	public final static int MODE_SHOW = 2;

	P4_Clark_Davin_MSAI(int[][] cBoard){
		this.cBoard=cBoard;
	}
	public int getState(){
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
	private ArrayList<Point> flagFromPoint(int row, int col) {
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
	private ArrayList<Point> showFromPoint(int row, int col) {
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
