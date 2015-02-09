package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class PriorityForwardRule extends MovementRule{
	
	public PriorityForwardRule(State myEmpty){
		super(myEmpty);
	}
	
	private int getIndex(State s, boolean b){
		if(b) // b = hasFood
			return 0; // return home pheremones
		return 1; // return food pheremones
	}
	
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		//if cur has food, i is the index of the home pheremones. otherwise, it's the index of the food pheremones
		int i = getIndex(cur.getCurState(), cur.getCurState().getFunc());	
		int k = Integer.MIN_VALUE;
		Cell d = null;
		
		//gets the non-updated, empty cell with the largest value at index i
		for(Cell c : myNeighbors)
			if((!c.isUpdated())&&(c.getCurState().getInt(i) > k)&&c.getCurState().equals(myTargetState)){
				k = c.getCurState().getInt(i);
				d = c;
			}
		
		//FOR NOW, only implements the max-finding, not the directional bias
		
		if(d == null)
			return; //does not update if there are no available Cells
		
		//DONT LIKE THIS WAY OF UPDATINGvvv
		
		cur.setNextState(d.getCurState());
		cur.getNextState().setInt(i, d.getCurState().getInt(i) - 10); //HARD CODED CONSTANTS
		cur.setUpdated(true);
		
		
		//DONT LIKE THIS WAY OF UPDATING^^^
		
		d.setNextState(cur.getCurState());
		d.setUpdated(true); //updating moved Cell
		
		cur.setCurState(d.getCurState()); //not updating the vacated cell
		
		
	
	}
	
	
}
