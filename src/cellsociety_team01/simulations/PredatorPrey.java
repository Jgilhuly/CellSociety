package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Map;
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
	
	private Random myRandom = new Random();
	private int myReproductionThreshold;
	private int myDeathThreshold;
	
	
	public PredatorPrey(){
		super();
	}
	
	
	public int getNeighborType(){return 1;}
	public void initialize(){
		//for this simulation, the first arg is the turns since reproduction (both), second is turns since eating (shark)
		State shark = new IntState(Color.web(myColorScheme.getString("teamA")), "shark", 2);
		State fish = new IntState(Color.web(myColorScheme.getString("teamB")), "fish", 2);
		State ocean = new State(Color.web(myColorScheme.getString("empty")), "ocean");
		
		myData.put(shark, new ArrayList<Rule>());
		myData.put(fish, new ArrayList<Rule>());
		myData.put(ocean, new ArrayList<Rule>());
		
		ConsumptionRule eatFish = new ConsumptionRule(fish, ocean, myReproductionThreshold, 1);
		ReproductionRule waterBirth = new ReproductionRule(ocean, myDeathThreshold, 0);
		MovementRule move = new MovementRule(ocean);
		
		myData.get(shark).add(eatFish);
		myData.get(shark).add(waterBirth);
		myData.get(shark).add(move);
		
		myData.get(fish).add(waterBirth);
		myData.get(fish).add(move);
	}

	//returning null if there is no 
	public void update(Cell cur, ArrayList<Cell> myNeighbors){

		if(cur.isUpdated())
			return;
		for(Rule r: myData.get(cur.getCurState())){
			r.apply(cur, myNeighbors);
		}
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());	
	}
	
	public void parseConfigs(Map<String, String> configs){
		myReproductionThreshold = Integer.parseInt(configs.get("sim_reproduction_threshold"));
		myDeathThreshold = Integer.parseInt(configs.get("sim_death_threshold"));

}
}
