package cellsociety_team01.rules;

import java.util.ArrayList;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ConsumptionRule extends MovementRule{


	public State myEmpty;
	public double myTurnsThreshold;
	public int myDataIndex;

	public ConsumptionRule(State targetCellType, State emptyCellType, double t, int dataIndex){
		super(targetCellType);
		myEmpty = emptyCellType;
		myTurnsThreshold = t;
		myDataIndex = dataIndex;
	}

	public void kill(Cell cur, State target){
		cur.setCurState(myEmpty);
		cur.setNextState(myEmpty); // setting cur state without setting it as updated to allow for future cells to consider it as empty in the same step
	}

	public void apply(Cell cur, ArrayList<Cell> myNeighbors){
		cur.getCurState().setInt(myDataIndex, cur.getCurState().getInt(myDataIndex) + 1);

		if((cur.getCurState().getInt(myDataIndex) >= myTurnsThreshold)){ //checking turns since eating
			kill(cur, myEmpty);
			return;
		}

		//this DOES NOT randomize 
		Cell c = pickCell(myTargetState, myNeighbors);
		if(!(c == null)){
			eat(c, cur);
			return;
		}
		/*		else{ //ONLY set true if you've eaten, otherwise do NOT so that you can move later
			cur.setNextState(cur.getCurState());

			return;
		}*/
	}

	private void eat (Cell prey, Cell predator){	
		prey.setCurState(myEmpty);

		switchStatesUpdated(prey, predator, true, true);

		/*prey.setNextState(predator.getCurState());
		predator.setNextState(myEmpty); //GOOD WAY TO DO THIS?
		prey.setUpdated(true); //config: BUT, the place it takes IS updated
		predator.setUpdated(true); // CONTROVERSIAL : should recently vacated spots be able to be taken

		predator.setCurState(myEmpty); //config: a predator's vacated place CAN be taken immediately by the another object
		//System.out.println(((State) java.util.Arrays.asList(myData.keySet().toArray()).get(myData.keySet().size()-1)).getName());



		prey.setCurState(prey.getNextState());
		predator.setCurState(predator.getNextState());*/

		prey.getNextState().setInt(myDataIndex, 0); //resetting turns since eaten

	}
}
