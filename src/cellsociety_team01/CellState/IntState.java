package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class IntState extends State {

	private int[] stateNums;

	public IntState(Color c, String s, int i){
		super(c, s);
		stateNums = new int[i];
		initialize();
	}
	
	public State newInstanceOf(){
		return new IntState(myColor, myName, stateNums.length);
	}
	
	public void initialize(){
		for(@SuppressWarnings("unused") int a : stateNums)
			a = 0;
		return;
	}
	
	public boolean equals(State s) {
		return super.equals(s);
	}
	
	public int hashCode() {
		return super.hashCode();
	}
	
	public int getNumVars(){
		return stateNums.length;
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