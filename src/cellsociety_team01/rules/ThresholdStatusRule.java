package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;

public class ThresholdStatusRule extends Rule{
	
	public int myStatusIndex;
	public int myChangeAmtIndex;
	public int myThresholdIndex;
	
	public ThresholdStatusRule(int statusIndex, int changeAmtIndex, int thresholdIndex){
		 	myStatusIndex = statusIndex;
		 	myChangeAmtIndex = changeAmtIndex;
			myThresholdIndex = thresholdIndex;
		
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		cur.getCurState().setInt(myStatusIndex, cur.getCurState().getInt(myStatusIndex) + cur.getCurState().getInt(myChangeAmtIndex));
		if(cur.getCurState().getInt(myStatusIndex) > cur.getCurState().getInt(myThresholdIndex))
			cur.getCurState().setInt(myStatusIndex, cur.getCurState().getInt(myThresholdIndex));		
	}

}
