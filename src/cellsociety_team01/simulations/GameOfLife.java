package cellsociety_team01.simulations;



import java.util.ArrayList;

import cellsociety_team01.Cell;
import cellsociety_team01.State;
import cellsociety_team01.rules.ThresholdRule;
import javafx.scene.paint.Color;

public class GameOfLife extends Simulation{

	public GameOfLife(){
		super();
		initialize();
	}
	
	//BLACK = ALIVE
	//WHITE = DEAD
	
	private void initialize(){
		
		State dead = new State(Color.WHITE, "Dead");
		State alive = new State(Color.BLACK, "Alive");
		
		myStates.add(dead);
		myStates.add(alive);
		
		
		myRules.add(new ThresholdRule(alive, dead, alive, 0, 1));
		myRules.add(new ThresholdRule(alive, alive, alive, 2, 3));
		myRules.add(new ThresholdRule(alive, dead, alive, 4, 8));
		myRules.add(new ThresholdRule(dead, alive, alive, 3, 3));
	}
	
	//unwrapped find 8 neighbors 
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
		if (col < cols-2 && row < rows-2) {
			neighbors.add(cells[row+1][col+1]);
		}
		if (row > 0 && col < cols-2) {
			neighbors.add(cells[row-1][col+1]);
		}
		if (row < rows-2 && col > 0) {
			neighbors.add(cells[row+1][col-1]);
		}
		if (row > 0 && col > 0)
			neighbors.add(cells[row-1][col-1]);

		return neighbors;
	}
	
	}


