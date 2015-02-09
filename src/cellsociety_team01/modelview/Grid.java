package cellsociety_team01.modelview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import cellsociety_team01.Pair;
import cellsociety_team01.CellState.Cell;
import cellsociety_team01.CellState.State;
import cellsociety_team01.simulations.Simulation;

public class Grid {
	Simulation simulation;
	private ArrayList<Cell> cells;
	private GUI myView;
	private boolean simRunning;
	private double updateRate;
	private Timeline myLoop;
	private String author;
	int cols, rows;
	private GridModel myGridModel;

	public static enum gridShapeTypes {
		SQUARE, TRIANGULAR
	};

	private gridShapeTypes myCellShape;

	public static enum gridEdgeTypes {
		FINITE, INFINITE, TOROIDAL
	};

	private gridEdgeTypes myEdgeType;
	private boolean gridOutline;

	public Grid() {
		simRunning = false;
	}

	public void setView(GUI viewIn) {
		myView = viewIn;
	}

	public void setBounds(Pair bounds) {
		cols = bounds.getX();
		rows = bounds.getY();
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public boolean isGridOutlined() {
		return gridOutline;
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
		updateCells();
		updateCurStates();
		myView.update(true);
	}

	public void updateCurStates() {
		for (Cell c : cells)
			c.updateCurState();
	}

	public void setConfigs(Map<String, String> configs) {
		String grid_edge = configs.get("grid_edge");
		if (grid_edge.equals("Finite"))
			myEdgeType = gridEdgeTypes.FINITE;
		else if (grid_edge.equals("Infinite"))
			myEdgeType = gridEdgeTypes.INFINITE;
		else if (grid_edge.equals("Toroidal"))
			myEdgeType = gridEdgeTypes.TOROIDAL;
		else {
			Exception e = new Exception();
			e.printStackTrace();
		}
		
		String grid_shape = configs.get("grid_shape");
		if (grid_shape.equals("Square")) {
			myGridModel = new SquareGridModel(myEdgeType);
			myCellShape = gridShapeTypes.SQUARE;
		}
		else if (grid_shape.equals("Triangle")) {
			myGridModel = new TriangularGridModel(myEdgeType);
			myCellShape = gridShapeTypes.TRIANGULAR;
		}
		else {
			Exception e = new Exception();
			e.printStackTrace();
		}

		String grid_outline = configs.get("grid_outline");
		if (grid_outline.equals("Yes"))
			gridOutline = true;
		else
			gridOutline = false;
	}

	public void setAuthor(String authorIn) {
		author = authorIn;
	}

	public String getAuthor() {
		return author;
	}

	public void setSimulation(Simulation simulationIn) {

		simulation = simulationIn;
	}

	public void updateGrid(Collection<Cell> cellsIn) {
		cells = new ArrayList<Cell>();
		cells.addAll(cellsIn);
	}

	public void setNeighbors(Map<Pair, Cell> cellMap) {
		myGridModel.setNeighbors(cellMap, simulation, rows, cols);
	}
	
	public void setTitle(String titleIn) {
		myView.getStage().setTitle(titleIn);
	}
	

	public void changeUpdateRate(double newRate) {
		myLoop.stop();
		
		myLoop = new Timeline(newRate);
		KeyFrame frame = start(newRate);
		myLoop.setCycleCount(Animation.INDEFINITE);
		myLoop.getKeyFrames().add(frame);
		myLoop.play();
	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(double frameRate) {
		updateRate = frameRate;
		System.out.println("Update Rate: " + updateRate);
		return new KeyFrame(Duration.millis(updateRate*10),
				e -> update());
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public gridShapeTypes getShape() {
		return myCellShape;
	}

	public void update() {
		if (simRunning) {
			updateCells();
			updateCurStates();
		}
		myView.update(false); // by this call, NEXT is null, and CUR is
		// up-to-date
	}

	private void updateCells() {
		for (State s : simulation.getStates()) {
			for (Cell cur : cells) { // cur = each cell in the grid
				if ((cur.getCurState().equals(s))) { // THIS SHOULD take both
					// types of RACE at one
					// time
					simulation.update(cur, cur.getNeighbors());
				}
			}
		}
		setNotUpdated();
	}

	private void setNotUpdated() {
		for (Cell c : cells) {
			c.setUpdated(false);
		}
	}

	public void cycleCellState(int cellX, int cellY) {
		for (Cell c : cells) {
			if (c.getX() == cellX && c.getY() == cellY) {
				c.setCurState(simulation.cycleNextState(c.getCurState()));
			}
		}		
	}

}
