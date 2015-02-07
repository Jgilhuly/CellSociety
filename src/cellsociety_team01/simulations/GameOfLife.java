package cellsociety_team01.simulations;



import java.util.ArrayList;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.Rule;
import cellsociety_team01.rules.ThresholdRule;
import javafx.scene.paint.Color;

public class GameOfLife extends Simulation{

	public GameOfLife(){
		super();
		initialize();
	}
	
	//BLACK = ALIVE
	//WHITE = DEAD
	
	private void initialize(){
		
		State dead = new State(Color.WHITE, "Dead");
		State alive = new State(Color.BLACK, "Alive");
		
		myStates.add(alive);
		myStates.add(dead);
		
		myRules.add(new ThresholdRule(alive, dead, alive, 0, 1));
		myRules.add(new ThresholdRule(alive, alive, alive, 2, 3));
		myRules.add(new ThresholdRule(alive, dead, alive, 4, 8));
		myRules.add(new ThresholdRule(dead, alive, alive, 3, 3));
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		for (Rule r: myRules){
			System.out.println("RULE  " + myRules.indexOf(r));
			int k = 0;
			if (cur.getCurState().equals(r.getStart())){
				for(Cell c: myNeighbors)
					if(c.getCurState().equals(r.getCause()))
						k++;
				if(r.applies(k)){
					System.out.println("RULE APPLIED");
					cur.setNextState(r.getEnd());
					setUpdated(cur);
					return;
				}
			}
		}
		cur.setNextState(cur.getCurState());
		}
	
	//unwrapped find 8 neighbors 
	//written by John Gilhuly
	
	
	}


