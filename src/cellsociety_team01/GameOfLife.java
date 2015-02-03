package cellsociety_team01;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class GameOfLife extends Simulation{

	public GameOfLife(){
		super();
		initialize();
	}
	
	//BLACK = ALIVE
	//WHITE = DEAD
	
	private void initialize(){
		public State dead = new State("Dead", Color.WHITE, 0);
		public State alive - new State("Alive", Color.BLACK, 0);
		myStates.add(dead);
		myStates.add(alive);
		
		myRules.add(new ThresholdRule(dead, alive, alive, 0, 2));
		myRules.add(new ThresholdRule(alive, alive, alive, 2, 3));
		myRules.add(new ThresholdRule(alive, dead, alive, 3, 8));
		myRules.add(new ThresholdRule(dead, alive, alive, 3, 3));
	}
	
	
	

		
	}

}
