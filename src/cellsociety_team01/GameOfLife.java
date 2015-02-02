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
	
	public void initialize(){
		public State dead = new State("Dead", Color.WHITE);
		public State alive - new State("Alive", Color.WHITE);
		myStates.addAll(dead, alive);
		
		myRules.add(new Rule(dead, alive, alive, 0, 2));
		myRules.add(new Rule(alive, alive, alive, 2, 3));
		myRules.add(new Rule(alive, dead, alive, 3, 8));
		myRules.add(new Rule(dead, alive, alive, 3, 3));
	}
	
	
	

		
	}

}
