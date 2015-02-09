package cellsociety_team01.CellState;

import javafx.scene.paint.Color;

public class Race extends State{
	
	protected String myDemonym;

	public Race(Color c, String demonym) { // demonym(n) : the name (-onym) for a people (demos)
		super(c, "Race"); 
		myDemonym = demonym;
	}
	
	public String getDemonym(){
		return myDemonym;
	}
	
	public boolean comparePopulations(State s){	
		if(s == this) return true;
		Race that = (Race) s;
		return (this.getDemonym().equals(that.getDemonym()));
	}
}
