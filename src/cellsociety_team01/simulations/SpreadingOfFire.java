package cellsociety_team01.simulations;
import cellsociety_team01.State;
import cellsociety_team01.rules.RandomRule;
import javafx.scene.paint.Color;


public class SpreadingOfFire extends Simulation {
	
	public double probCatch;
	
	public SpreadingOfFire(){
		super();
		probCatch = 0.6; //HARD CODED
		initialize();
	}
	
	private void initialize(){
		public State burning = new State("burning", Color.RED, 2);
		public State tree = new State("tree", Color.GREEN, 1);
		public State empty = new State("empty", Color.YELLOW, 0);
		
		myRules.add(new RandomRule(tree, burning, burning, probCatch));
		myRules.add(new RandomRule(burning, empty, null, 1.0)); //RANDOM WITH 1.0 = ABSOLUTE
	}

}
