package cellsociety_team01.CellState;
import javafx.scene.paint.Color;

public class State {
	protected Color myColor;
	protected String myName;

	public State(Color c, String s) {
		myColor = c;
		myName = s;
	}
	
	public State newInstanceOf(){
		return new State(myColor, myName);
	}

	//Updated -- equals MUST take an arg of type Object, not of a specific state - PW
	public boolean equals(Object s) {
		if(s == this) return true;
		State that = (State) s;
		return (this.getName().equals(that.getName()));
	}

	public int hashCode() {
		int ret = 0;
		for(int i = 0; i < myName.length(); i++){
			ret += myName.charAt(i) * (i + 1);
		}
		//ret += myColor.hashCode();
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
	
	//public void setInt(int a, int b){}
	public int getInt(int a){return 0;}
	
	public int getNumArgs(){return 0;} // THIS IS BAD - SHOULD MAKE EACH STATE HAVE A REINITIALIZE AS WELL

	public void setInt(int a, int b) {}
		// TODO Auto-generated method stub

	public boolean comparePopulations(State curState) {
		
		return(this.getName().equals(curState.getName()));
	}
		
	public String getDemonym(){return null;}
	
	public void updateColor(){return;}

	public boolean getFunc(){
		return false;
	}}
	

