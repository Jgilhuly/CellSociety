package cellsociety_team01.simulations;
import java.util.ArrayList;

import cellsociety_team01.Cell;
import cellsociety_team01.State;
import cellsociety_team01.rules.RandomRule;
import javafx.scene.paint.Color;


public class SpreadingOfFire extends Simulation {
	
	
	private double[] myConfigs;
	public SpreadingOfFire(){
		super();
		 //HARD CODED
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
		myRules.add(new RandomRule(burning, empty, empty, 1.0)); //RANDOM WITH 1.0 = ABSOLUTE
	}
	
	public void setConfigs(String[] configs){
		myConfigs = new double[configs.length];
		for(int i = 0; i< configs.length; i++)
			myConfigs[i] = Double.parseDouble(configs[i]);
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
