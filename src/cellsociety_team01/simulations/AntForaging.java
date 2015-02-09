package cellsociety_team01.simulations;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.DirectionalAutomaton;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.PriorityForwardRule;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.ThresholdRule;

public class AntForaging extends Simulation{
	
	private int[] myConfigs = new int[2];
	
	public AntForaging(){
		super();
		myConfigs[0] = 100; // Max pheremone level
		initialize();
	}
	
	private void initialize(){
		//for ant, the func field corresponds to whether or not it has food
		State fullAnt = new DirectionalAutomaton(Color.RED, "ant", 2, true);
		State emptyAnt = new DirectionalAutomaton(Color.BROWN, "ant", 2, false);
		fullAnt.setInt(1, myConfigs[0]);
		emptyAnt.setInt(0, myConfigs[0]);
		
		State food = new State(Color.GREEN, "food");
		State home = new State(Color.BLUE, "home");
		State empty= new IntState(Color.WHITE, "empty", 2);
		
		myData.put(fullAnt, new ArrayList<Rule>());	//update full ones first
		myData.put(emptyAnt, new ArrayList<Rule>()); // update empty ones next
		myData.put(empty, new ArrayList<Rule>()); // do you update at all?
		myData.put(food, new ArrayList<Rule>());
		myData.put(home, new ArrayList<Rule>());
		
		Rule dropFood = new ThresholdRule(fullAnt, emptyAnt, home, 1, Integer.MAX_VALUE);
		Rule pickFood = new ThresholdRule(emptyAnt, fullAnt, food, 1, Integer.MAX_VALUE);
		Rule antMovement = new PriorityForwardRule(empty);
		
		myData.get(fullAnt).add(dropFood);
		myData.get(fullAnt).add(antMovement);
		myData.get(emptyAnt).add(pickFood);
		myData.get(emptyAnt).add(antMovement);
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		for(Rule r: myData.get(cur.getCurState()))
			r.apply(cur, myNeighbors);
		
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
		
	}
	
	

}
