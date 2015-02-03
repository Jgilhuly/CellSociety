package cellsociety_team01;
import javafx.scene.paint.Color;


public class SpreadingOfFire extends Simulation {
	
	public double myProbCatch;
	
	public SpreadingOfFire(){
		super();
		probCatch = 0.6; //HARD CODED
		useDiags = false; 
		initialize();
	}
	
	public double setProbCatch(double new){
		myProbCatch = new;
	}
	
	//THIS GETS USED WITH RANDOMRULE
	private void initialize(){
		public State burning = new State("burning", Color.RED);
		public State tree = new State("tree", Color.GREEN);
		public State empty = new State("empty", Color.YELLOW);
		
		myRules.add(new RandomRule(tree, burning, burning, probCatch));
		myRules.add(new RandomRule(burning, empty, null, 1.0)); //RANDOM WITH 1.0 = ABSOLUTE
	}

}
