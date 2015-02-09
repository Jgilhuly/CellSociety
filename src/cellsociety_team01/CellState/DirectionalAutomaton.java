package cellsociety_team01.CellState;

import javafx.scene.paint.Color;

public class DirectionalAutomaton extends IntState{
	
	private boolean myFunc;
	//func in this instance is hasFood
	public DirectionalAutomaton(Color c, String name, int i, boolean func){
		super(c,name,i);
		myFunc = func;
		initialize();
	}
	
	public boolean getFunc(){
		return myFunc;
	}
	

}
