package cellsociety_team01.modelview;

import java.util.ArrayList;

import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;

public interface GridModel {
	public ArrayList<Pair> getAllPossibleNeighbors(Cell cell, int levels);
	public ArrayList<Pair> getCardinalPossibleNeighbors(Cell cell);
}
