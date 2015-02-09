package cellsociety_team01.simulations;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.DirectionalAutomaton;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.UphillMovementRule;

public class Sugarscape extends Simulation{
	
	public Sugarscape(){
		super();
		initialize();
		}
	
	private void initialize(){
		//0. amt. of sugar 1. sugarMetabolism 2. vision
		State actor = new DirectionalAutomaton(Color.BLACK, "actor", 3, true);
		
		//0. amt. of sugar 1. max capacity
		State empty = new IntState(Color.ORANGE, "empty", 2); 
		
		myData.put(actor, new ArrayList<Rule>());
		myData.put(empty, new ArrayList<Rule>());
		
		Rule move = new UphillMovementRule(empty, 0);
		
		myData.get(actor).add(move);
		//myData.get(actor).add(new Rule...
		//myData.get
		
		return;
		
		
	}

	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		for(Rule r: myData.get(cur))
			r.apply(cur, myNeighbors);
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
		
		return;
		
	}
	
}