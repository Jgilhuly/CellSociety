package cellsociety_team01.rules;

import cellsociety_team01.State;

public class Rule {
	
	public State myStart;
	public State myEnd;
	public State myCause;

	
	public Rule(State start, State end, State cause){
		myStart = start;
		myEnd = end;
		myCause = cause;
		
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
		return (k>0);
	}
	

}
