package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.Race;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.ThresholdMovementRule;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {
	private double mySatisfactionThreshold;
	
	public Segregation(){
		super();
	}
	public int getNeighborType(){return 0;}
	
	public void initialize(){

		State race1 = new Race(Color.web(myColorScheme.getString("teamA")), "race1");
		State race2 = new Race(Color.web(myColorScheme.getString("teamB")), "race2");
		State empty = new State(Color.web(myColorScheme.getString("empty")), "empty");
		
		myData.put(race1, new ArrayList<Rule>());
		myData.put(race2, new ArrayList<Rule>()); 
		myData.put(empty, new ArrayList<Rule>());
		
		Rule segMove = new ThresholdMovementRule(empty, mySatisfactionThreshold);
		
		myData.get(race1).add(segMove);
		myData.get(race2).add(segMove);
	}

	
	@Override
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;

		for(Rule r : myData.get(cur.getCurState()))
			r.apply(cur,myNeighbors);
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
	
			}
	
	public void parseConfigs(Map<String, String> configs){
		mySatisfactionThreshold = Double.parseDouble(configs.get("sim_satisfaction_threshold"));
}
}