package cellsociety_team01.simulations;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.ColorChangeIntState;
import cellsociety_team01.CellState.DirectionalAutomaton;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.StatusRule;
import cellsociety_team01.rules.ThresholdStatusRule;
import cellsociety_team01.rules.UphillMovementRule;

public class Sugarscape extends Simulation{
	
	final int MAX_VISION = 5;
	//final int MAX_SUGAR = 10;
	
	public Sugarscape(){
		super();
		initialize();
		}
	
	public int getNeighborType(){return MAX_VISION;}
	
	public void initialize(){
		//0. amt. of sugar 1. sugarMetabolism 2. vision
		State actor = new DirectionalAutomaton(Color.BLACK, "actor", 3, true);
		
		//0. amt. of sugar 1. sugarGrowBackRate 2. max capacity
		State empty = new ColorChangeIntState(Color.ORANGE, "empty", 2, 0); 
		
		myData.put(actor, new ArrayList<Rule>());
		myData.put(empty, new ArrayList<Rule>());
		
		Rule move = new UphillMovementRule(empty, 0);
		Rule updateAlive = new StatusRule(actor, empty, 0, 1);
		
		Rule updateSugarGrowth = new ThresholdStatusRule(0, 1, 2);
		
		myData.get(actor).add(move);
		myData.get(actor).add(updateAlive);
		
		myData.get(empty).add(updateSugarGrowth);
		
		
		return;
		
		
	}

	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		
		for(Rule r: myData.get(cur))
			r.apply(cur, myNeighbors);
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
		
		cur.getNextState().updateColor();
		
		return;
		
	}
	
}
