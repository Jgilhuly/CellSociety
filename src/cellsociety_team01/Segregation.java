package cellsociety_team01;

import javafx.scene.paint.Color;

public class Segregation extends Simulation {
	
	private double mySatisfaction;
	
	public Segregation(){
		super();
		initialize();
	}
	
	private void initialize(){
		useDiags = false;
		
	    //RACE IS THE STATE, SATISFIED WILL BE THE STRING
		//CURRENTLY ONLY SUPPORTS 2 STATES
		
		State race1 = new State("race1", Color.RED);
		State race2 = new State("race2", Color.BLUE);
		State empty = new State("empty", Color.WHITE);
		myStates.add(race1);
		myStates.add(race2);
		myStates.add(empty);
		
		myRules.add(new ThresholdRule(State start, State end, State cause, int min, int max))
	}
	
	public void applyRules()
	
	
	public void setSatisfaction(double new){
		mySatisfaction  = new;
	}

}
