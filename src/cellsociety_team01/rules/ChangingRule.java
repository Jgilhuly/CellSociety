package cellsociety_team01.rules;

import cellsociety_team01.CellState.State;

public class ChangingRule extends Rule{
	
	protected State myStart;
	protected State myEnd;
	
	
	public ChangingRule(State start, State end){
		myStart = start;
		myEnd = end;
		
	}
	
	public State getStart(){
		return myStart;
	}
	
	public State getEnd(){
		return myEnd;
	}
	
	public boolean applies(int k){
		return (k>0);
	}
}
