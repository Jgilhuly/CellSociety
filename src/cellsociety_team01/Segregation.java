package cellsociety_team01;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Cell;
import javafx.scene.paint.Color;

public class Segregation extends Simulation {
	
	private double mySatisfaction;
	private Random myRandom;
	
	public Segregation(){
		super();
		initialize();
	}
	
	private void initialize(){
		useDiags = false;
		myRandom = new Random();
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING'
		//RACE MUST BE THE STATE FOR THE GUI'S SAKE
		//CURRENTLY ONLY SUPPORTS 2 STATES (RACES)
		
		State race1 = new State("race1", Color.RED);
		State race2 = new State("race2", Color.BLUE);
		State empty = new State("empty", Color.WHITE);
		myStates.add(race1);
		myStates.add(race2);
		myStates.add(empty);
		
		
	}
	
	
	public void applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		double k = 0.0;
			for(Cell c: myNeighbors)
				if (c.getState().equals(cur.getState())) //if the neighbors have the same state as you
					k = k + 1.0;
		
		if (k/myNeighbors.size() > mySatisfaction)
			return;
		
		switchEmptyAdjacent(cur, myNeighbors);
	}
	
	public void switchEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		for (Cell c: myNeighbors)
			if (!(c.getState.equals(new State("empty", null)))) //REVISE THIS COMPARISON
				myNeighbors.remove(c);
			
		int i  = (int) Math.floor(myRandom.nextDouble()*myNeighbors.size());
		
		cur.setNextState(myNeighbors.get(i).getCurState());
		myNeighbors.get(i).setNextState(cur.getCurState());

	}
	
	
	
	public void setSatisfaction(double new){
		mySatisfaction  = new;
	}

}
