package cellsociety_team01;

import java.util.ArrayList;

public class Simulation {
	
	public Rule[] myRules;
	public String myName;
	
	public Simulation(){
		myRules = new ArrayList<Rule>();
		myStates = new ArrayList<State>();
	}
	
	public State applyRules(State curState, Cell[] myNeighbors){
		for (Rule r: myRules){
			int k = 0;
			if (curState.equals(r.getStart()))
				for(Cell c: myNeighbors)
					if (c.getState().equals(r.getCause()))
						k++;
			if(r.isInRange(k))
				return r.getEnd();
		}
	}
	

}
