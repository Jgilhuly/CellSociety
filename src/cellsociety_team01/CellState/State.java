package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class State {
	private Color myColor;
	private String myName;

	public State(Color c, String s) {
		myName = s;
		myColor = c;
	}

	public boolean equals(State s) {
		if(this.getName().equals(s.getName()))
			return true;
		return false;
	}

	public int hashCode() {
		int ret = 0;
		for(int i = 0; i < myName.length(); i++){
			ret += myName.charAt(i) * (i + 1);
		}
		ret += myColor.hashCode();
		return ret;
	}

	public void setColor(Color color) {
		myColor = color;
	}

	public void setName(String s) {
		myName = s;
	}

	public String getName() {
		return myName;
	}

	public Color getColor() {
		return myColor;
	}
	
	public int getInt() {
		return 0;
	}

	public void setInt (int i) {
	}
}