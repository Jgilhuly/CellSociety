package cellsociety_team01.simulations;

import java.util.ArrayList;

import cellsociety_team01.Cell;

public class PredatorPrey extends Simulation {
	
	public PredatorPrey(){
		super();
		initialize();
	}
	
	private void initialize(){
		
		
		
	}
	
	//wrapped find 4 neighbors
	
	public ArrayList<Cell> find4NeighborsWrap(Cell[][] cells, int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		else {
			neighbors.add(cells[row][0]);
		}
		
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		else {
			neighbors.add(cells[row][cols]);
		}
		
		if (row > 0) {
			neighbors.add(cells[row-1][col]);
		}
		else {
			neighbors.add(cells[rows][col]);
		}
		
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		else {
			neighbors.add(cells[0][col]);
		}
		
		return neighbors;
	}
	
	

}
