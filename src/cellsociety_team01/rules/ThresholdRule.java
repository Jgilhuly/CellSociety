// This entire file is part of my masterpiece.
// Patrick Wickham

package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ThresholdRule extends ThresholdChangingRule{

	protected int myMin;
	protected int myMax;
	protected State myCause;
	
	public ThresholdRule(State start, State end, State cause, int min, int max){
		super(start, end);
		myCause = cause;
		myMin = min;
		myMax = max;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		process(cur,myNeighbors, myCause);
	}
	
	public boolean applies(int k){
		return (k <= myMax && k >= myMin);
	}
}
