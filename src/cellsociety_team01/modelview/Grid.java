package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;
import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.exceptions.BadGridConfigException;
import cellsociety_team01.simulations.Simulation;

public class Grid {

	public static enum gridShapeTypes {
		SQUARE, TRIANGULAR
	};

	public static enum gridEdgeTypes {
		FINITE, INFINITE, TOROIDAL
	};

	Simulation simulation;
	private ArrayList<Cell> cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private Timeline myLoop;
	private String author;
	int cols, rows;
	private GridModel myGridModel;
	private gridShapeTypes myCellShape;
	private gridEdgeTypes myEdgeType;
	private boolean gridOutline;

	public Grid() {
		simRunning = false;
	}

	/**
	 * Called by Parser to set up the grid / cells
	 * 
	 * @param configs
	 */
	public void setConfigs(Map<String, String> configs) {
		String grid_edge = configs.get("grid_edge");
		if (grid_edge.equals("Finite"))
			myEdgeType = gridEdgeTypes.FINITE;
		else if (grid_edge.equals("Infinite"))
			myEdgeType = gridEdgeTypes.INFINITE;
		else if (grid_edge.equals("Toroidal"))
			myEdgeType = gridEdgeTypes.TOROIDAL;
		else {
			BadGridConfigException e = new BadGridConfigException();
			e.handleException();
		}

		String grid_shape = configs.get("grid_shape");
		if (grid_shape.equals("Square")) {
			myGridModel = new SquareGridModel(myEdgeType);
			myCellShape = gridShapeTypes.SQUARE;
		} else if (grid_shape.equals("Triangle")) {
			myGridModel = new TriangularGridModel(myEdgeType);
			myCellShape = gridShapeTypes.TRIANGULAR;
		} else {
			BadGridConfigException e = new BadGridConfigException();
			e.handleException();
		}

		String grid_outline = configs.get("grid_outline");
		if (grid_outline.equals("Yes"))
			gridOutline = true;
		else
			gridOutline = false;
	}

	/**
	 * Sets this models view controller
	 * 
	 * @param viewIn
	 */
	public void setView(GUI viewIn) {
		myView = viewIn;
	}

	/**
	 * Called by parser to set rows and cols
	 * 
	 * @param bounds
	 */
	public void setBounds(Pair bounds) {
		cols = bounds.getX();
		rows = bounds.getY();
	}

	/**
	 * Sets the animation loop, used during initialization
	 * 
	 * @param anIn
	 */
	public void setAnimationLoop(Timeline anIn) {
		myLoop = anIn;
	}

	/**
	 * Sets which simulation to use
	 * 
	 * @param simulationIn
	 */
	public void setSimulation(Simulation simulationIn) {
		simulation = simulationIn;
	}

	/**
	 * Sets the author, provided by the XML file
	 * 
	 * @param authorIn
	 */
	public void setAuthor(String authorIn) {
		author = authorIn;
	}

	/**
	 * Sets the title of the simulation
	 * 
	 * @param titleIn
	 */
	public void setTitle(String titleIn) {
		myView.getStage().setTitle(titleIn);
	}

	/**
	 * Called during initialization to set which cells are neighbors
	 * 
	 * @param cellMap
	 */
	public void setNeighbors(Map<Pair, Cell> cellMap) {
		myGridModel.setNeighbors(cellMap, simulation, rows, cols);
	}

	/**
	 * updates the Grid with a new collection of cells
	 * 
	 * @param cellsIn
	 */
	public void updateGrid(Collection<Cell> cellsIn) {
		cells = new ArrayList<Cell>();
		cells.addAll(cellsIn);
	}

	/**
	 * Creates the game's frame
	 */
	public KeyFrame start(double frameRate) {
		updateRate = frameRate;
		return new KeyFrame(Duration.millis(updateRate * 10), e -> update());
	}

	/**
	 * Changes the sim refresh rate
	 * 
	 * @param newRate
	 */
	public void changeUpdateRate(double newRate) {
		myLoop.stop();

		myLoop = new Timeline(newRate);
		KeyFrame frame = start(newRate);
		myLoop.setCycleCount(Animation.INDEFINITE);
		myLoop.getKeyFrames().add(frame);
		myLoop.play();
	}

	/**
	 * Handles pressing of the play button
	 */
	public void play() {
		simRunning = true;
	}

	/**
	 * Handles pressing of the pause button
	 */
	public void pause() {
		simRunning = false;
	}

	/**
	 * Handles pressing of the step button
	 */
	public void step() {
		updateCells();
		updateCurStates();
		myView.update(true);
	}

	/**
	 * Main update method
	 */
	public void update() {
		if (simRunning) {
			updateCells();
			updateCurStates();
		}
		myView.update(false);
	}

	/**
	 * Tells each cell to determine its next state
	 */
	private void updateCells() {
		for (State s : simulation.getStates()) {
			for (Cell cur : cells) {
				if ((cur.getCurState().equals(s))) {
					simulation.update(cur, cur.getNeighbors());
				}
			}
		}
		setNotUpdated();
	}

	/**
	 * Sets each cell to be not updated, used to prevent cells moving during an
	 * update sweep
	 */
	private void setNotUpdated() {
		for (Cell c : cells) {
			c.setUpdated(false);
		}
	}

	/**
	 * Tells each cell to update their current states
	 */
	public void updateCurStates() {
		for (Cell c : cells)
			c.updateCurState();
	}

	/**
	 * Cycles the state of a given cell
	 * 
	 * @param cellX
	 * @param cellY
	 */
	public void cycleCellState(int cellX, int cellY) {
		for (Cell c : cells) {
			if (c.getX() == cellX && c.getY() == cellY) {
				c.setCurState(simulation.cycleNextState(c.getCurState()));
			}
		}
	}

	/**
	 * check is the XML file said to outline the grid cells
	 * 
	 * @return
	 */
	public boolean isGridOutlined() {
		return gridOutline;
	}

	/**
	 * Checks if the simulation is running
	 * 
	 * @return
	 */
	public boolean isSimRunning() {
		return simRunning;
	}

	/**
	 * Gets number of rows
	 * 
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * gets number of cols
	 * 
	 * @return
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Returns cells
	 * 
	 * @return
	 */
	public ArrayList<Cell> getCells() {
		return cells;
	}

	/**
	 * Returns which shape the cells are
	 * 
	 * @return
	 */
	public gridShapeTypes getShape() {
		return myCellShape;
	}

	/**
	 * gets the author
	 * 
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Fills a series with data on State population, CURRENTLY UNIMPLEMENTED
	 * 
	 * @param series
	 * @param tick
	 * @return
	 */
	public Series fillGraphSeries(Series series, int tick) {
		Map<State, Integer> stateCounts = new HashMap<State, Integer>();

		for (Cell c : cells) {
			if (c.getCurState() != null
					&& stateCounts.containsKey(c.getCurState())) {
				stateCounts.put(c.getCurState(),
						stateCounts.get(c.getCurState()) + 1);
			} else {
				stateCounts.put(c.getCurState(), 1);
			}
		}

		for (Integer i : stateCounts.values()) {
			series.getData().add(new XYChart.Data(tick, i));
		}

		return series;
	}

}
