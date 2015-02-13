// This entire file is part of my masterpiece.
// Patrick Wickham

package cellsociety_team01.rules;

import java.util.ArrayList;
import java.util.Random;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class RandomRule extends ThresholdChangingRule{
	
	private double myProb;
	Random myGen = new Random();
	protected State myCause;
	
	public RandomRule(State start, State end, State cause, double prob){
		super(start, end);
		myCause = cause;
		myProb = prob;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		
		process(cur, myNeighbors, myCause);

	}
	
	public boolean applies(int k){
	if(myProb >= 1.0)
		return true;
		
		while (k > 0){
			if (myGen.nextDouble() <= myProb)
				return true;
			k--;
		}
		return false;
	}
}
