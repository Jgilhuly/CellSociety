package cellsociety_team01.simulations;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;

public class Simulation {
	
	protected ArrayList<Rule> myRules;
	protected ArrayList<State> myStates;
	
	public Simulation(){
		myRules = new ArrayList<Rule>();
		myStates = new ArrayList<State>();
	}
	
	//default implementation - used for SoF and GoL
	//essentially sums affecting neighbors and determines if they meet the threshold
	public State applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		for (Rule r: myRules){
			int k = 0;
			if (cur.getCurState().equals(r.getStart()))
				for(Cell c: myNeighbors)
					if(c.getCurState().equals(r.getCause()))
						k++;
			
			if(r.applies(k))
				return r.getEnd();
		}
		
		return cur.getCurState();
	}
	
	public void setConfigs(ArrayList<String> configs){
	}
	
	
	public State findState(Color c){
		for (State s: myStates)
			if(s.getColor().equals(c))
				return s;
		
		return null; // MIGHT CAUSE PROBLEMS IF THE COLORS DON'T COMPARE WELL
	}
	
	public  ArrayList<Cell> findNeighbors(Cell[][] cells, int row, int col){
		return null;
	}
	

}
