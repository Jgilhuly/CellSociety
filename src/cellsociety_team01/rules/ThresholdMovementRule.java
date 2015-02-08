package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ThresholdMovementRule extends MovementRule{
	
	public double myThreshold; // number of neighbors which have to be the same to be satisfied 
	
	public ThresholdMovementRule(State emptyState, double threshold) {
		super(emptyState);
		myThreshold = threshold;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		
		double numSimilarNeighbors = 0.0;
		
		for (Cell c: myNeighbors)
			if((!c.getCurState().equals(myTargetState))&&(c.getCurState().comparePopulations(cur.getCurState()))) // check for NON-EMPTY similar states
				numSimilarNeighbors = numSimilarNeighbors + 1.0;
		
		double numNonEmptyNeighbors = 0.0;
		
		for (Cell c: myNeighbors)
			if((!c.getCurState().equals(myTargetState))) // check for NON-EMPTY  states
				numNonEmptyNeighbors = numNonEmptyNeighbors + 1.0;
		
		
		System.out.println(numSimilarNeighbors + " / " + numNonEmptyNeighbors);
		//if((numSimilarNeighbors/numNonEmptyNeighbors) >= myThreshold) return; not sure which to use...
		if((numSimilarNeighbors/myNeighbors.size()) >= myThreshold) return;
		
		System.out.println("MOVEMENT THRESHOLD REACHED");
		
		Cell c = findRandomAdjacent(myTargetState, myNeighbors);
		
		
		if(!(c == null)){
			switchStatesUpdated(c, cur, true, false); // correctly configured call to swap an object and an empty spot to allow the vacated spot to be accessed
			return;
		}
	}

}
