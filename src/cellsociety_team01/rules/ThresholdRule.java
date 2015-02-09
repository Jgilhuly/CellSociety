package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ThresholdRule extends ChangingRule{

	public int myMin;
	public int myMax;
	protected State myCause;
	
	public ThresholdRule(State start, State end, State cause, int min, int max){
		super(start, end);
		myCause = cause;
		myMin = min;
		myMax = max;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		int k = 0;
		for(Cell c: myNeighbors)
			if(c.getCurState().equals(myCause))
				k++;
		if(applies(k)){
			cur.setNextState(myEnd);
			cur.setUpdated(true);
			return;
		}
		//cur.setNextState(cur.getCurState());
	}
	
	public boolean applies(int k){
		return (k <= myMax && k >= myMin);
	}
	

}
