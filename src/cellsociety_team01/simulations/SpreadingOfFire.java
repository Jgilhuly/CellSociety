// This entire file is part of my masterpiece.
// Patrick Wickham

// submitted to show how simple it is to create an additional simulation
// and the extendability of our code

package cellsociety_team01.simulations;

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.rules.RandomRule;
import cellsociety_team01.rules.Rule;
import javafx.scene.paint.Color;

public class SpreadingOfFire extends Simulation {

	private double flammability;

	public SpreadingOfFire() {
		super();
	}

	public int getNeighborType() {
		return 1;
	}

	public void initialize() {
		State burning = new State(Color.web(myColorScheme.getString("teamA")),"burning");
		State tree = new State(Color.web(myColorScheme.getString("teamB")),	"tree");
		State empty = new State(Color.web(myColorScheme.getString("empty")),	"empty");

		myData.put(tree, new ArrayList<Rule>());
		myData.put(burning, new ArrayList<Rule>());
		myData.put(empty, new ArrayList<Rule>());

		myData.get(tree).add(new RandomRule(tree, burning, burning, flammability));
		myData.get(burning).add(new RandomRule(burning, empty, new State(Color.BLACK, "dummy"),1.0));
	}

	public void update(Cell cur, ArrayList<Cell> myNeighbors) {
		for (Rule r : myData.get(cur.getCurState())) {
			r.apply(cur, myNeighbors);
		}
		
		if (!cur.isUpdated())
			cur.setNextState(cur.getCurState());
	}

	public void parseConfigs(Map<String, String> configs) {
		flammability = Double.parseDouble(configs.get("sim_flammability"));
	}
}
