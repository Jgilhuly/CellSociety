package cellsociety_team01.simulations;



import cellsociety_team01.State;
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
		
		myStates.add(dead);
		myStates.add(alive);
		
		
		myRules.add(new ThresholdRule(dead, alive, alive, 0, 2));
		myRules.add(new ThresholdRule(alive, alive, alive, 2, 3));
		myRules.add(new ThresholdRule(alive, dead, alive, 3, 8));
		myRules.add(new ThresholdRule(dead, alive, alive, 3, 3));
	}
	}


