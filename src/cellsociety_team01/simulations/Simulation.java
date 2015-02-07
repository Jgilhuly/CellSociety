package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Color;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.modelview.Grid;
import cellsociety_team01.rules.Rule;

public class Simulation {

	Random myRandom = new Random();
	protected ArrayList<Rule> myRules;
	protected ArrayList<State> myStates;

	public Simulation(){
		myRules = new ArrayList<Rule>();
		myStates = new ArrayList<State>();
	}
	
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
		
		for (State s: myStates) 
			for (ArrayList<Cell> a: g) 
				for (Cell cur: a){ 
					if ((cur.getCurState().equals(s))){
						update(cur, grid.getNeighbors(cur));				
					}}
		return g;
	}
	
	public ArrayList<ArrayList<Cell>> getShuffledCopy(ArrayList<ArrayList<Cell>> g){
		ArrayList<ArrayList<Cell>> copy = new ArrayList<ArrayList<Cell>>(g);
		for(ArrayList<Cell> a: copy)
			Collections.shuffle(a);
		Collections.shuffle(copy);
		return copy;
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		return;
	}
	
	public void setUpdated(Cell c){
		c.setUpdated(true);
	}

	//finds a random adjacent Cell with target State, returns NULL if none
	public Cell findRandomAdjacentState(ArrayList<Cell> myNeighbors, State target){
			ArrayList<Cell> temp = new ArrayList<Cell>();
			for (Cell c: myNeighbors)
				if (c.getCurState().equals(target)&&(!(c.isUpdated())))
					temp.add(c);
			if(temp.size() == 0)
				return null;
			int i  = (int) Math.floor(myRandom.nextDouble()*temp.size());
			Cell empty = temp.get(i);
			return empty;
		}
		
	public void setConfigs(ArrayList<String> configs){
	}

	//might not need
	public boolean colorEquals(Color a, Color b){
		return (((a.getBlue() >= b.getBlue()-.1 )&&(a.getBlue() <= b.getBlue()+.1 ))&&
				((a.getGreen() >= b.getGreen()-.1)&&(a.getGreen() <= b.getGreen()+.1))&&
				((a.getRed() >= b.getRed()-.1)&&(a.getRed() <= b.getRed()+.1)));
	}

	//swaps two Cells' States
	public void swap(Cell a, Cell b){
		a.setNextState(b.getCurState());
		b.setNextState(a.getCurState());
	}
	
	//for use by GUI / Grid
	public State findState(Color c){
		for (State s: myStates){
			if(colorEquals(c, s.getColor()))//if(s.getColor().equals(c)) // MIGHT CAUSE PROBLEMS if the colors don's compare well
				return s;
		}
		return new State(Color.BLACK, "test"); // ONLY for default purposes
	}
	
	//for use by the GUI / Grid
	public ArrayList<State> getStates(){
		return myStates;
	}
	
	//for use by the GUI / Grid
	public State cycleNextState(State s){
		return myStates.get(myStates.indexOf(s) + 1); // WILL THIS WORK?? Wonder because each Cell has a NEW Instance of State. 
	}
	
	

}
