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
		myConfigs[0] = 0.5; //HARD CODED CONSTANT
		initialize();
	}
	
	private void initialize(){
		
		State burning = new State(Color.RED, "burning");
		State tree = new State(Color.GREEN, "tree");
		State empty = new State(Color.YELLOW, "empty");
		
		myStates.add(burning);
		myStates.add(tree);
		myStates.add(empty);
		
		myRules.add(new RandomRule(tree, burning, burning, myConfigs[0]));

		myRules.add(new RandomRule(burning, empty, new State(Color.BLACK, "dummy"), 1.0)); //RANDOM WITH 1.0 = ABSOLUTE

	}
	
public void update(Cell cur, ArrayList<Cell> myNeighbors){
	for (Rule r: myRules){
		int k = 0;
		if (cur.getState().equals(r.getStart())){
			for(Cell c: myNeighbors)
				if(c.getState().equals(r.getCause()))
					k++;

			if(r.applies(k)){
				cur.setState(r.getEnd());
				setUpdated(cur);
			}
		}
	}
	}
	
	public void setConfigs(ArrayList<String> configs){
		myConfigs = new double[configs.size()];
		for(int i = 0; i< configs.size(); i++)
			myConfigs[i] = Double.parseDouble(configs.get(i));
	}
	//unwrapped find 4
	//written by John Gilhuly
	public ArrayList<Cell> findNeighbors(Cell[][] cells, int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		if (row > 0) {
			neighbors.add(cells[row-1][col]);
		}
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		return neighbors;
	}

}
