package cellsociety_team01.rules;

import java.util.ArrayList;
import java.util.Random;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class RandomRule extends ChangingRule{
	
	private double myProb;
	Random myGen = new Random();
	protected State myCause;
	
	public RandomRule(State start, State end, State cause, double prob){
		super(start, end);
		myCause = cause;
		myProb = prob;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated()) return;
		int k = 0;
		
		for(Cell c: myNeighbors)
			if(c.getCurState().equals(myCause)) //MAYBE PUT THE "UPDATED" CHECK IN GETNEIGHBORS?
				k++;
			
		if(applies(k)){
			cur.setNextState(myEnd);
			cur.setUpdated(true);
			return;
			}
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
