package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.modelview.Grid.gridEdgeTypes;
import cellsociety_team01.simulations.Simulation;

public abstract class GridModel {
	private RefineNeighborsAlgorithm[] rna = {new FiniteRefineNeighborsAlgorithm(), new InfiniteRefineNeighborsAlgorithm(), new ToroidalRefineNeighborsAlgorithm()};
	private RefineNeighborsAlgorithm myRefineAlgorithm;
	
	public GridModel(gridEdgeTypes myEdgeTypeIn) {
		if (myEdgeTypeIn == gridEdgeTypes.FINITE) {
			myRefineAlgorithm = rna[0];
		}
		else if (myEdgeTypeIn == gridEdgeTypes.INFINITE) {
			myRefineAlgorithm = rna[1];
		}
		else {
			myRefineAlgorithm = rna[2];
		}
	}

	/**
	 * Abstract Method that returns an array of neighbors in the cardinal
	 * directions from the cell, levels steps away from the cell
	 * 
	 * @param cell
	 * @param levels
	 * @return
	 */
	protected abstract ArrayList<Pair> getCardinalNeighbors(Cell cell,
			int levels);

	/**
	 * Abstract Method that returns an array of neighbors in all
	 * directions from the cell, levels steps away from the cell
	 * 
	 * @param cell
	 * @param levels
	 * @return
	 */
	protected ArrayList<Pair> getAllPossibleNeighbors(Cell cell, int levels) {
		ArrayList<Pair> ret = new ArrayList<Pair>();
		int x = cell.getX();
		int y = cell.getY();

		for (int i = -1 * levels; i <= levels; i++) {
			for (int j = -1 * levels; j <= levels; j++) {
				if (i != 0 || j != 0)
					ret.add(new Pair(x + i, y + j));
			}
		}

		return ret;
	}

	/**
	 * Finds the finite set neighbors of each cell
	 * @param cellMap
	 * @param simulation
	 * @param rows
	 * @param cols
	 */
	public void setNeighbors(Map<Pair, Cell> cellMap,
			Simulation simulation, int rows, int cols) {
		int neighborsType = simulation.getNeighborType();
		// 0 is 8 neighbors, 1 is cardinal 4 neighbors, else
		// number of outward steps in cardinal directions

		if (neighborsType == 0) {
			for (Pair pair : cellMap.keySet()) {
				ArrayList<Pair> possibleNeighbors = getAllPossibleNeighbors(
						cellMap.get(pair), 1);
				cellMap.get(pair)
						.getNeighbors()
						.addAll(verifyNeighbors(cellMap, rows, cols, pair,
								possibleNeighbors));
			}
		} else {
			for (Pair pair : cellMap.keySet()) {
				ArrayList<Pair> possibleNeighbors = getCardinalNeighbors(
						cellMap.get(pair), neighborsType);
				cellMap.get(pair)
						.getNeighbors()
						.addAll(verifyNeighbors(cellMap, rows, cols, pair,
								possibleNeighbors));
			}
		}
	}

	/**
	 * Checks the array of neighbors for legality, this will be overridden by
	 * subclasses
	 * 
	 * @param cellMap
	 * @param rows
	 * @param cols
	 * @param pair
	 * @param possibleNeighbors
	 * @return
	 */
	protected ArrayList<Cell> verifyNeighbors(Map<Pair, Cell> cellMap,
			int rows, int cols, Pair pair, ArrayList<Pair> possibleNeighbors) {
		return myRefineAlgorithm.refineNeighbors(cellMap, rows, cols, pair, possibleNeighbors);
	}

}
