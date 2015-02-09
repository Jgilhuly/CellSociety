package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;

public class Simulation {

	private static final String COLORSCHEME_RESOURCE_PACKAGE = "resources/ColorScheme/ColorScheme";
	protected Map<State, ArrayList<Rule>> myData;
	protected ResourceBundle myColorScheme;

	public Simulation(){
		myData = new LinkedHashMap<State, ArrayList<Rule>>();
	}

	public int getNumPossibleStates(){
		return myData.keySet().size();
	}
	
	//default implementation - used for SoF and GoL
	//essentially sums affecting neighbors and determines if they meet the threshold
	public ArrayList<ArrayList<Cell>> getShuffledCopy(ArrayList<ArrayList<Cell>> g){
		ArrayList<ArrayList<Cell>> copy = new ArrayList<ArrayList<Cell>>(g);
		for(ArrayList<Cell> a: copy)
			Collections.shuffle(a);
		Collections.shuffle(copy);
		return copy;
	}
	
	
	
	public int getNeighborType(){return 0;}
	//REALLY WANT EACH CELL TO HAVE A GETNEIGHBORS
	
	
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){	
		return;
	}
	
	public void setUpdated(Cell c){
		c.setUpdated(true);
	}


	public State findState(String identifier){
		for (State s: myData.keySet()){
			if(Color.web(myColorScheme.getString(identifier)).equals(s.getColor())){//if(s.getColor().equals(c))
				return s.newInstanceOf();}
		}
		return new State(Color.BLACK, "test"); // TEST PURPOSES ONLY
	}
	
	public Set<State> getStates(){
		return myData.keySet();
	}
	
	public void setConfigs(Map<String, String> a){
		myColorScheme = ResourceBundle.getBundle(COLORSCHEME_RESOURCE_PACKAGE, new Locale(a.get("sim_color_scheme")));
		parseConfigs(a);
		initialize();
		return;
	}
	
	public void initialize(){return;}
	
	public void parseConfigs(Map<String, String> a){
		return;
	}
	
	public State cycleNextState(State s){ // UNTESTED
		ArrayList<State> states = new ArrayList<State>();
		states.addAll(myData.keySet());
		int i = states.indexOf(s);
			if(i == states.size())
				i = 0;
		return states.get(i);	
				}

}
