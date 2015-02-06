package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class IntState extends State {

	private int[] stateNums;

	public IntState(Color c, String s, int i){
		super(c, s);
		stateNums = new int[i];
		stateNums[0] = Integer.MIN_VALUE;
		stateNums[1] = Integer.MIN_VALUE;
	}

	public int[] getStateNums() {
		return stateNums;
	}
	
	public int getInt(int i){
		return stateNums[i];
	}

	public void setInt(int target, int value) {
		stateNums[target] = value;
	}
}