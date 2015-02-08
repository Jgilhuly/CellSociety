package cellsociety_team01.simulations;

import java.util.ArrayList;


import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.Race;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.ThresholdMovementRule;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {
	
	private double[] myConfigs = new double[1];;
	
	public Segregation(){
		super();
		myConfigs[0] = 0.5; //satisfaction threshold
		initialize();
	}
	
	private void initialize(){
		State race1 = new Race(Color.RED, "race1");
		State race2 = new Race(Color.BLUE, "race2");
		State empty = new State(Color.WHITE, "empty");
		
		myData.put(race1, new ArrayList<Rule>());
		myData.put(race2, new ArrayList<Rule>()); // technically redundant to add this bc iteratively checking for the first will also do to the second due to .equals() equality, but STILL USEFUL for findState
		//HashMap SHOULD take this, because hashCodes depend on Color, and they have different colors
		//We WANT .equals() to be fooled because we want to a) iterate through the states independent of Race or Color, and b) because
		//we want the .get function to properly call the Rules
		myData.put(empty, new ArrayList<Rule>());
		
		Rule segMove = new ThresholdMovementRule(empty, myConfigs[0]);
		
		myData.get(race1).add(segMove);
		myData.get(race2).add(segMove);
	}
	
	public void setConfigs(ArrayList<String> configs){
		myConfigs = new double[configs.size()];
		for(int i = 0; i< configs.size(); i++)
			myConfigs[i] = Double.parseDouble(configs.get(i));
	}

	
	@Override
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		if(myData.get(cur.getCurState()).size() == 0)
			System.out.println(cur.getCurState().getName());
		for(Rule r : myData.get(cur.getCurState()))
			r.apply(cur,myNeighbors);
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState()); // because you didn't update them in the TMR
	
			}
}