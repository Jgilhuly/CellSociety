package cellsociety_team01;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Grid {
	// Rule myRule;
	// private Cell[][] cells;
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
	
//	public Cell[][] getCells() {
//		return cells;
//	}

	private void updateCells() {
		// for (int i = 0; i < cells.length; i++) {
		// for (int j = 0; j < cells[i].length; i++) {
		//		cells[i][j].findNextState();
		// }
		// }
		
		// for (int i = 0; i < cells.length; i++) {
		// for (int j = 0; j < cells[i].length; i++) {
		//		cells[i][j].updateState();
		// }
		// }
	}

}
