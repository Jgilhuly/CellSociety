package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.rules.Rule;

public class Simulation {

	protected ArrayList<Rule> myRules;
	protected ArrayList<State> myStates;

	public Simulation(){
		myRules = new ArrayList<Rule>();
		myStates = new ArrayList<State>();
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
	
	//REALLY WANT EACH CELL TO HAVE A GETNEIGHBORS
	
	public ArrayList<ArrayList<Cell>> updateGrid(Grid grid){ // MAKE SURE THIS ONLY GETS PASSED AN ITERABLE
		
		ArrayList<ArrayList<Cell>> g = grid.getCells();
		
		ArrayList<ArrayList<Cell>> copy = getShuffledCopy(g);
		
		
		for (State s: myStates) // organized in order of which move first
			for (ArrayList<Cell> a: copy) //iterating through the SHUFFLED VERSION
				for (Cell c: a)
					if ((!(g.get(g.indexOf(a)).get(g.get(g.indexOf(a)).indexOf(c)).isUpdated()))&&(c.getState().equals(s))){
						
						
						
						
						update (g.get(g.indexOf(a)).get(g.get(g.indexOf(a)).indexOf(c)), 
								grid.getNeighbors(c));
						
					}
		return g;
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		
		
		return;
	}
	
	
	public void setUpdated(Cell c){
		c.setUpdated(true);
	}
	
	/*public State applyRules(Cell cur, ArrayList<Cell> myNeighbors){
		for (Rule r: myRules){
			int k = 0;
			if (cur.getCurState().equals(r.getStart())){
				for(Cell c: myNeighbors)
					if(c.getCurState().equals(r.getCause()))
						k++;

				if(r.applies(k))
					return r.getEnd();
			}
		}

		return cur.getCurState();
	}*/

	public void setConfigs(ArrayList<String> configs){
	}

	public boolean colorEquals(Color a, Color b){
		
		return (((a.getBlue() >= b.getBlue())&&(a.getBlue() <= b.getBlue()))&&
				((a.getGreen() >= b.getGreen())&&(a.getGreen() <= b.getGreen()))&&
				((a.getRed() >= b.getRed())&&(a.getRed() <= b.getRed())));
	}

	public State findState(Color c){
		
		for (State s: myStates){
			if(colorEquals(c, s.getColor()))//if(s.getColor().equals(c))
				return s;
		}

		return null;//new State(Color.WHITE, "white"); // MIGHT CAUSE PROBLEMS IF THE COLORS DON'T COMPARE WELL
	}

	public  ArrayList<Cell> findNeighbors(Cell[][] cells, int row, int col){
		return null;
	}


}
