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
		myConfigs[0] = 0.7; //HARD CODED CONSTANT
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

	
	
	@Override
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		
		
		double k = 0.0;
			for(Cell c: myNeighbors)
				if (c.getCurState().equals(cur.getCurState())) //if the neighbors have the same state as you
					k = k + 1.0; // k = # neighbors that have same state as you
		
		if (k/myNeighbors.size() > myConfigs[0]){
			cur.setNextState(cur.getCurState());
			return;
		}
		switchEmptyAdjacent(cur, myNeighbors);

		return;
			}
	
	public void switchEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		ArrayList<Cell> temp = new ArrayList<Cell>();
		for (Cell c: myNeighbors)
			if ((!(c.getCurState().equals(new State(null, "empty"))))&&(!(c.isUpdated()))) //REVISE THIS COMPARISON
				temp.add(c);
		
		if(temp.size() == 0){
			cur.setNextState(cur.getCurState());
			return;
		}
		int i  = (int) Math.floor(myRandom.nextDouble()*temp.size());
		Cell c = temp.get(i);
		cur.setNextState(c.getCurState());
		c.setNextState(cur.getCurState());
		
		cur.setCurState(c.getCurState());
		c.setCurState(cur.getCurState());
		
		
		cur.setUpdated(true);
		c.setUpdated(true);
	}
	
	
	
	

}