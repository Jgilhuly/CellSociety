package cellsociety_team01.rules;

import cellsociety_team01.CellState.State;

public class ThresholdRule extends Rule {

	public int myMin;
	public int myMax;
	
	public ThresholdRule(State start, State end, State cause, int min, int max){
		super(start, end, cause);
		myMin = min;
		myMax = max;
	}
	
	public State getStart(){
		return myStart;
	}
	
	public State getEnd(){
		return myEnd;
	}
	
	public State getCause(){
		return myCause;
	}
	
	public boolean applies(int k){
		return (k <= myMax && k >= myMin);
	}
	

}
