import java.awt.Point;
import java.util.ArrayList;

public interface P4_Clark_Davin_MSAI2 {
	
	public final static int MODE_FLAG=1;
	public final static int MODE_GUESS = 0;
	public final static int MODE_SHOW = 2;

	
	public int getState();
	public ArrayList<Point> flagPoints();
	public ArrayList<Point> flagFromPoint(int row, int col);
	public ArrayList<Point> showPoints();
	public ArrayList<Point> showFromPoint(int row, int col);
	public Point randomPoint();
}
