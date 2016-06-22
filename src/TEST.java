import java.awt.Point;
import java.util.ArrayList;

public class TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] board ={{0,0,-2},
						{0,1,-1},
						{0,0,0}};
			
			
		
		P4_Clark_Davin_MSAI a = new P4_Clark_Davin_MSAI(board);
		ArrayList<Point>list =a.flagPoints();
		System.out.println(list);
		System.out.println(a.getState()+"");
		System.out.println("done");
	}

	
}
