package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.ColorChangeIntState;
import cellsociety_team01.CellState.IntState;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.averageUpdateRule;

public class SlimeMolds extends Simulation{
	
	private int myMaxPheremone;
	
	public SlimeMolds(){
		super();
	}

	
	public void initialize(){
		State amoebe = new IntState(Color.web(myColorScheme.getString("teamA")), "amoebe", 1);
		amoebe.setInt(0, myMaxPheremone);
		State empty = new ColorChangeIntState(Color.web(myColorScheme.getString("teamB")), "empty", 1, Color.web(myColorScheme.getString("teamB")), 0, myMaxPheremone/2);
		
		Rule updateHormone = new averageUpdateRule(0);
		
		myData.put(amoebe, new ArrayList<Rule>());
		myData.put(empty, new ArrayList<Rule>());
		
		myData.get(empty).add(updateHormone);
		
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		for(Rule r: myData.get(cur.getCurState()))
			r.apply(cur, myNeighbors);
		
		cur.getCurState().updateColor();

	}
	
	public void parseConfigs(Map<String, String> configs){
		myMaxPheremone = Integer.parseInt(configs.get("sim_max_value"));
}

}
