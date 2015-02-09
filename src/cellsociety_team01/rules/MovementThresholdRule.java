package cellsociety_team01.rules;

import cellsociety_team01.CellState.State;

public class MovementThresholdRule extends MovementRule{

	protected State myCause;
	protected double mySatisfactionThreshold;
	
	public MovementThresholdRule(State targetState, State cause, double t) {
		super(targetState);
		myCause = cause;
		mySatisfactionThreshold = t;
	}
	
	
	
	

}
