package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ReproductionRule extends MovementRule {
	
	 // types of States that you can switch with
	protected double myTurnsThreshold;

	private int myDataIndex;
	
	public ReproductionRule(State targetCellType, double t, int dataIndex){ 
		super(targetCellType);
		
		myTurnsThreshold = t;
		myDataIndex = dataIndex;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		cur.getCurState().setInt(myDataIndex, cur.getCurState().getInt(myDataIndex) + 1);
		if(cur.isUpdated())
			return;

		System.out.println("TYPE " + cur.getCurState().getName() + " INT IN Q : " + cur.getCurState().getInt(myDataIndex));
		
		if(!(cur.getCurState().getInt(myDataIndex) >= myTurnsThreshold)) //checking turns since reproducing
			return;
		
		Cell s = pickCell(myTargetState, myNeighbors);
		if(s == null) // if there are no available Cells
			return;
		
		
		
		cur.setNextState(cur.getCurState());
		cur.getNextState().setInt(myDataIndex, 0); 
		cur.setUpdated(true);
		
		
		s.setNextState(cur.getCurState().newInstanceOf()); 
		s.setUpdated(true);
	}
}
