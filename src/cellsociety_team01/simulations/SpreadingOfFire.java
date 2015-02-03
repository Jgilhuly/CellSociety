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
		State burning = new State(Color.RED, "burning");
		State tree = new State(Color.GREEN, "tree");
		State empty = new State(Color.YELLOW, "empty");
		
		myRules.add(new RandomRule(tree, burning, burning, probCatch));
		myRules.add(new RandomRule(burning, empty, null, 1.0)); //RANDOM WITH 1.0 = ABSOLUTE
	}

}
