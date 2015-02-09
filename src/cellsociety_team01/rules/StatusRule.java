package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class StatusRule extends ChangingRule{
	
	private int myStatusIndex;
	private int myChangeAmtIndex;
	
	public StatusRule(State start, State end, int statusIndex, int changeAmtIndex){
		super(start, end);
		myStatusIndex = statusIndex;
		myChangeAmtIndex = changeAmtIndex;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
	
		cur.getNextState().setInt(myStatusIndex, cur.getNextState().getInt(myStatusIndex) + cur.getNextState().getInt(myChangeAmtIndex));
		if(cur.getNextState().getInt(myStatusIndex) >= 0)
			cur.setNextState(myEnd);
		
	}

}
