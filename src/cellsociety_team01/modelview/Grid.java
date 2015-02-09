package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.simulations.Simulation;

public class Grid {
	Simulation simulation;
	private ArrayList<Cell> cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private String author;
	private Timeline myLoop;

	public Grid() {
	}

	public void setView(GUI viewIn) {
		myView = viewIn;
	}

	public boolean isSimRunning() {
		return simRunning;
	}

	public void setAnimationLoop(Timeline anIn) {
		myLoop = anIn;
	}
	
	public void play() {
		simRunning = true;
	}

	public void pause() {
		simRunning = false;
	}

	public void step() {
		updateGrid();
		myView.update(true);
	}

	public void changeUpdateRate(double newRate) {	
		myLoop.stop();
		KeyFrame frame = start(newRate);
		myLoop.setCycleCount(Animation.INDEFINITE);
		myLoop.getKeyFrames().add(frame);
		myLoop.play();
	}

	public void setConfigs(HashMap<String, String> configs) {
		
	}
	
	public void setSimulation(Simulation simulationIn) {
		simulation = simulationIn;
	}

	public void updateGrid(ArrayList<Cell> cellsIn) {
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
	public KeyFrame start(double frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(1000 / updateRate * 1000), e -> update());
	}
	
	public ArrayList<Cell> getCells() {
		return cells;
	}

	private void update() {
		if (simRunning) {			
			updateGrid();
		}
		myView.update(false);
	}

	private void setNotUpdated(){
		for (Cell c : cells) {
			c.setUpdated(false);
		}
	}
	
	private void updateGrid() {
		cells = simulation.updateGrid(cells);
		setNotUpdated();
	}
}
