package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.DirectionalAutomaton;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.PriorityForwardRule;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.ThresholdRule;

public class AntForaging extends Simulation{

	private int myMaxPheremoneLevel;
	
	public AntForaging(){
		super();
	}
	
	public void initialize(){
		//for ant, the func field corresponds to whether or not it has food
		State fullAnt = new DirectionalAutomaton(Color.web(myColorScheme.getString("teamA")).brighter().brighter(), "ant", 2, true);
		State emptyAnt = new DirectionalAutomaton(Color.web(myColorScheme.getString("teamA")), "ant", 2, false);
		fullAnt.setInt(1, myMaxPheremoneLevel);
		emptyAnt.setInt(0, myMaxPheremoneLevel);
		
		State food = new State(Color.web(myColorScheme.getString("teamB")), "food");
		State home = new State(Color.web(myColorScheme.getString("teamC")), "home");
		State empty= new IntState(Color.web(myColorScheme.getString("empty")), "empty", 2);
		
		myData.put(emptyAnt, new ArrayList<Rule>()); // update empty ones next
		myData.put(fullAnt, new ArrayList<Rule>());	//update full ones first
		myData.put(empty, new ArrayList<Rule>()); // do you update at all?
		myData.put(food, new ArrayList<Rule>());
		myData.put(home, new ArrayList<Rule>());
		
		Rule dropFood = new ThresholdRule(fullAnt, emptyAnt, home, 1, Integer.MAX_VALUE);
		Rule pickFood = new ThresholdRule(emptyAnt, fullAnt, food, 1, Integer.MAX_VALUE);
		Rule antMovement = new PriorityForwardRule(empty);
		
		myData.get(fullAnt).add(dropFood);
		myData.get(fullAnt).add(antMovement);
		myData.get(emptyAnt).add(pickFood);
		myData.get(emptyAnt).add(antMovement);
	}
	
	public int getNeighborType(){return 1;}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		for(Rule r: myData.get(cur.getCurState()))
			r.apply(cur, myNeighbors);
		
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());
		
	}
	
	public void parseConfigs(Map<String, String> configs){
		myMaxPheremoneLevel = Integer.parseInt(configs.get("sim_max_level"));

}
	

}
