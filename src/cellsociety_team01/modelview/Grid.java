package cellsociety_team01.modelview;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import cellsociety_team01.Cell;
import cellsociety_team01.simulations.Simulation;

public class Grid {
	Simulation simulation;
	private Cell[][] cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private String author;
	private File startingFile;

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
		// NEEDS IMPLEMENTATION
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
	
	public void setAuthor(String authorIn) {
		author = authorIn;
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(int frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(1000 / updateRate * 1000), e -> update());
	}
	
	public Cell[][] getCells() {
		return cells;
	}

	private void update() {
		if (simRunning) {			
			updateCells();
		}
		myView.update();
	}


	private void updateCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				ArrayList<Cell> neighbors = simulation.findNeighbors(cells, i, j);
				cells[i][j].findNextState(neighbors, simulation);
				cells[i][j].update();
			}
		}
	}
}
