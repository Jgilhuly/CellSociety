// This entire file is part of my masterpiece.
// Patrick Wickham
package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ThresholdChangingRule extends ChangingRule{
	
	public ThresholdChangingRule(State start, State end){
		super(start, end);
	}
	
	public void process(Cell cur, ArrayList<Cell> myNeighbors, State myCause){
		int k = 0;
		for(Cell c: myNeighbors)
			if(c.getCurState().equals(myCause))
				k++;
		if(applies(k)){
			cur.setNextState(myEnd);
			cur.setUpdated(true);
			return;
		}	
	}			
}
