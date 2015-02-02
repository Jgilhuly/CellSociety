package cellsociety_team01;

public class Rule {
	
	public State myStart;
	public State myEnd;
	public State myCause;
	public int myMin;
	public int myMax;
	
	public Rule(State start, State end, State cause, int min, int max){
		myStart = start;
		myEnd = end;
		myCause = cause;
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
	
	public boolean isInRange(int k){
		return (k <= myMax && k >= myMin);
	}
	

}
