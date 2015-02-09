package cellsociety_team01.rules;

import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;

public class averageUpdateRule extends Rule{
	
	private int myIndex;
	
	public averageUpdateRule(int i){
		super();
		myIndex = i;
	}
	
	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		
		double k = 0;
		for(Cell c: myNeighbors)
			k += c.getCurState().getInt(myIndex);
		
		cur.setNextState(cur.getCurState());
		cur.getNextState().setInt(myIndex, (int) (k/myNeighbors.size()));
	}

}
