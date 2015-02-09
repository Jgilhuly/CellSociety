package cellsociety_team01.modelview;

/**
 * Abstract class to define refineNeighbors hierarchy
 */

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;

public abstract class RefineNeighborsAlgorithm {

	public abstract ArrayList<Cell> refineNeighbors(Map<Pair, Cell> cellMap,
			int rows, int cols, Pair pair, ArrayList<Pair> possibleNeighbors);

	/**
	 * Finds the cell associated with a pair, necessary because vanilla get()
	 * map method uses pointers
	 * 
	 * @param cellMap
	 * @param pair
	 * @return
	 */
	protected Cell findCellForPair(Map<Pair, Cell> cellMap, Pair pair) {
		for (Pair cur : cellMap.keySet()) {
			if (cur.getX() == pair.getX() && cur.getY() == pair.getY()) {
				return cellMap.get(cur);
			}
		}
		return null;
	}
}
