package cellsociety_team01.simulations;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;

public class SlimeMolds extends Simulation{
	
	//parameters
	private static int PHEREMONE_MAX = 10;
	private static int SNIFF_BIAS = 1; // the amount that a neighbor's pheremone amt effects the randomness of the direction it moves
	
	public SlimeMolds(){
		super();
	}
	
	public void initialize(){
		State mold = new IntState(Color.GREEN, "mold", 1);
		State empty = new IntState(Color.BLUE, "empty", 1);
		
		myStates.add(mold);
		myStates.add(empty);	
	}
	
	
	//does NOT handle degradation - although it already should bc you're averaging?
	//causes a problem when the grid is closed - pheremones just build up all over the place
	public void updatePheremones(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.getCurState().equals(myStates.get(myStates.size() -1))){ //if it's not mold
			int a = 0;
			for(Cell c : myNeighbors)
				a += c.getCurState().getInt(0);
			a = a/myNeighbors.size(); // WILL CAUSE THE PHEREMONE TO BUILD UP IN THE CORNERS
			//perhaps divide it by the amount of neighbors every cell has instead?
			cur.getNextState().setInt(0,a);
			return;
		}
		
		else{ // if it is mold
			cur.getNextState().setInt(0, PHEREMONE_MAX);	
		}	
	}
	//returns null if there are no nearby surrounding cells
	public Cell findHighestEmptyAdjacent(ArrayList<Cell> myNeighbors){
		Cell c = new Cell(0, 0, new IntState(null, null, 1));
		c.getCurState().setInt(0, Integer.MIN_VALUE);
		for (Cell a : myNeighbors)
			if((a.getCurState().getInt(0)>c.getCurState().getInt(0)) && a.getCurState().equals(myStates.get(myStates.size()-1)))
				c = a;
		if(c.getCurState().getInt(0) > Integer.MIN_VALUE)
			return c;
		return null;
	}
	

	
	public void move(Cell cur, ArrayList<Cell> myNeighbors){
		Cell c = findHighestEmptyAdjacent(myNeighbors);
		if(c == null){ // if there are no empty neighbors
			cur.setNextState(cur.getCurState());
			cur.setUpdated(true);
			return;
		}
		
		swap(cur, c);
		c.setUpdated(true);
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		updatePheremones(cur, myNeighbors);
		if(cur.equals(myStates.get(0)))
			move(cur, myNeighbors);
	}

}
