import java.util.*;
import javafx.scene.paint.Color;

public class State {
	private boolean myActive;
	private Color myColor;
	private String myName;

	public State(Color c, String s) {
		myActive = false;
		myName = s;
		myColor = c;
	}

	public boolean equals(State s) {
		if(this.hashCode() == s.hashCode())
			return true;
		return false;
	}

	public int hashCode() {
		int ret = 0;
		for(int i = 0; i < myName.length(); i++){
			ret += myName.get(i) * (i + 1);
		}
		ret += myColor.hashCode();
		return ret;
	}

	public void setActive(boolean active) {
		myActive = active;
	} 

	public void setColor(Color color) {
		myColor = color;
	}
}