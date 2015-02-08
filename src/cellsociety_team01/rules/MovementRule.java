package cellsociety_team01.rules;

import java.util.ArrayList;
import java.util.Random;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

//Superclass for all rules that involve the updating of Cells other than the current Cell

public class MovementRule extends Rule{

	private Random myRandom = new Random();
	protected State myTargetState;

	public MovementRule(State targetState){
		myTargetState = targetState;
	}

	public void update(){}

	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;

		Cell c = findRandomAdjacent(myTargetState, myNeighbors);

		if(!(c == null)){
			//cur.setNextState(c.getCurState());
			c.setNextState(cur.getCurState());
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

	//RETURNS a random, non-updated cell of the given type out of the List
	//null if no such cell exists
	public Cell findRandomAdjacent(State target, ArrayList<Cell> myNeighbors){
		ArrayList<Cell> temp = new ArrayList<Cell>();
		for (Cell c: myNeighbors)
			if ((c.getCurState().equals(target))&&(!(c.isUpdated()))) //REVISE THIS COMPARISON
				temp.add(c);

		if(temp.size() == 0)
			return null;
		int i  = (int) Math.floor(myRandom.nextDouble()*temp.size());
		Cell empty = temp.get(i);
		return empty;
	}


	/*used to move States between Cells, booleans indicate which of the involve Cells will be considered Updated until next Cycle
	if a given Cell is not updated (such as the Cell vacated by a movement - this empty space could theoretically be used by a later
	Cell in the same turn), then we update its myCurState field as well to have it be considered empty.*/
	public void switchStatesUpdated(Cell a, Cell b, boolean aUpdated, boolean bUpdated){
		a.setNextState(b.getCurState());
		b.setNextState(a.getCurState());
		a.setUpdated(aUpdated);
		b.setUpdated(bUpdated);
		if(!aUpdated)
			a.setCurState(b.getCurState());
		if(!bUpdated)
			b.setCurState(a.getCurState());
	}
}
