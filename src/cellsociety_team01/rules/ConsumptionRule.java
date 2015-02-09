package cellsociety_team01.rules;

import java.util.ArrayList;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;

public class ConsumptionRule extends MovementRule {

	private State myEmpty;
	private double myTurnsThreshold;
	private int myDataIndex;

	public ConsumptionRule(State targetCellType, State emptyCellType, double t,
			int dataIndex) {
		super(targetCellType);
		myEmpty = emptyCellType;
		myTurnsThreshold = t;
		myDataIndex = dataIndex;
	}

	public void kill(Cell cur, State target) {
		cur.setCurState(myEmpty);
		cur.setNextState(myEmpty); // setting cur state without setting it as
									// updated to allow for future cells to
									// consider it as empty in the same step
	}

	public void apply(Cell cur, ArrayList<Cell> myNeighbors) {
		cur.getCurState().setInt(myDataIndex,
				cur.getCurState().getInt(myDataIndex) + 1);

		if ((cur.getCurState().getInt(myDataIndex) >= myTurnsThreshold)) { // checking
																			// turns
																			// since
																			// eating
			kill(cur, myEmpty);
			return;
		}

		Cell c = pickCell(myTargetState, myNeighbors);
		if (!(c == null)) {
			eat(c, cur);
			return;
		}

	}

	private void eat(Cell prey, Cell predator) {
		prey.setCurState(myEmpty);

		switchStatesUpdated(prey, predator, true, true);

		prey.getNextState().setInt(myDataIndex, 0);

	}
}
