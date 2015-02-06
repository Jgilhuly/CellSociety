package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;

public class PredatorPrey extends Simulation {
	
	
	private double[] myConfigs = new double[2];
	
	public PredatorPrey(){
		super();
		myConfigs[0] = 1; //reproduction threshold for sharks and fish
		myConfigs[1] = 10; //death threshold for sharks
		initialize();
	}
	
	//shark -> fish -> ocean
	
	private void initialize(){
		
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING'
		//RACE MUST BE THE STATE FOR THE GUI'S SAKE
		//CURRENTLY ONLY SUPPORTS 2 STATES (RACES)
		
		
		//for this simulation, the first arg is the turns since reproduction (both), second is turns since eating (shark)
		State ocean = new IntState(Color.BLUE, "ocean", 2); //third arg is the NUMBER of INTS to keep track of
		State shark = new IntState(Color.RED, "shark", 2);
		State fish = new IntState(Color.YELLOW, "fish", 2);
		myStates.add(shark);
		myStates.add(fish);
		myStates.add(ocean);
	}
	
	private void eat (Cell fish, Cell shark){
		/*shark.getCurState().setInt(1, 0); // resetting turns since eaten
*/		fish.setNextState(shark.getCurState());
		shark.setNextState(myStates.get(myStates.size()-1)); //GOOD WAY TO DO THIS?
		shark.setCurState(myStates.get(myStates.size()-1)); //config: a shark's vacated place CAN be taken immediately by the another object
		
		fish.getNextState().setInt(1, 0); //resetting turns since eaten
		fish.setUpdated(true); //config: BUT, the place it takes IS updated
		System.out.println("EAT");
		/*fish.setCurState(fish.getNextState());
		shark.setCurState(shark.getNextState());*/
	}
	
	/*private void reproduce(Cell cur, ArrayList<Cell> myNeighbors){
		Cell s = findRandomEmptyAdjacent(cur, myNeighbors);
		//s.setCurState(cur.getCurState());
		s.setNextState(cur.getCurState());
		//s.getCurState().setInt(0);
		s.getNextState().setInt(0, 0); //setting turns alive
		s.getNextState().setInt(1, 0); //setting turns since eaten
		s.setUpdated(true);
		
		cur.getCurState().setInt(0, 0); //resetting turns since reproduced - DOES THIS WORK?
	}*/
	
	private void updateAlive(Cell cur){
		//THROWS ERROR -- 
		//next state is null on first cycle
		System.out.println(cur.getCurState().getName() + cur.getCurState().getInt(1));
		cur.getCurState().setInt(1, (cur.getCurState().getInt(1) + 1)); //update turns since eating

		if((cur.getCurState().getInt(1) >= myConfigs[1])){ //killing off if turns since eaten is too big
			cur.setCurState(new IntState(Color.BLUE, "ocean", 2));
			cur.setNextState(new IntState(Color.BLUE, "ocean", 2));	
			//cur.setUpdated(true);
			//NOT updated -- When a shark dies, a fish will be able to take its place immediately
		}
	}
	
	public State findState(Color c){
		for (State s: myStates){
			if(colorEquals(c, s.getColor()))//if(s.getColor().equals(c))
				return new IntState(s.getColor(), s.getName(), 2);
		}
		return new State(Color.BLACK, "test");
		}
	
	private void checkReproduce(Cell cur, ArrayList<Cell> myNeighbors){
		cur.getCurState().setInt(0, cur.getCurState().getInt(0) + 1);
		if(!(cur.getCurState().getInt(0) >= myConfigs[0])) //checking turns since reproducing
			return;
		Cell s = findRandomAdjacentState(myNeighbors, myStates.get(myStates.size() -1));
		if(s == null)
			return;
		s.setCurState(new IntState(cur.getCurState().getColor(), cur.getCurState().getName(), 2)); // NEEDS to have 2 ints
		s.getCurState().setInt(0, 0);
		s.getCurState().setInt(1,0);
		s.setUpdated(true);
		cur.setUpdated(true); // DO WE NEED THIS?
		cur.getCurState().setInt(0,0);
	}
	
	//returning null if there is no 
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		if(cur.isUpdated())
			return;
		
		//EGREGIOUS SWITCH STATEMENT
		
		if(!(cur.getNextState() == null))
			checkReproduce(cur, myNeighbors); 
		
// If fish
		if(cur.getCurState().getColor().equals(Color.YELLOW)){
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.RED)&&(!c.isUpdated())){	
					eat(cur,c);
					return;
				}}
// If shark
		else if(cur.getCurState().getColor().equals(Color.RED))
		{
			if(!(cur.getNextState() == null))
				updateAlive(cur); //ONLY update alive if it's a shark 
			//SHOULD THE ALIVE CHECK come after eating or before??
			
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.YELLOW)&&(!c.isUpdated())){
					eat(c, cur);
					return;
				}}
		
// ONLY REACH THIS IF YOU HAVENT EATEN/BEEN EATEN YET
			Cell s = findRandomAdjacentState(myNeighbors, myStates.get(myStates.size() -1));
			
			if(!(s == null)){
				cur.setNextState(s.getCurState());
				s.setNextState(cur.getCurState());
				
				cur.setCurState(s.getCurState());
				s.setCurState(cur.getCurState());
				s.setUpdated(true);
				cur.setUpdated(true);
				return;
			}
			
			else{
				
				cur.setNextState(cur.getCurState());
				cur.setUpdated(true);
				return;
			}
		
	}
	

}
