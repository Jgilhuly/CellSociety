package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.ConsumptionRule;
import cellsociety_team01.rules.MovementRule;
import cellsociety_team01.rules.ReproductionRule;
import cellsociety_team01.rules.Rule;

public class PredatorPrey extends Simulation {
	
	Random myRandom = new Random();
	private double[] myConfigs = new double[2];
	
	public PredatorPrey(){
		super();
		myConfigs[0] = 6; //reproduction threshold for sharks and fish
		myConfigs[1] = 5; //death threshold for sharks
		initialize();
	}
	
	//shark -> fish -> ocean
	
	private void initialize(){
		//for this simulation, the first arg is the turns since reproduction (both), second is turns since eating (shark)
		State shark = new IntState(Color.RED, "shark", 2);
		State fish = new IntState(Color.YELLOW, "fish", 2);
		State ocean = new State(Color.BLUE, "ocean");
		
		myData.put(shark, new ArrayList<Rule>());
		myData.put(fish, new ArrayList<Rule>());
		myData.put(ocean, new ArrayList<Rule>());
		
		ConsumptionRule eatFish = new ConsumptionRule(fish, ocean, myConfigs[1], 1);
		ReproductionRule waterBirth = new ReproductionRule(ocean, myConfigs[0], 0);
		MovementRule move = new MovementRule(ocean);
		
		myData.get(shark).add(eatFish);
		myData.get(shark).add(waterBirth);
		myData.get(shark).add(move);
		
		myData.get(fish).add(waterBirth);
		myData.get(fish).add(move);
	}
	
	/*private void eat (Cell fish, Cell shark){
		shark.getCurState().setInt(1, 0); // resetting turns since eaten
		 * 		
		
		fish.setNextState(shark.getCurState());
		shark.setNextState((State) java.util.Arrays.asList(myData.keySet().toArray()).get(myData.keySet().size()-1)); //GOOD WAY TO DO THIS?
		shark.setCurState((State) java.util.Arrays.asList(myData.keySet().toArray()).get(myData.keySet().size()-1)); //config: a shark's vacated place CAN be taken immediately by the another object
		System.out.println(((State) java.util.Arrays.asList(myData.keySet().toArray()).get(myData.keySet().size()-1)).getName());
		fish.getNextState().setInt(1, 0); //resetting turns since eaten
		fish.setUpdated(true); //config: BUT, the place it takes IS updated
		
		fish.setCurState(fish.getNextState());
		shark.setCurState(shark.getNextState());
	}*/

	
	/*private void updateAlive(Cell cur){
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
	}*/
	
	public State findState(Color c){
		for (State s: myData.keySet()){
			if(colorEquals(c, s.getColor()))//if(s.getColor().equals(c))
				return new IntState(s.getColor(), s.getName(), 2);
		}
		return new State(Color.BLACK, "test");
		}
	
	/*private void checkReproduce(Cell cur, ArrayList<Cell> myNeighbors){
		cur.getCurState().setInt(0, cur.getCurState().getInt(0) + 1);
		if(!(cur.getCurState().getInt(0) >= myConfigs[0])) //checking turns since reproducing
			return;
		Cell s = findRandomEmptyAdjacent(cur, myNeighbors);
		s.setCurState(new IntState(cur.getCurState().getColor(), cur.getCurState().getName(), 2)); // NEEDS to have 2 ints
		s.getCurState().setInt(0, 0);
		s.getCurState().setInt(1,0);
		s.setUpdated(true);
		cur.setUpdated(true);
		cur.getCurState().setInt(0,0);
	}*/
	
	//returning null if there is no 
	public void update(Cell cur, ArrayList<Cell> myNeighbors){

		if(cur.isUpdated())
			return;
		
		
		for(Rule r: myData.get(cur.getCurState())){
			r.apply(cur, myNeighbors);
		}
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
		
		
		//EGREGIOUS SWITCH STATEMENT
		
	/*	if(!(cur.getNextState() == null))
			checkReproduce(cur, myNeighbors); */
		
// If fish
		/*if(cur.getCurState().getColor().equals(Color.YELLOW)){
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.RED)&&(!c.isUpdated())){	
					System.out.println("EAT FROM FISH");
					eat(cur,c);
					return;
				}}*/
// If shark
		/*else if(cur.getCurState().getColor().equals(Color.RED))
		{
			if(!(cur.getNextState() == null))
				updateAlive(cur); //ONLY update alive if it's a shark 
			//SHOULD THE ALIVE CHECK come after eating or before??
			
			for(Cell c: myNeighbors)
				if(c.getCurState().getColor().equals(Color.YELLOW)&&(!c.isUpdated())){
					System.out.println("EAT FROM SHARK");
					eat(c, cur);
					return;
				}}*/
		
// ONLY REACH THIS IF YOU HAVENT EATEN YET
			/*Cell s = findRandomEmptyAdjacent(cur, myNeighbors);
			
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
			}*/
		
	}
	
	public Cell findRandomEmptyAdjacent(Cell cur, ArrayList<Cell> myNeighbors){
		ArrayList<Cell> temp = new ArrayList<Cell>();
		for (Cell c: myNeighbors)
			if (!(c.getCurState().equals(new State(null, "kelp")))&&(!(c.isUpdated()))) //REVISE THIS COMPARISON
				temp.add(c);
			
		if(temp.size() == 0)
			return null;
		int i  = (int) Math.floor(myRandom.nextDouble()*temp.size());
		Cell empty = temp.get(i);
		return empty;
	}
}
