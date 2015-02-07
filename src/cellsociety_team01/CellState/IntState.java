package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class IntState extends State {

	private int[] stateNums;

	public IntState(Color c, String s, int i){
		super(c, s);
		stateNums = new int[i];
		stateNums[0] = 0; // IS IT OKAY TO HAVE THESE HARD CODED?
		stateNums[1] = 0;
	}

	public int[] getStateNums() {
		return stateNums;
	}
	
	public void setAllInts(int c){
		for(int a: stateNums)
			a = c;
	}
	
	public int getInt(int i){
		return stateNums[i];
	}

	public void setInt(int target, int value) {
		stateNums[target] = value;
	}
}