package cellsociety_team01.rules;

import java.util.Random;

import cellsociety_team01.CellState.State;

public class RandomRule extends Rule{
	
	private double myProb;
	Random myGen = new Random();
	
	public RandomRule(State start, State end, State cause, double prob){
		super(start, end, cause);
		myProb = prob;
	}
	
	public boolean applies(int k){
	if(k == 1.0)
		return true;
		
		while (k > 0){
			if (myGen.nextDouble() <= myProb)
				return true;
			k--;
		}
		return false;

	}
}
