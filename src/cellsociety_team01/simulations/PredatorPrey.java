package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;

public class PredatorPrey extends Simulation {
	
	Random myRandom = new Random();
	private double[] myConfigs = new double[2];
	
	public PredatorPrey(){
		super();
		myConfigs[0] = 5; //reproduction threshold for sharks and fish
		myConfigs[1] = 2; //death threshold for sharks
		initialize();
	}
	
	private void initialize(){
		
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING'
		//RACE MUST BE THE STATE FOR THE GUI'S SAKE
		//CURRENTLY ONLY SUPPORTS 2 STATES (RACES)
		
		State fish = new IntState(Color.BLUE, "fish", 0);
		State shark = new IntState(Color.RED, "shark", 0);
		State kelp = new IntState(Color.GREEN, "kelp", 0);
		myStates.add(fish);
		myStates.add(shark);
		myStates.add(kelp);
	}
	
	private void eat (Cell fish, Cell shark){
		fish.setNextState(shark.getCurState());
		shark.setNextState(fish.getCurState());
		
		fish.setCurState(fish.getNextState());
		shark.setCurState(shark.getNextState());
	}
	
	private void reproduce(Cell cur, ArrayList<Cell> myNeighbors){
		
		
	}
	
	private void updateAlive(Cell cur){
		cur.getCurState().setInt(cur.getCurState().getInt() + 1);
		
		if(cur.getCurState().equals(new State(null, "shark"))
			&& (cur.getCurState().getInt() >= myConfigs[1])){
			cur.setCurState(new IntState(Color.GREEN, "kelp", 0));
			cur.setNextState(new IntState(Color.GREEN, "kelp", 0));	
		}
	}
	
	private void checkReproduce(Cell cur, ArrayList<Cell> myNeighbors){
		if(!(cur.getCurState().getInt() >= myConfigs[0]))
			return;
		
		Cell s = findEmptyAdjacent(cur, myNeighbors);
		
		s.setCurState(new IntState(cur.getCurState().getColor(), cur.getCurState().getName(), 0));
		
		
	}
	
	//returning null if there is no 
	public State applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		
		updateAlive(cur);
		checkReproduce(cur, myNeighbors);
		//EGREGIOUS SWITCH STATEMENT
		
		
		
		if(cur.getCurState().getColor().equals(Color.BLUE))
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.RED)){	
					eat(cur,c);
					return null;
				}
		if(cur.getCurState().getColor().equals(Color.RED))
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.BLUE)){
					eat(c, cur);
					return null;
				}

		Cell s = findEmptyAdjacent(cur, myNeighbors);
		
		cur.setNextState(s.getCurState());
		s.setNextState(cur.getCurState());
		
		cur.setCurState(cur.getCurState());
		s.setCurState(s.getCurState());
		
		return null;
	}
	
	public Cell findEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		ArrayList<Cell> temp = new ArrayList<Cell>();
		for (Cell c: myNeighbors)
			if (!(c.getCurState().equals(new State(null, "kelp")))) //REVISE THIS COMPARISON
				temp.add(c);
			
		int i  = (int) Math.floor(myRandom.nextDouble()*temp.size());
		
		Cell empty = temp.get(i);
		
		return empty;

	}
	
	//wrapped find 4 neighbors
	//written by John Gilhuly
	public ArrayList<Cell> findNeighbors(Cell[][] cells, int row, int col) {
		
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
