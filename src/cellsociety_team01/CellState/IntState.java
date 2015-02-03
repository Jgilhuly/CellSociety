package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class IntState extends State {

	private int stateNum;

	public IntState(Color c, String s, int i){
		super(c, s);
		stateNum = i;
	}

	public int getInt() {
		return stateNum;
	}

	public void setInt(int i) {
		stateNum = i;
	}
}