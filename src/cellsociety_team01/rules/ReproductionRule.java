package cellsociety_team01.rules;

import java.util.ArrayList;
import java.util.Random;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;

public class ReproductionRule extends MovementRule {
	
	 // types of States that you can switch with
	public double myTurnsThreshold;
	private Random myRandom = new Random();
	private int myDataIndex;
	
	public ReproductionRule(State targetCellType, double t, int dataIndex){ // myConfigs[0] in P/P
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
		
		Cell s = findRandomAdjacent(myTargetState, myNeighbors);
		if(s == null) // if there are no available Cells
			return;
		
		cur.setNextState(cur.getCurState());
		cur.getNextState().setInt(myDataIndex, 0); // would probably throw an NPE if not for the previous line
		cur.setUpdated(true);
		
		
		s.setNextState(cur.getCurState().newInstanceOf()); // SHOULD GIVE IT A NEW STATE EQUAL TO THE FIRST BUT WITH 0 AS VALUES FOR INTS
		s.setUpdated(true);
	}
}
