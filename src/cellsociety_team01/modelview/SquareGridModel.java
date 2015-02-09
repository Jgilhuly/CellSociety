package cellsociety_team01.modelview;

import java.util.ArrayList;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;

public class SquareGridModel implements GridModel {

	public ArrayList<Pair> getAllPossibleNeighbors(Cell cell, int levels) {
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

	@Override
	public ArrayList<Pair> getCardinalPossibleNeighbors(Cell cell) {
		ArrayList<Pair> ret = new ArrayList<Pair>();
		int x = cell.getX();
		int y = cell.getY();

		ret.add(new Pair(x + 1, y));
		ret.add(new Pair(x - 1, y));
		ret.add(new Pair(x, y + 1));
		ret.add(new Pair(x, y - 1));

		return ret;
	}

}
