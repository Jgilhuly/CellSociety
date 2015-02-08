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
		
		
		myData.put(tree, new ArrayList<Rule>());
		myData.put(burning, new ArrayList<Rule>());
		myData.put(empty, new ArrayList<Rule>());

		myData.get(tree).add(new RandomRule(tree, burning, burning, myConfigs[0]));
		myData.get(burning).add(new RandomRule(burning, empty, new State(Color.BLACK, "dummy"), 1.0));
		
	}
	
public void update(Cell cur, ArrayList<Cell> myNeighbors){
	
	for (Rule r: myData.get(cur.getCurState())){
		r.apply(cur, myNeighbors);
			}
	
	if(!cur.isUpdated())
		cur.setNextState(cur.getCurState());
	}
	
public void setConfigs(ArrayList<String> configs){
		myConfigs = new double[configs.size()];
		for(int i = 0; i< configs.size(); i++)
			myConfigs[i] = Double.parseDouble(configs.get(i));
	}
}
