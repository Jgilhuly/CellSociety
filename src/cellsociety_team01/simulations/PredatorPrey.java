package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import cellsociety_team01.Cell;
import cellsociety_team01.State;

public class PredatorPrey extends Simulation {
	
	Random myRandom = new Random();
	public PredatorPrey(){
		super();
		initialize();
	}
	
	private void initialize(){
		
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING'
		//RACE MUST BE THE STATE FOR THE GUI'S SAKE
		//CURRENTLY ONLY SUPPORTS 2 STATES (RACES)
		
		State fish = new State(Color.BLUE, "fish");
		State shark = new State(Color.RED, "shark");
		State kelp = new State(Color.WHITE, "green");
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
	
	
	//returning null if there is no 
	public State applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		
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

		switchEmptyAdjacent(cur, myNeighbors);
		return null;
	}
	
	public void switchEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		for (Cell c: myNeighbors)
			if (!(c.getCurState().equals(new State(null, "kelp")))) //REVISE THIS COMPARISON
				myNeighbors.remove(c);
			
		int i  = (int) Math.floor(myRandom.nextDouble()*myNeighbors.size());
		
		cur.setNextState(myNeighbors.get(i).getCurState());
		myNeighbors.get(i).setNextState(cur.getCurState());
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
