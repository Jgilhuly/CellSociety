package cellsociety_team01;

import java.util.Random;

public class RandomRule extends Rule{
	
	private double myProb;
	Random myGen = new Random();
	
	public RandomRule(State start, State end, State cause, int prob){
		super(start, end, cause);
		myProb = prob;
	}
	
	public boolean applies(int k){
		while (k > 0){
			if (myGen.nextDouble() <= myProb)
				return true;
			k--;
		}
		return false;

	}
}