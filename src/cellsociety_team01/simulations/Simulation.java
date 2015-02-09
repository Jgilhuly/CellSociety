package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.rules.Rule;

public class Simulation {

	protected Map<State, ArrayList<Rule>> myData;

	public Simulation(){
		myData = new LinkedHashMap<State, ArrayList<Rule>>();
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
	
	public ArrayList<ArrayList<Cell>> updateGrid(Grid grid){ // MAKE SURE THIS ONLY GETS PASSED AN ITERABLE
	
		ArrayList<ArrayList<Cell>> g = grid.getCells();
		//ArrayList<ArrayList<Cell>> copy = getShuffledCopy(g);
		
		/*for (State s: myStates) // organized in order of which move first
			for (ArrayList<Cell> a: copy) //iterating through the SHUFFLED VERSION
				for (Cell c: a)
					if ((!(g.get(g.indexOf(a)).get(g.get(g.indexOf(a)).indexOf(c)).isUpdated()))&&(c.getCurState().equals(s)) ){
						update(g.get(g.indexOf(a)).get(g.get(g.indexOf(a)).indexOf(c)), 
								grid.getNeighbors(c));				
					}*/
		
		for (State s: myData.keySet()) 
			for (ArrayList<Cell> a: g) 
				for (Cell cur: a){ // cur = each cell in the grid
					if ((cur.getCurState().equals(s))){ // THIS SHOULD take both types of RACE at one time
						update(cur, grid.getNeighbors(cur));				
					}}
		return g;
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){	
		return;
	}
	
	public void setUpdated(Cell c){
		c.setUpdated(true);
	}


	public boolean colorEquals(Color a, Color b){ // IDEALLY DON'T HAVE TO USE THIS
		return (((a.getBlue() >= b.getBlue()-.1 )&&(a.getBlue() <= b.getBlue()+.1 ))&&
				((a.getGreen() >= b.getGreen()-.1)&&(a.getGreen() <= b.getGreen()+.1))&&
				((a.getRed() >= b.getRed()-.1)&&(a.getRed() <= b.getRed()+.1)));
	}

	public State findState(Color c){
		for (State s: myData.keySet()){
			if(colorEquals(c, s.getColor())){//if(s.getColor().equals(c))
				System.out.println(s.getName() + "  " + s.getDemonym());
				return s;}
		}
		return new State(Color.BLACK, "test"); // TEST PURPOSES ONLY
	}
	
	public Set<State> getStates(){
		return myData.keySet();
	}
	
	public void setConfigs(Map<String, String> a){
		return;
	}
	
	public State cycleNextState(State s){ // UNTESTED
		return (State) java.util.Arrays.asList(myData.keySet().toArray()).get(java.util.Arrays.asList(myData.keySet().toArray()).indexOf(s) + 1);
	}

}
