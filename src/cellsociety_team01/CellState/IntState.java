package cellsociety_team01;
import javafx.scene.paint.Color;

public class IntState extends State {

	private int stateNum;

	public IntState(int i, Color c, String s){
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