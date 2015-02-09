package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class UphillMovementRule extends MovementRule{

	private int myDataIndex;
	//private State myEmpty;
	
	public UphillMovementRule(State targetCellType, int dataIndex){
		super(targetCellType);
		//myEmpty = emptyCellType;
		myDataIndex = dataIndex;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){ 
		if(cur.isUpdated())
			return;

		Cell c = pickCell(myTargetState, myNeighbors);

		if(!(c == null)){
			//cur.setNextState(c.getCurState());
			c.setNextState(cur.getCurState());
			c.getNextState().setInt(myDataIndex, c.getCurState().getInt(myDataIndex) + cur.getCurState().getInt(myDataIndex)); // consumes the sugar
			cur.setCurState(c.getCurState());
			c.setUpdated(true);
			return;
		}

		else{
			cur.setNextState(cur.getCurState());
			cur.setUpdated(true);
			return;
		}
	}
	
	public Cell pickCell(State target, ArrayList<Cell> myNeighbors){
		int k = Integer.MIN_VALUE;
		Cell d = null;
		
		//note -- this needs NON-UPDATED Cells, so update after in sim
		//gets the non-updated, empty cell with the largest value at index i
		for(Cell c : myNeighbors)
			if((!c.isUpdated())&&(c.getCurState().getInt(myDataIndex) > k)&&c.getCurState().equals(myTargetState)){
				k = c.getCurState().getInt(myDataIndex);
				d = c;
			}		
		return d;
	}
}
