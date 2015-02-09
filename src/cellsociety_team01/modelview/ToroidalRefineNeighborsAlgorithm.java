package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;

public class ToroidalRefineNeighborsAlgorithm extends RefineNeighborsAlgorithm {

	/**
	 * Refines the possible neighbors so that they wrap around the grid
	 */
	@Override
	public ArrayList<Cell> refineNeighbors(Map<Pair, Cell> cellMap, int rows,
			int cols, Pair pair, ArrayList<Pair> possibleNeighbors) {
		ArrayList<Cell> legalNeighbors = new ArrayList<Cell>();

		for (Pair possibleNeighbor : possibleNeighbors) {
			if (possibleNeighbor.getX() < 0)
				possibleNeighbor.setX(cols - 1);
			if (possibleNeighbor.getY() < 0)
				possibleNeighbor.setY(rows - 1);
			if (possibleNeighbor.getX() >= cols)
				possibleNeighbor.setX(0);
			if (possibleNeighbor.getY() >= rows)
				possibleNeighbor.setY(0);

			legalNeighbors.add(findCellForPair(cellMap, possibleNeighbor));
		}

		return legalNeighbors;
	}
}
