package cellsociety_team01.modelview;

import java.util.ArrayList;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.modelview.Grid.gridEdgeTypes;

public class TriangleGridModel extends GridModel {

	public TriangleGridModel(gridEdgeTypes myEdgeTypeIn) {
		super(myEdgeTypeIn);
	}

	/**
	 * Returns a list of all possible cardinal neighbors for a given cell
	 */
	@Override
	protected ArrayList<Pair> getCardinalNeighbors(Cell cell, int levels) {
		ArrayList<Pair> ret = new ArrayList<Pair>();
		int x = cell.getX();
		int y = cell.getY();

		for (int i = 1; i <= levels; i++) {
			ret.add(new Pair(x - i, y));
			ret.add(new Pair(x + i, y));

			if (x % 2 == 0) {
				ret.add(new Pair(x, y + i));
			} else {
				ret.add(new Pair(x, y - i));
			}
		}

		return ret;
	}

}
