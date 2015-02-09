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

	private void initialize(){
		State dead = new State(Color.WHITE, "Dead");
		State alive = new State(Color.BLACK, "Alive");
		
		myData.put(alive, new ArrayList<Rule>());
		myData.put(dead, new ArrayList<Rule>());

		myData.get(alive).add(new ThresholdRule(alive, dead, alive, 0, 1));
		myData.get(alive).add(new ThresholdRule(alive, alive, alive, 2, 3));
		myData.get(alive).add(new ThresholdRule(alive, dead, alive, 4, 8));
		myData.get(dead).add(new ThresholdRule(dead, alive, alive, 3, 3));
	}
	
	public void update(Cell cur, ArrayList<Cell> myNeighbors){
		for (Rule r: myData.get(cur.getCurState())) // relatively Important bug fix - call get with the STATE, and not the CELL
			r.apply(cur,myNeighbors);	
		
		if(!cur.isUpdated())
			cur.setNextState(cur.getCurState());	
		}
	}


