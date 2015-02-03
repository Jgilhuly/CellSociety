package cellsociety_team01.simulations;

import java.util.ArrayList;

import cellsociety_team01.Cell;
import cellsociety_team01.State;
import cellsociety_team01.rules.Rule;

public class Simulation {
	
	protected ArrayList<Rule> myRules;
	protected ArrayList<State> myStates;
	protected String myName; 
	
	
	public Simulation(){
		myRules = new ArrayList<Rule>();
		myStates = new ArrayList<State>();
	}
	
	public State applyRules(State curState, Cell[] myNeighbors){
		for (Rule r: myRules){
			int k = 0;
			if (curState.equals(r.getStart()))
				for(Cell c: myNeighbors)
					if (c.getCurState().equals(r.getCause()))
						k++;
			
			//K IS A METRIC THAT WEIGHS THE SURROUNDING CELLS -  DIFFERENT RULES WILL DO WITH IT WHAT THEY WANT (BELOW)
			
			if(r.applies(k))
				return r.getEnd();
		}
		
		return curState;
	}
	
	public  ArrayList<Cell> findNeighbors(Cell[][] cells, int row, int col){
		return null;
	}
	

}
