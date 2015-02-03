package ModelView;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Grid {
	Simulation simulation;
	private Cell[][] cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;

	public Grid() {
	}

	public void setView(GUI viewIn) {
		myView = viewIn;
	}

	public boolean isSimRunning() {
		return simRunning;
	}

	public void play() {
		simRunning = true;
	}

	public void pause() {
		simRunning = false;
	}

	public void step() {
		updateCells();
		myView.update();
	}

	public void reset() {
	}

	public void changeUpdateRate(double newRate) {
		updateRate = newRate;
	}

	public void setSimulation(Simulation simulationIn) {
		simulation = simulationIn;
	}

	public void updateGrid(Cell[][] cellsIn) {
		cells = cellsIn;
	}

	public void setTitle(String titleIn) {
		myView.getStage().setTitle(titleIn);
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(int frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(1000 / updateRate), e -> update());
	}

	private void update() {
		updateCells();
		myView.update();
	}

	public Cell[][] getCells() {
		return cells;
	}

	private void updateCells() {
		Cell[] neighbors;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; i++) {
				if (simulation.useDiagonals())
				{
					neighbors = find8Neighbors(i, j);
				}
				else {
					neighbors = find4Neighbors(i, j);
				}
				cells[i][j].findNextState(neighbors, simulation);
				cells[i][j].updateState(neighbors, simulation);
			}
		}
	}

	private ArrayList<Cell> find8Neighbors(int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		if (row > 0) {
			neighbors[2].add(cells[row-1][col]);
		}
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		if (col < cols-2 && row < rows-2) {
			neighbors.add(cells[row+1][col+1]);
		}
		if (row > 0 && col < cols-2) {
			neighbors.add(cells[row-1][col+1]);
		}
		if (row < rows-2 && col > 0) {
			neighbors.add(cells[row+1][col-1]);
		}
		if (row > 0 && col > 0)
			neighbors.add(cells[row-1][col-1]);

		return neighbors;
	}

	private ArrayList<Cell> find4Neighbors(int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		if (row > 0) {
			neighbors[2].add(cells[row-1][col]);
		}
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		return neighbors;
	}

	private ArrayList<Cell> find8NeighborsWrap(int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		else {
			neighbors.add(cells[row][0]);
		}
		
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		else {
			neighbors.add(cells[row][cols]);
		}
		
		if (row > 0) {
			neighbors[2].add(cells[row-1][col]);
		}
		else {
			neighbors[2].add(cells[rows][col]);
		}
		
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		else {
			neighbors.add(cells[0][col]);
		}
		
		if (col < cols-2 && row < rows-2) {
			neighbors.add(cells[row+1][col+1]);
		}
		else {
			neighbors.add(cells[0][0]);
		}
		
		if (row > 0 && col < cols-2) {
			neighbors.add(cells[row-1][col+1]);
		}
		else {
			neighbors.add(cells[rows][0]);
		}
		
		if (row < rows-2 && col > 0) {
			neighbors.add(cells[row+1][col-1]);
		}
		else {
			neighbors.add(cells[0][cols]);
		}
		
		if (row > 0 && col > 0) {
			neighbors.add(cells[row-1][col-1]);
		}
		else {
			neighbors.add(cells[rows][cols]);
		}

		return neighbors;
	}
	
	private ArrayList<Cell> find4NeighborsWrap(int row, int col) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		int rows = cells.length;
		int cols = cells[0].length;

		if (col < cols-2) {
			neighbors.add(cells[row][col+1]);
		}
		else {
			neighbors.add(cells[row][0]);
		}
		
		if (col > 0) {
			neighbors.add(cells[row][col-1]);
		}
		else {
			neighbors.add(cells[row][cols]);
		}
		
		if (row > 0) {
			neighbors[2].add(cells[row-1][col]);
		}
		else {
			neighbors[2].add(cells[rows][col]);
		}
		
		if (row < rows-2) {
			neighbors.add(cells[row+1][col]);
		}
		else {
			neighbors.add(cells[0][col]);
		}
	}
}
