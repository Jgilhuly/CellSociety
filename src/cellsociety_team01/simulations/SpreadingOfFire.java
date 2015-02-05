package cellsociety_team01.simulations;
import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.RandomRule;
import cellsociety_team01.rules.Rule;
import javafx.scene.paint.Color;


public class SpreadingOfFire extends Simulation {
	
	
	private double[] myConfigs;
	public SpreadingOfFire(){
		super();
		myConfigs = new double[1];
		myConfigs[0] = 0.99; //HARD CODED CONSTANT
		initialize();
	}
	
	private void initialize(){
		
		State burning = new State(Color.RED, "burning");
		State tree = new State(Color.GREEN, "tree");
		State empty = new State(Color.YELLOW, "empty");
		
		myStates.add(tree);
		myStates.add(burning);
		myStates.add(empty);
		
		
		myRules.add(new RandomRule(tree, burning, burning, myConfigs[0]));
		myRules.add(new RandomRule(burning, empty, new State(Color.BLACK, "dummy"), 1.0)); //RANDOM WITH 1.0 = ABSOLUTE

	}
	
public void update(Cell cur, ArrayList<Cell> myNeighbors){
	for (Rule r: myRules){
		int k = 0;
		if (cur.getCurState().equals(r.getStart())){
			for(Cell c: myNeighbors)
				if(c.getCurState().equals(r.getCause())) //MAYBE PUT THE "UPDATED" CHECK IN GETNEIGHBORS?
					k++;
			if(r.applies(k)){
				System.out.println("RULE APPLIED");
				cur.setNextState(r.getEnd());
				setUpdated(cur);
				return;
			}
		}
	}
	cur.setNextState(cur.getCurState());
	}
	
	public void setConfigs(ArrayList<String> configs){
		myConfigs = new double[configs.size()];
		for(int i = 0; i< configs.size(); i++)
			myConfigs[i] = Double.parseDouble(configs.get(i));
	}
}
