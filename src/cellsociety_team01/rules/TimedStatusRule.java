package cellsociety_team01.rules;

import cellsociety_team01.CellState.State;

public class TimedStatusRule extends ChangingRule{
	
	protected int myNumTurns;
	protected int myDataIndex;
	
	public TimedStatusRule(State start, State end, int numTurns, int dataIndex){
		super(start,end);
		myNumTurns = numTurns;
		myDataIndex = dataIndex;
		
	}

}
