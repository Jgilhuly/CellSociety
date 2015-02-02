package cellsociety_team01;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Grid {
	// Rule myRule;
	// private Cell[][] cells;
	private GUI myView;
	private boolean simRunning;
	private int updateRate;

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

	}

	public void reset() {
	}

	public void changeUpdateRate(int newRate) {
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
		myView.update();
	}

}
