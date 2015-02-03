package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Random;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {
	
	private double[] myConfigs = new double[1];;
	private Random myRandom;
	
	public Segregation(){
		super();
		myConfigs[0] = 0.5; //HARD CODED CONSTANT
		initialize();
	}
	
	private void initialize(){
		
		myRandom = new Random();
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING'
		//RACE MUST BE THE STATE FOR THE GUI'S SAKE
		//CURRENTLY ONLY SUPPORTS 2 STATES (RACES)
		
		State race1 = new State(Color.RED, "race1");
		State race2 = new State(Color.BLUE, "race2");
		State empty = new State(Color.WHITE, "empty");
		myStates.add(race1);
		myStates.add(race2);
		myStates.add(empty);

	}
	
	public void setConfigs(ArrayList<String> configs){
		myConfigs = new double[configs.size()];
		for(int i = 0; i< configs.size(); i++)
			myConfigs[i] = Double.parseDouble(configs.get(i));
	}
	
	//unwrapped find 4 neighbors
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
	
	@Override
	public State applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		double k = 0.0;
			for(Cell c: myNeighbors)
				if (c.getCurState().equals(cur.getCurState())) //if the neighbors have the same state as you
					k = k + 1.0;
		
		if (k/myNeighbors.size() > myConfigs[0])
			return cur.getCurState();

		switchEmptyAdjacent(cur, myNeighbors);
		
		return null;
		
	}
	
	public void switchEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		ArrayList<Cell> temp = new ArrayList
		for (Cell c: myNeighbors)
			if (!(c.getCurState().equals(new State(null, "empty")))) //REVISE THIS COMPARISON
				myNeighbors.remove(c);
			
		int i  = (int) Math.floor(myRandom.nextDouble()*myNeighbors.size());
		
		cur.setNextState(myNeighbors.get(i).getCurState());
		
		myNeighbors.get(i).setNextState(cur.getCurState());
		
		cur.setCurState(cur.getNextState());
		
		myNeighbors.get(i).setCurState(myNeighbors.get(i).getNextState());
	}
	
	
	
	

}